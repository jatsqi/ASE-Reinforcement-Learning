package de.jquast.domain.agent;

import de.jquast.domain.agent.impl.FlatMovingPullAgent;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlatMovingPullAgentTest {

    private FlatMovingPullAgent pullAgent;

    @Mock
    private Environment environment;

    @Mock
    private ActionSource source;

    @BeforeEach
    public void prepare() {
        MockitoAnnotations.openMocks(this);

        pullAgent = new FlatMovingPullAgent(environment, source, new RLSettings(
                0.0, 0.0, 0.0, 0.0
        ));
    }

    @Test
    public void transformActionShouldReturnCorrectMapping() {
        assertEquals(new Agent.ActionDataPair(
                Action.DO_NOTHING, 1
        ), pullAgent.transformAction(0));

        assertEquals(new Agent.ActionDataPair(
                Action.MOVE_X_UP, 1
        ), pullAgent.transformAction(1));

        assertEquals(new Agent.ActionDataPair(
                Action.MOVE_X_DOWN, 1
        ), pullAgent.transformAction(2));

        assertEquals(new Agent.ActionDataPair(
                Action.PULL, 1
        ), pullAgent.transformAction(3));
    }

}
