package de.jquast.application.service;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.algorithm.AlgorithmFactory;
import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.config.DefaultConfigItem;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.policy.PolicyVisualizer;
import de.jquast.domain.policy.PolicyVisualizerFactory;
import de.jquast.domain.shared.ActionValueRepository;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.domain.shared.StoredValueInfo;
import de.jquast.utils.di.annotations.Inject;
import exception.StartAgentTrainingException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ExecutionService {

    private AgentService agentService;
    private EnvironmentService envService;
    private ConfigService configService;
    private RLSettingsService rlSettingsService;
    private ActionValueRepository actionValueRepository;

    private EnvironmentFactory environmentFactory;
    private AgentFactory agentFactory;
    private PolicyFactory policyFactory;
    private AlgorithmFactory algorithmFactory;
    private PolicyVisualizerFactory policyVisualizerFactory;

    @Inject
    public ExecutionService(
            AgentService agentService,
            EnvironmentService envService,
            ConfigService configService,
            RLSettingsService rlSettingsService,
            ActionValueRepository actionValueRepository,
            RLFactoryBundle factoryBundle) {
        this.agentService = agentService;
        this.envService = envService;
        this.configService = configService;
        this.rlSettingsService = rlSettingsService;
        this.actionValueRepository = actionValueRepository;

        this.environmentFactory = factoryBundle.getEnvironmentFactory();
        this.agentFactory = factoryBundle.getAgentFactory();
        this.policyFactory = factoryBundle.getPolicyFactory();
        this.algorithmFactory = factoryBundle.getAlgorithmFactory();
        this.policyVisualizerFactory = factoryBundle.getPolicyVisualizerFactory();
    }

    public Optional<PolicyVisualizer> startAgentTraining(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            String initFromFile,
            int resumeFromStore) throws StartAgentTrainingException {
        Optional<AgentDescriptor> agentDescriptorOp = agentService.getAgent(agentName);
        Optional<EnvironmentDescriptor> environmentDescriptorOp = envService.getEnvironment(envName);
        RLSettings settings = rlSettingsService.getRLSettings();
        System.out.println(settings.toString());

        // Check availability
        if (agentDescriptorOp.isEmpty() || environmentDescriptorOp.isEmpty())
            throw new StartAgentTrainingException("Agent oder Environment nicht gefunden!");

        // Unwrap descriptors
        AgentDescriptor agentDescriptor = agentDescriptorOp.get();
        EnvironmentDescriptor environmentDescriptor = environmentDescriptorOp.get();

        // Create & Check Environment
        Map<String, String> envOptionsMap = parseEnvOptions(envOptions);
        if (initFromFile != null && !initFromFile.isEmpty()) {
            envOptionsMap.put("from", initFromFile);
        }

        Optional<Environment> environmentOp = environmentFactory.createEnvironment(environmentDescriptor, envOptionsMap);
        if (environmentOp.isEmpty())
            throw new StartAgentTrainingException("Beim Erstellen des Environments ist ein Fehler aufgetreten. Bitte Parameter überprüfen!");

        // Unwrap Environment
        Environment environment = environmentOp.get();
        ActionValueStore store = buildStore(environment.getStateSpace(), agentDescriptor.actionSpace(), resumeFromStore);
        if (environment.getStateSpace() != store.getStateCount() || agentDescriptor.actionSpace() != store.getActionCount())
            throw new StartAgentTrainingException("Dieser Value-Store ist nicht mit dem Environment kompatibel!");

        // Create & Check Policy
        Optional<Policy> policyOp = policyFactory.createPolicy(null, store, settings);
        if (policyOp.isEmpty())
            throw new StartAgentTrainingException("Fehler beim Erstellen der Policy. Das darf nicht passieren hehe.");

        // Create & Check Algorithm
        Optional<RLAlgorithm> algorithmOp = algorithmFactory.createAlgorithm("qlearning", store, policyOp.get(), settings);
        if (algorithmOp.isEmpty())
            throw new StartAgentTrainingException("Fehler beim Erstellen des Algorithmus.");

        // Create & Check Agent
        Optional<Agent> agentOp = agentFactory.createAgent(agentName, environment, algorithmOp.get(), settings);
        if (agentOp.isEmpty())
            throw new StartAgentTrainingException("Fehler beim Erstellen des Agenten.");

        // Start Training & Store result
        startTrainLoop(agentOp.get(), environment, steps);
        storeTrainedPolicy(agentName, envName, policyOp.get());

        // Create Visualization
        return policyVisualizerFactory.createVisualizer(policyOp.get(), environment);
    }

    private ActionValueStore buildStore(int stateSpace, int actionSpace, int resumeFromStore) throws StartAgentTrainingException {
        if (resumeFromStore != -1) {
            Optional<StoredValueInfo> storedValueInfoOp = actionValueRepository.getStoredActionValueInfoById(resumeFromStore);

            if (storedValueInfoOp.isEmpty())
                throw new StartAgentTrainingException("ID des Stores ungültig!");

            Optional<ActionValueStore> actionValueStoreOp = actionValueRepository.fetchActionValueInfo(storedValueInfoOp.get());
            if (actionValueStoreOp.isEmpty())
                throw new StartAgentTrainingException("Fehler beim Lesen des Stores.");

             return actionValueStoreOp.get();
        }

        return new ActionValueStore(stateSpace, actionSpace);
    }

    private void startTrainLoop(Agent agent, Environment environment, long steps) {
        int trainingMessageInterval = Integer.parseInt(configService.getConfigItem(DefaultConfigItem.MESSAGE_TRAINING_AVERAGE_REWARD_STEPS).value());
        long lastMessage = 0;

        long currStep = 0;
        while (currStep < steps) {
            if (currStep - lastMessage >= trainingMessageInterval) {
                System.out.println(String.format(
                        Locale.US,
                        "Schritt %d/%d (%.2f%%), Durchschnittlicher Reward %f",
                        currStep,
                        steps,
                        (float) currStep / steps * 100,
                        agent.getCurrentAverageReward()));

                lastMessage = currStep;
            }

            environment.tick();
            agent.executeNextAction();

            currStep++;
        }
    }

    private void storeTrainedPolicy(String agent, String environment, Policy policy) {
        actionValueRepository.createActionValueStore(agent, environment, policy.getActionValueStore());
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
        private EnvironmentFactory environmentFactory;
        private AgentFactory agentFactory;
        private PolicyFactory policyFactory;
        private AlgorithmFactory algorithmFactory;
        private PolicyVisualizerFactory policyVisualizerFactory;

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
