package de.jquast.application.service;

import de.jquast.application.exception.StartSzenarioException;
import de.jquast.application.fake.FakeBestAgent;
import de.jquast.application.fake.FakeBestAlgorithm;
import de.jquast.application.fake.FakeBestPolicy;
import de.jquast.application.fake.FakeBestVisualizer;
import de.jquast.application.service.impl.ExecutionServiceImpl;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentRepository;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.domain.exception.*;
import de.jquast.domain.policy.PolicyDescriptor;
import de.jquast.domain.policy.PolicyRepository;
import de.jquast.domain.shared.*;
import de.jquast.domain.visualizer.PolicyVisualizerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ExecutionServiceTest {

    private ExecutionServiceImpl executionService;

    @Mock
    private AgentRepository agentRepository;
    @Mock
    private PolicyRepository policyRepository;
    @Mock
    private EnvironmentRepository environmentRepository;
    @Mock
    private ConfigService configService;
    @Mock
    private ActionValueRepository actionValueRepository;
    @Mock
    private RLAlgorithmRepository algorithmRepository;
    @Mock
    private PolicyVisualizerFactory policyVisualizerFactory;

    @Mock
    private SzenarioExecutionObserver observer;

    @Mock
    private Environment environment;
    @Mock
    private ActionSource source;

    private RLSettings settings;
    private AgentDescriptor agentDescriptor;
    private PolicyDescriptor policyDescriptor;
    private EnvironmentDescriptor environmentDescriptor;
    private RLAlgorithmDescriptor algorithmDescriptor;

    @BeforeEach
    void prepare() throws AgentCreationException, PolicyCreationException, EnvironmentCreationException, AlgorithmCreationException, VisualizerCreationException, PersistStoreException {
        MockitoAnnotations.openMocks(this);

        settings = new RLSettings(
                0.0, 0.0, 0.0, 0.0
        );

        setupDescriptors();
        setupActionValueRepository();
        setupAgentRepository();
        setupPolicyRepository();

        when(environment.getStateSpace()).thenReturn(10);

        when(environmentRepository.getEnvironment("best-environment")).thenReturn(Optional.of(environmentDescriptor));
        when(environmentRepository.createEnvironmentInstance(
                eq(environmentDescriptor),
                any()
        )).thenReturn(environment);

        when(configService.getRLSettings()).thenReturn(settings);

        when(policyVisualizerFactory.createVisualizer(any(), any(), any())).thenReturn(
                Optional.of(new FakeBestVisualizer(null, null, null))
        );

        executionService = new ExecutionServiceImpl(
                agentRepository,
                policyRepository,
                environmentRepository,
                configService,
                actionValueRepository,
                algorithmRepository,
                policyVisualizerFactory
        );
    }

    private void setupDescriptors() {
        agentDescriptor = new AgentDescriptor(
                "best-agent", "cool agent",
                new Action[]{Action.DO_NOTHING}, 10
        );
        policyDescriptor = new PolicyDescriptor(
                "best-policy", "Beste Policy"
        );
        environmentDescriptor = new EnvironmentDescriptor(
                "best-environment",
                "cool environment",
                new Action[]{}
        );
        algorithmDescriptor = new RLAlgorithmDescriptor(
                "best-algorithm",
                "cool algorithm",
                FakeBestAlgorithm.class
        );
    }

    private void setupAgentRepository() throws AgentCreationException {
        when(agentRepository.getAgentInfo("best-agent")).thenReturn(Optional.of(agentDescriptor));
        when(agentRepository.createAgentInstance(eq(agentDescriptor), any(), any())).thenReturn(
                new FakeBestAgent(environment, source, settings)
        );
    }

    private void setupPolicyRepository() throws PolicyCreationException {
        when(policyRepository.getPolicyInfo("best-policy")).thenReturn(Optional.of(policyDescriptor));
        when(policyRepository.getDefaultPolicyInfo()).thenReturn(policyDescriptor);
        when(policyRepository.getMaximizingPolicyInfo()).thenReturn(policyDescriptor);
        when(policyRepository.createPolicyInstance(eq(policyDescriptor), any())).thenReturn(
                new FakeBestPolicy(new ActionValueStore(10, 10), settings)
        );
    }

    private void setupActionValueRepository() throws AlgorithmCreationException, PersistStoreException {
        PersistedStoreInfo bestInfo = new PersistedStoreInfo(0, "best-agent", "best-environment");
        when(actionValueRepository.persistActionValueStore(
                eq("best-environment"),
                eq("best-agent"),
                any()
        )).thenReturn(bestInfo);
        when(actionValueRepository.getInfoById(0)).thenReturn(Optional.of(bestInfo));
        when(actionValueRepository.fetchStoreFromInfo(bestInfo)).thenReturn(Optional.of(new ActionValueStore(10, 10)));

        PersistedStoreInfo differentInfo = new PersistedStoreInfo(1, "unknown-agent", "unknown-environment");
        when(actionValueRepository.persistActionValueStore(
                eq("unknown-environment"),
                eq("unknown-agent"),
                any()
        )).thenReturn(differentInfo);
        when(actionValueRepository.getInfoById(1)).thenReturn(Optional.of(differentInfo));
        when(actionValueRepository.fetchStoreFromInfo(differentInfo)).thenReturn(Optional.of(new ActionValueStore(1, 1)));

        when(algorithmRepository.getDefaultAlgorithmInfo()).thenReturn(algorithmDescriptor);
        when(algorithmRepository.createAlgorithmInstance(eq(algorithmDescriptor), any(), any())).thenReturn(new FakeBestAlgorithm(new ActionValueStore(10, 10), source, settings));
    }

    @Test
    void startSzenarioWithUnknownDescriptorsShouldThrow() throws StartSzenarioException {
        Exception e = assertThrows(StartSzenarioException.class, () -> executionService.startTraining(
                "???????",
                "best-environment",
                "",
                1,
                0,
                Optional.empty()
        ));
        assertExceptionMessage(e, "Der Agent konnte nicht gefunden werden.");

        e = assertThrows(StartSzenarioException.class, () -> executionService.startTraining(
                "best-agent",
                "??????????",
                "",
                1,
                0,
                Optional.empty()
        ));
        assertExceptionMessage(e, "Das Environment konnte nicht gefunden werden.");
    }

    @Test
    void startSzenarioShouldThrowOnInvalidActionValueStore() throws StartSzenarioException {
        Exception e = assertThrows(StartSzenarioException.class, () -> executionService.startTraining(
                "best-agent",
                "best-environment",
                "",
                1,
                1, // Anderer Store
                Optional.empty()
        ));

        assertExceptionMessage(e, "Dieser Value-Store ist nicht mit dem Environment kompatibel!");
    }

    @Test
    void startSzenarioShouldThrowOnUnknownActionValueStore() {
        Exception e = assertThrows(StartSzenarioException.class, () -> executionService.startTraining(
                "best-agent",
                "best-environment",
                "",
                1,
                1000, // Unbekannter Store
                Optional.empty()
        ));

        assertExceptionMessage(e, "ID des Stores ungültig!");
    }

    @Test
    void startSzenarioShouldCallAllObserverMethods() throws StartSzenarioException {
        executionService.startTraining(
                "best-agent",
                "best-environment",
                "",
                10,
                0,
                Optional.of(observer));

        verify(observer, times(1)).onSzenarioStart(any());
        verify(observer, times(1)).onSzenarioEnd(any(), anyDouble());
        verify(observer, times(10)).preSzenarioStep(any(), anyLong(), anyDouble());
        verify(observer, times(10)).postSzenarioStep(any(), anyLong(), anyDouble());
        verify(observer, times(1)).onActionStorePersisted(any());
    }

    @Test
    void startEvaluationShouldNotCallPersistStore() throws StartSzenarioException, PersistStoreException {
        executionService.startEvaluation(
                "best-agent",
                "best-environment",
                "",
                10,
                0,
                Optional.of(observer));

        verify(observer, times(1)).onSzenarioStart(any());
        verify(observer, times(1)).onSzenarioEnd(any(), anyDouble());
        verify(observer, times(10)).preSzenarioStep(any(), anyLong(), anyDouble());
        verify(observer, times(10)).postSzenarioStep(any(), anyLong(), anyDouble());
        verify(observer, times(0)).onActionStorePersisted(any());
        verify(actionValueRepository, times(0)).persistActionValueStore(anyString(), any(), any());
    }

    @Test
    void startEvaluationShouldThrowOnNegativeStoreId() {
        Exception e = assertThrows(StartSzenarioException.class, () -> executionService.startEvaluation(
                "best-agent",
                "best-environment",
                "",
                1,
                -10, // Negative Store-ID
                Optional.empty()
        ));

        assertExceptionMessage(e, "Um das Training einer Policy zu bewerten muss ein entsprechender Store übergeben werden.");
    }

    private void assertExceptionMessage(Exception ex, String expected) {
        assertEquals(ex.getMessage(), expected);
    }

}
