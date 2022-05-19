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
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.utils.di.annotations.Inject;
import exception.StartAgentTrainingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExecutionService {

    private AgentService agentService;
    private EnvironmentService envService;
    private ConfigService configService;
    private RLSettingsService rlSettingsService;
    private RLAlgorithmService algorithmService;

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
            RLAlgorithmService algorithmService,
            RLFactoryBundle factoryBundle) {
        this.agentService = agentService;
        this.envService = envService;
        this.configService = configService;
        this.rlSettingsService = rlSettingsService;
        this.algorithmService = algorithmService;

        this.environmentFactory = factoryBundle.getEnvironmentFactory();
        this.agentFactory = factoryBundle.getAgentFactory();
        this.policyFactory = factoryBundle.getPolicyFactory();
        this.algorithmFactory = factoryBundle.getAlgorithmFactory();
        this.policyVisualizerFactory = factoryBundle.getPolicyVisualizerFactory();
    }

    public Optional<PolicyVisualizer> startAgentTraining(String agentName, String envName, String envOptions, long steps) throws StartAgentTrainingException {
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
        Optional<Environment> environmentOp = environmentFactory.createEnvironment(environmentDescriptor, parseEnvOptions(envOptions));
        if (environmentOp.isEmpty())
            throw new StartAgentTrainingException("Beim Erstellen des Environments ist ein Fehler aufgetreten. Bitte Parameter überprüfen!");

        // Unwrap Environment
        Environment environment = environmentOp.get();
        ActionValueStore store = new ActionValueStore(environment.getStateSpace(), agentDescriptor.actionSpace());

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

        // Start Training
        startTrainLoop(agentOp.get(), environment, steps);

        // Create Visualization
        return policyVisualizerFactory.createVisualizer(policyOp.get(), environment);
    }

    private void startTrainLoop(Agent agent, Environment environment, long steps) {
        int trainingMessageInterval = Integer.parseInt(configService.getConfigItem(DefaultConfigItem.MESSAGE_TRAINING_AVERAGE_REWARD_MS).value());
        long lastMessage = 0;

        long currStep = 0;
        while (++currStep < steps) {
            if (System.currentTimeMillis() - lastMessage > trainingMessageInterval) {
                System.out.println(String.format("Schritt %d, Durchschnittlicher Reward %f", currStep, agent.getCurrentAverageReward()));
                lastMessage = System.currentTimeMillis();
            }

            environment.tick();
            agent.executeNextAction();
        }
    }

    private Map<String, String> parseEnvOptions(String envOptions) {
        Map<String, String> result = new HashMap<>();

        for (String s : envOptions.split(";")) {
            String[] parts = s.split("=");
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
