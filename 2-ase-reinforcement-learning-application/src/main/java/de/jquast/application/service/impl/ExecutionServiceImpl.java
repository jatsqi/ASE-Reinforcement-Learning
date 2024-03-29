package de.jquast.application.service.impl;

import de.jquast.application.exception.StartSzenarioException;
import de.jquast.application.exception.SzenarioCreationException;
import de.jquast.application.service.ConfigService;
import de.jquast.application.service.ExecutionService;
import de.jquast.application.service.SzenarioExecutionObserver;
import de.jquast.application.session.DescriptorBundle;
import de.jquast.application.session.Szenario;
import de.jquast.application.session.SzenarioSession;
import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentRepository;
import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.domain.exception.PersistStoreException;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyDescriptor;
import de.jquast.domain.policy.PolicyRepository;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionValueRepository;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.domain.shared.PersistedStoreInfo;
import de.jquast.domain.visualizer.PolicyVisualizer;
import de.jquast.domain.visualizer.PolicyVisualizerFactory;
import de.jquast.utils.di.annotations.Inject;

import java.util.Optional;

/**
 * Primärer Service, der mit dem Training bzw. der Evaluation der Agenten beschäftigt
 */
public class ExecutionServiceImpl implements ExecutionService {

    private final AgentRepository agentRepository;
    private final PolicyRepository policyRepository;
    private final EnvironmentRepository environmentRepository;
    private final ConfigService configService;
    private final ActionValueRepository actionValueRepository;
    private final RLAlgorithmRepository algorithmRepository;
    private final PolicyVisualizerFactory policyVisualizerFactory;

    @Inject
    public ExecutionServiceImpl(
            AgentRepository agentRepository,
            PolicyRepository policyRepository,
            EnvironmentRepository environmentRepository,
            ConfigService configService,
            ActionValueRepository actionValueRepository,
            RLAlgorithmRepository algorithmRepository,
            PolicyVisualizerFactory policyVisualizerFactory) {
        this.agentRepository = agentRepository;
        this.policyRepository = policyRepository;
        this.environmentRepository = environmentRepository;
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

    /**
     * Startet ein Training für einen Agenten.
     *
     * @param agentName  Name des Agenten.
     * @param envName    Name des Environment.
     * @param envOptions Optionen für das Erstellen des Environments.
     * @param steps      Maximale Schritte, die das Training benötigen darf.
     * @param storeId    ID des Action Value Stores, mit denen das Training initialisiert wird.
     * @param observer   Externer Beobachter, um das Training zu beobachten.
     * @throws StartSzenarioException Wird geworfen, sofern etwas beim Starten des Trainings fehlgeschlagen ist.
     */
    public void startTraining(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            int storeId,
            Optional<SzenarioExecutionObserver> observer) throws StartSzenarioException {
        try {
            Szenario szenario = createSzenario(agentName, envName, policyRepository.getDefaultPolicyInfo().name(), envOptions, steps, storeId);

            // Hole Agent Info, muss an dieser Stelle gültig sein, da Szenario erfolgreich erstellt
            @SuppressWarnings("OptionalGetWithoutIsPresent")
            AgentDescriptor agentDescriptor = agentRepository
                    .getAgentInfo(agentName).get();
            ActionValueStore store = szenario.policy().getActionValueStore();

            // Decorate Policy with Algorithm and recreate Agent
            RLAlgorithm algorithm = algorithmRepository.createAlgorithmInstance(algorithmRepository.getDefaultAlgorithmInfo(), store, szenario.policy());
            Agent agent = agentRepository.createAgentInstance(agentDescriptor, szenario.environment(), algorithm);

            SzenarioSession session = new SzenarioSession(new Szenario(
                    szenario.metadata(),
                    agent,
                    szenario.environment(),
                    szenario.policy(),
                    szenario.visualizer(),
                    steps,
                    szenario.settings()
            ));

            // Streng genommen braucht es hier keinen gewrappten Observer, da die Session hier gestartet wird.
            // Der Code für das Speichern könnte auch nach session.start() erscheinen.
            // Für die sehr nahe Zukunft ist allerdings angedacht, dass eine Session ASYNCHRON gestartet wird,
            // weswegen ein solcher Observer dann unabdingbar wird.
            session.addObserver(createWrappedTrainingObserver(observer));

            session.start();
        } catch (Exception e) {
            throw new StartSzenarioException(e.getMessage());
        }
    }

    /**
     * Startet eine Evaluation für einen Agenten.
     * Unterscheidet sich strukturell nur minimal vom Training, da hier kein Algorithmus als Decorator genutzt wird.
     *
     * @param agentName  Name des Agenten.
     * @param envName    Name des Environment.
     * @param envOptions Optionen für das Erstellen des Environments.
     * @param steps      Maximale Schritte, die das Training benötigen darf.
     * @param storeId    ID des Action Value Stores, mit denen das Training initialisiert wird.
     * @param observer   Externer Beobachter, um das Training zu beobachten.
     * @throws StartSzenarioException Wird geworfen, sofern etwas beim Starten des Trainings fehlgeschlagen ist.
     */
    public void startEvaluation(
            String agentName,
            String envName,
            String envOptions,
            long steps,
            int storeId,
            Optional<SzenarioExecutionObserver> observer) throws StartSzenarioException {
        if (storeId < 0)
            throw new StartSzenarioException("Um das Training einer Policy zu bewerten muss ein entsprechender Store übergeben werden.");

        try {
            Szenario szenario = createSzenario(agentName, envName, policyRepository.getMaximizingPolicyInfo().name(), envOptions, steps, storeId);

            SzenarioSession session = new SzenarioSession(szenario);

            if (observer.isPresent())
                session.addObserver(observer.get());

            session.start();
        } catch (SzenarioCreationException e) {
            throw new StartSzenarioException(e.getMessage());
        }
    }

    /**
     * Erstellt ein grundsätzliches Szenario aus den übergeben Parametern, welches für das Training oder ähnliches
     * genutzt werden kann.
     *
     * @param agentName  Name des Agenten.
     * @param envName    Name des Environments.
     * @param policyName Name der Policy.
     * @param envOptions Optionen für das Environment.
     * @param maxSteps   Maximale Anzahl an Trainingsschritten.
     * @param storeId    Store ID, von der das Training fortgefahren wird.
     * @return Das erstellte Szenario.
     * @throws SzenarioCreationException Wird ausgelöst, sofern etwas bei der Erstellung nicht geklappt hat.
     */
    private Szenario createSzenario(
            String agentName,
            String envName,
            String policyName,
            String envOptions,
            long maxSteps,
            int storeId) throws SzenarioCreationException {
        // Get & Check descriptor
        AgentDescriptor agentDescriptor = agentRepository
                .getAgentInfo(agentName)
                .orElseThrow(() -> new SzenarioCreationException("Der Agent konnte nicht gefunden werden."));
        EnvironmentDescriptor envDescriptor = environmentRepository
                .getEnvironment(envName)
                .orElseThrow(() -> new SzenarioCreationException("Das Environment konnte nicht gefunden werden."));
        PolicyDescriptor policyDescriptor = policyRepository
                .getPolicyInfo(policyName)
                .orElseThrow(() -> new SzenarioCreationException("Die Policy konnte nicht gefunden werden."));

        if (Action.computeMissingRequirements(agentDescriptor.availableCapabilities(), envDescriptor.requiredCapabilities()).length > 0)
            throw new SzenarioCreationException("Dieser Agent und das Environment sind nicht miteinander kompatibel!");

        try {
            // Settings abrufen
            RLSettings settings = configService.getRLSettings();

            // Create Environment & Store
            Environment environment = environmentRepository.createEnvironmentInstance(envDescriptor, makeStringOptional(envOptions));

            // Create Store
            ActionValueStore store = queryStore(environment.getStateSpace(), agentDescriptor.actionSpace(), storeId);
            if (environment.getStateSpace() != store.getStateCount() || agentDescriptor.actionSpace() != store.getActionCount())
                throw new SzenarioCreationException("Dieser Value-Store ist nicht mit dem Environment kompatibel!");

            // Build Policy
            Policy policy = policyRepository.createPolicyInstance(policyDescriptor, store);

            // Build Agent
            Agent agent = agentRepository.createAgentInstance(agentDescriptor, environment, policy);

            // Create Visualization
            PolicyVisualizer visualizer = policyVisualizerFactory
                    .createVisualizer(agent, policy, environment)
                    .orElseThrow(() -> new SzenarioCreationException("Visualizer konnte nicht erstellt werden :("));

            return new Szenario(
                    new DescriptorBundle(agentDescriptor, envDescriptor, policyDescriptor),
                    agent, environment, policy, visualizer, maxSteps, settings);
        } catch (Exception e) {
            throw new SzenarioCreationException(e.getMessage());
        }
    }

    private ActionValueStore queryStore(int stateSpace, int actionSpace, int resumeFromStore) throws StartSzenarioException {
        if (resumeFromStore >= 0) {
            Optional<PersistedStoreInfo> storedValueInfoOp = actionValueRepository.getInfoById(resumeFromStore);

            if (storedValueInfoOp.isEmpty())
                throw new StartSzenarioException("ID des Stores ungültig!");

            Optional<ActionValueStore> actionValueStoreOp = actionValueRepository.fetchStoreFromInfo(storedValueInfoOp.get());
            if (actionValueStoreOp.isEmpty())
                throw new StartSzenarioException("Fehler beim Lesen des Stores.");

            return actionValueStoreOp.get();
        }

        return new ActionValueStore(stateSpace, actionSpace);
    }

    private PersistedStoreInfo storeTrainedPolicy(String agent, String environment, Policy policy) throws PersistStoreException {
        return actionValueRepository.persistActionValueStore(agent, environment, policy.getActionValueStore());
    }

    /**
     * Erstellt einen Wrapper um einen bestehenden Observer, um onSzenarioEnd() abzufangen, den Store zu speichern, un den
     * gewrappten Observer durch einen Aufruf über onActionStorePersisted() über die Speicherung zu informieren.
     *
     * @param progressObserver Der zu wrappende Observer.
     * @return Ein Observer, welche den Store speichern kann.
     */
    private SzenarioExecutionObserver createWrappedTrainingObserver(Optional<SzenarioExecutionObserver> progressObserver) {
        return new SzenarioExecutionObserver() {
            @Override
            public void onSzenarioEnd(SzenarioSession session, double averageReward) {
                progressObserver.ifPresent(szenarioExecutionObserver -> szenarioExecutionObserver.onSzenarioEnd(session, averageReward));

                DescriptorBundle bundle = session.getSzenario().metadata();
                PersistedStoreInfo info = null;
                try {
                    info = storeTrainedPolicy(
                            bundle.agentDescriptor().name(),
                            bundle.environmentDescriptor().name(),
                            session.getSzenario().policy());
                } catch (PersistStoreException e) {
                    throw new RuntimeException("Es ist ein kritischer Fehler beim Speichern der Trainieren Policy auftreten!");
                }

                onActionStorePersisted(info);
            }

            @Override
            public void onActionStorePersisted(PersistedStoreInfo info) {
                progressObserver.ifPresent(szenarioExecutionObserver -> szenarioExecutionObserver.onActionStorePersisted(info));
            }

            @Override
            public void onSzenarioStart(SzenarioSession session) {
                progressObserver.ifPresent(szenarioExecutionObserver -> szenarioExecutionObserver.onSzenarioStart(session));
            }

            @Override
            public void preSzenarioStep(SzenarioSession session, long currentStep, double averageReward) {
                progressObserver.ifPresent(szenarioExecutionObserver -> szenarioExecutionObserver.preSzenarioStep(session, currentStep, averageReward));
            }

            @Override
            public void postSzenarioStep(SzenarioSession session, long currentStep, double averageReward) {
                progressObserver.ifPresent(szenarioExecutionObserver -> szenarioExecutionObserver.postSzenarioStep(session, currentStep, averageReward));
            }
        };
    }
}
