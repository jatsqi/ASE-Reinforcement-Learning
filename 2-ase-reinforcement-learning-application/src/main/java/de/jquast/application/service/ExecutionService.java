package de.jquast.application.service;

import de.jquast.application.config.DefaultConfigItem;
import de.jquast.application.session.TrainingSession;
import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.agent.AgentRepository;
import de.jquast.domain.algorithm.AlgorithmFactory;
import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.policy.PolicyRepository;
import de.jquast.domain.policy.visualizer.PolicyVisualizer;
import de.jquast.domain.policy.visualizer.PolicyVisualizerFactory;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueRepository;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.domain.shared.PersistedStoreInfo;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.application.exception.StartAgentTrainingException;

import java.util.Locale;
import java.util.Optional;

public class ExecutionService {

    private final AgentRepository agentRepository;
    private final PolicyRepository policyRepository;
    private final EnvironmentService envService;
    private final ConfigService configService;
    private final ActionValueRepository actionValueRepository;
    private final RLAlgorithmRepository algorithmRepository;
    private final PolicyVisualizerFactory policyVisualizerFactory;

    @Inject
    public ExecutionService(
            AgentRepository agentRepository,
            PolicyRepository policyRepository,
            EnvironmentService envService,
            ConfigService configService,
            ActionValueRepository actionValueRepository,
            RLAlgorithmRepository algorithmRepository,
            PolicyVisualizerFactory policyVisualizerFactory) {
        this.agentRepository = agentRepository;
        this.policyRepository = policyRepository;
        this.envService = envService;
        this.configService = configService;
        this.actionValueRepository = actionValueRepository;
        this.algorithmRepository = algorithmRepository;
        this.policyVisualizerFactory = policyVisualizerFactory;
    }

    private static Optional<String> makeStringOptional(String str) {
        return Optional.ofNullable(
                str == null || str.isEmpty() || str.trim().isEmpty() ? null : str
        );
    }

    public TrainingSession createTrainingSession(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            String initFromFile,
            int resumeFromStoreId) throws StartAgentTrainingException {
        return createSession(agentName, envName, envOptions, steps, initFromFile, resumeFromStoreId, false);
    }

    public TrainingSession createEvaluationSession(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            String initFromFile,
            int evalStoreId) throws StartAgentTrainingException {
        if (evalStoreId < 0)
            throw new StartAgentTrainingException("Um das Training einer Policy zu bewerten muss ein entsprechender Store übergeben werden.");

        return createSession(agentName, envName, envOptions, steps, initFromFile, evalStoreId, true);
    }

    private TrainingSession createSession(
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
        Optional<AgentDescriptor> agentDescriptorOp = agentRepository.getAgentInfo(agentName);
        if (agentDescriptorOp.isEmpty())
            throw new StartAgentTrainingException("Agent oder Environment nicht gefunden!");
        AgentDescriptor agentDescriptor = agentDescriptorOp.get();

        try {
            // Create Environment & Store
            Environment environment = envService.createEnvironment(envName, makeStringOptional(envOptions), makeStringOptional(initFromFile));
            ActionValueStore store = queryStore(environment.getStateSpace(), agentDescriptor.actionSpace(), resumeFromStoreId);
            if (environment.getStateSpace() != store.getStateCount() || agentDescriptor.actionSpace() != store.getActionCount())
                throw new StartAgentTrainingException("Dieser Value-Store ist nicht mit dem Environment kompatibel!");

            // Build Policy
            Policy policy = onlyEvaluate ?
                    policyRepository.createMaximizingPolicy(store) :
                    policyRepository.createPolicyInstance(policyRepository.getDefaultPolicyInfo(), store);
            ActionSource actionSource = policy;

            // Decorate Policy with Algorithm if learning is enabled
            if (!onlyEvaluate)
                actionSource = algorithmRepository.createAlgorithmInstance(algorithmRepository.getAlgorithmInfo("qlearning").get(), store, policy);

            // Build Agent
            Agent agent = agentRepository.createAgentInstance(agentDescriptor, environment, actionSource);

            // Persist trained policy if learning is enabled
            if (!onlyEvaluate) {
                PersistedStoreInfo info = storeTrainedPolicy(agentName, envName, policy);
                System.out.printf(Locale.US, "\nPolicy gespeichert, Id: %d, Environment: %s, Agent: %s%n", info.id(), info.environment(), info.agent());
            }

            // Create Visualization
            Optional<PolicyVisualizer> visualizer = policyVisualizerFactory.createVisualizer(policy, environment);
            if (visualizer.isEmpty())
                throw new StartAgentTrainingException("Visualizer konnte nicht erstellt werden :(");

            return new TrainingSession(agent, environment, visualizer.get(), steps);
        } catch (Exception e) {
            throw new StartAgentTrainingException(e.getMessage());
        }
    }

    private ActionValueStore queryStore(int stateSpace, int actionSpace, int resumeFromStore) throws StartAgentTrainingException {
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

    private PersistedStoreInfo storeTrainedPolicy(String agent, String environment, Policy policy) {
        return actionValueRepository.persistActionValueStore(agent, environment, policy.getActionValueStore());
    }

}
