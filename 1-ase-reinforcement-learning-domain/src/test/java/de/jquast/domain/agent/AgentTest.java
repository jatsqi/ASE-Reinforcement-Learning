package de.jquast.domain.agent;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AgentTest {

    @Mock
    private Environment environment;

    @Mock
    private ActionSource source;

    private RLSettings settings;
    private FakeAgent agent;

    @BeforeEach
    public void prepareMocks() {
        settings = new RLSettings(
                0.0, 0.0, 0.0, 1.0
        );

        MockitoAnnotations.openMocks(this);

        when(environment.getCurrentState()).thenReturn(0);
        when(environment.getStateSpace()).thenReturn(2);
        when(environment.executeAction(eq(Action.DO_NOTHING), anyInt())).thenReturn(true);
        when(environment.executeAction(any(), anyInt())).thenReturn(false);
        when(environment.getReward()).thenReturn(1.0);

        when(source.selectAction(anyInt())).thenReturn(0);
        when(source.selectBestAction(anyInt())).thenReturn(0);

        this.agent = new FakeAgent(environment, source, settings);
        this.agent = Mockito.spy(agent);
    }

    @Test
    public void getCurrentStateShouldBeCalledThreeTimes() {
        agent.executeNextAction();

        // first call for state cache
        // second call for state update
        // third call in collectActionFromSource
        verify(environment, times(3)).getCurrentState();
    }

    @Test
    public void getCurrentAverageRewardShouldReturnOneIfStepSizeAndRewardIsOne() {
        agent.executeNextAction();

        assertEquals(1.0, agent.getCurrentAverageReward());
    }

    @Test
    public void actionShouldBeTransformedExactlyOnce() {
        agent.executeNextAction();

        verify(agent, times(1)).collectActionFromSource();
    }

    @Test
    public void actionShouldBeExecutedExactlyOnce() {
        agent.executeNextAction();

        verify(environment, times(1)).executeAction(any(), anyInt());
        verify(agent, times(1)).executeTransformedAction(any());
    }

    @Test
    public void sourceShouldBeCriticisedExactlyOnce() {
        agent.executeNextAction();

        verify(source, times(1)).criticiseAction(anyInt(), anyInt(), anyInt(), anyDouble());
    }

}
