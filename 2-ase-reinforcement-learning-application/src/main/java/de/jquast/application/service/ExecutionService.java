package de.jquast.application.service;

import config.DefaultConfigItem;
import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.algorithm.AlgorithmFactory;
import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.policy.PolicyVisualizer;
import de.jquast.domain.policy.PolicyVisualizerFactory;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueRepository;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.domain.shared.PersistedStoreInfo;
import de.jquast.utils.di.annotations.Inject;
import exception.StartAgentTrainingException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ExecutionService {

    private final AgentService agentService;
    private final EnvironmentService envService;
    private final ConfigService configService;
    private final ActionValueRepository actionValueRepository;

    private final EnvironmentFactory environmentFactory;
    private final AgentFactory agentFactory;
    private final PolicyFactory policyFactory;
    private final AlgorithmFactory algorithmFactory;
    private final PolicyVisualizerFactory policyVisualizerFactory;

    @Inject
    public ExecutionService(
            AgentService agentService,
            EnvironmentService envService,
            ConfigService configService,
            ActionValueRepository actionValueRepository,
            RLFactoryBundle factoryBundle) {
        this.agentService = agentService;
        this.envService = envService;
        this.configService = configService;
        this.actionValueRepository = actionValueRepository;

        this.environmentFactory = factoryBundle.getEnvironmentFactory();
        this.agentFactory = factoryBundle.getAgentFactory();
        this.policyFactory = factoryBundle.getPolicyFactory();
        this.algorithmFactory = factoryBundle.getAlgorithmFactory();
        this.policyVisualizerFactory = factoryBundle.getPolicyVisualizerFactory();
    }

    private static Optional<String> makeStringOptional(String str) {
        return Optional.ofNullable(
                str == null || str.isEmpty() || str.trim().isEmpty() ? null : str
        );
    }

    public Optional<PolicyVisualizer> startAgentTraining(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            String initFromFile,
            int resumeFromStoreId) throws StartAgentTrainingException {
        return startAgentSzenario(agentName, envName, envOptions, steps, initFromFile, resumeFromStoreId, false);
    }

    public Optional<PolicyVisualizer> startAgentEvaluation(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            String initFromFile,
            int evalStoreId) throws StartAgentTrainingException {
        if (evalStoreId < 0)
            throw new StartAgentTrainingException("Um das Training einer Policy zu bewerten muss ein entsprechender Store übergeben werden.");

        return startAgentSzenario(agentName, envName, envOptions, steps, initFromFile, evalStoreId, true);
    }

    private Optional<PolicyVisualizer> startAgentSzenario(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            String initFromFile,
            int resumeFromStoreId,
            boolean onlyEvaluate) throws StartAgentTrainingException {
        RLSettings settings = configService.getRLSettings();
        System.out.println(settings.toString());

        // Get & Check descriptor
        Optional<AgentDescriptor> agentDescriptorOp = agentService.getAgent(agentName);
        if (agentDescriptorOp.isEmpty())
            throw new StartAgentTrainingException("Agent oder Environment nicht gefunden!");
        AgentDescriptor agentDescriptor = agentDescriptorOp.get();

        // Create Environment & Store
        Environment environment = buildEnvironment(envName, makeStringOptional(envOptions), makeStringOptional(initFromFile));
        ActionValueStore store = buildStore(environment.getStateSpace(), agentDescriptor.actionSpace(), resumeFromStoreId);
        if (environment.getStateSpace() != store.getStateCount() || agentDescriptor.actionSpace() != store.getActionCount())
            throw new StartAgentTrainingException("Dieser Value-Store ist nicht mit dem Environment kompatibel!");

        // Build Policy
        Policy policy = buildPolicy(null, store, settings, onlyEvaluate);
        ActionSource actionSource = policy;

        // Decorate Policy with Algorithm if learning is enabled
        if (!onlyEvaluate)
            actionSource = buildAlgorithm("qlearning", store, policy, settings);

        // Build Agent
        Agent agent = buildAgent(agentName, environment, actionSource, settings);

        // Start Training & Store result
        startTrainLoop(agent, environment, steps);

        // Persist trained policy if learning is enabled
        if (!onlyEvaluate) {
            PersistedStoreInfo info = storeTrainedPolicy(agentName, envName, policy);
            System.out.printf(Locale.US, "\nPolicy gespeichert, Id: %d, Environment: %s, Agent: %s%n", info.id(), info.environment(), info.agent());
        }

        // Create Visualization
        return policyVisualizerFactory.createVisualizer(policy, environment);
    }

    private Environment buildEnvironment(String envName, Optional<String> envOptions, Optional<String> initFromFile) throws StartAgentTrainingException {
        Optional<EnvironmentDescriptor> environmentDescriptorOp = envService.getEnvironment(envName);
        if (environmentDescriptorOp.isEmpty())
            throw new StartAgentTrainingException("Das angegebene Environment konnte nicht gefunden werden.");

        EnvironmentDescriptor environmentDescriptor = environmentDescriptorOp.get();

        // Create & Check Environment
        Map<String, String> envOptionsMap = new HashMap<>();
        if (envOptions.isPresent()) {
            envOptionsMap = parseEnvOptions(envOptions.get());

            if (initFromFile.isPresent()) {
                envOptionsMap.put("from", initFromFile.get());
            }
        }

        Optional<Environment> environmentOp = environmentFactory.createEnvironment(environmentDescriptor, envOptionsMap);
        if (environmentOp.isEmpty())
            throw new StartAgentTrainingException("Beim Erstellen des Environments ist ein Fehler aufgetreten. Bitte Parameter überprüfen!");

        // Unwrap Environment
        return environmentOp.get();
    }

    private Agent buildAgent(String agentName, Environment environment, ActionSource source, RLSettings settings) throws StartAgentTrainingException {
        // Create & Check Agent
        Optional<Agent> agentOp = agentFactory.createAgent(agentName, environment, source, settings);
        if (agentOp.isEmpty())
            throw new StartAgentTrainingException("Fehler beim Erstellen des Agenten.");

        return agentOp.get();
    }

    private ActionValueStore buildStore(int stateSpace, int actionSpace, int resumeFromStore) throws StartAgentTrainingException {
        if (resumeFromStore >= 0) {
            Optional<PersistedStoreInfo> storedValueInfoOp = actionValueRepository.getInfoById(resumeFromStore);

            if (storedValueInfoOp.isEmpty())
                throw new StartAgentTrainingException("ID des Stores ungültig!");

            Optional<ActionValueStore> actionValueStoreOp = actionValueRepository.fetchStoreFromInfo(storedValueInfoOp.get());
            if (actionValueStoreOp.isEmpty())
                throw new StartAgentTrainingException("Fehler beim Lesen des Stores.");

            return actionValueStoreOp.get();
        }

        return new ActionValueStore(stateSpace, actionSpace);
    }

    private Policy buildPolicy(String policyName, ActionValueStore store, RLSettings settings, boolean onlyEvaluate) throws StartAgentTrainingException {
        Optional<Policy> policyOp;
        if (!onlyEvaluate)
            policyOp = policyFactory.createPolicy(policyName, store, settings);
        else
            policyOp = policyFactory.createMaximizingPolicy(store, settings);

        if (policyOp.isEmpty())
            throw new StartAgentTrainingException("Fehler beim Erstellen der Policy. Das darf nicht passieren hehe.");

        return policyOp.get();
    }

    private RLAlgorithm buildAlgorithm(String algoName, ActionValueStore store, ActionSource source, RLSettings settings) throws StartAgentTrainingException {
        Optional<RLAlgorithm> algorithmOp = algorithmFactory.createAlgorithm(algoName, store, source, settings);
        if (algorithmOp.isEmpty())
            throw new StartAgentTrainingException("Fehler beim Erstellen des Algorithmus.");

        return algorithmOp.get();
    }

    private void startTrainLoop(Agent agent, Environment environment, long steps) {
        int trainingMessageInterval = Integer.parseInt(configService.getConfigItem(DefaultConfigItem.MESSAGE_TRAINING_AVERAGE_REWARD_STEPS).value());
        long lastMessage = 0;

        long currStep = 0;
        while (currStep < steps) {
            if (currStep - lastMessage >= trainingMessageInterval) {
                System.out.printf(
                        Locale.US,
                        "Schritt %d/%d (%.2f%%), Durchschnittlicher Reward %f%n",
                        currStep,
                        steps,
                        (float) currStep / steps * 100,
                        agent.getCurrentAverageReward());

                lastMessage = currStep;
            }

            environment.tick();
            agent.executeNextAction();

            currStep++;
        }
    }

    private PersistedStoreInfo storeTrainedPolicy(String agent, String environment, Policy policy) {
        return actionValueRepository.persistActionValueStore(agent, environment, policy.getActionValueStore());
    }

    private Map<String, String> parseEnvOptions(String envOptions) {
        Map<String, String> result = new HashMap<>();

        for (String s : envOptions.split(";")) {
            String[] parts = s.split("=");
            if (parts.length != 2)
                continue;

            result.put(parts[0], parts[1]);
        }

        return result;
    }

    public static class RLFactoryBundle {
        private final EnvironmentFactory environmentFactory;
        private final AgentFactory agentFactory;
        private final PolicyFactory policyFactory;
        private final AlgorithmFactory algorithmFactory;
        private final PolicyVisualizerFactory policyVisualizerFactory;

        @Inject
        public RLFactoryBundle(
                EnvironmentFactory environmentFactory,
                AgentFactory agentFactory,
                PolicyFactory policyFactory,
                AlgorithmFactory algorithmFactory,
                PolicyVisualizerFactory policyVisualizerFactory) {
            this.environmentFactory = environmentFactory;
            this.agentFactory = agentFactory;
            this.policyFactory = policyFactory;
            this.algorithmFactory = algorithmFactory;
            this.policyVisualizerFactory = policyVisualizerFactory;
        }

        public AlgorithmFactory getAlgorithmFactory() {
            return algorithmFactory;
        }

        public EnvironmentFactory getEnvironmentFactory() {
            return environmentFactory;
        }

        public AgentFactory getAgentFactory() {
            return agentFactory;
        }

        public PolicyFactory getPolicyFactory() {
            return policyFactory;
        }

        public PolicyVisualizerFactory getPolicyVisualizerFactory() {
            return policyVisualizerFactory;
        }
    }
}
