package de.jquast.domain.agent;

import de.jquast.domain.agent.impl.MovingAgent2D;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovingAgent2DTest {

    private MovingAgent2D movingAgent;

    @Mock
    private Environment environment;

    @Mock
    private ActionSource source;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);

        movingAgent = new MovingAgent2D(environment, source, new RLSettings(
                0.0, 0.0, 0.0, 0.0
        ));
    }

    @Test
    void transformActionShouldReturnCorrectMapping() {
        assertEquals(new Agent.ActionDataPair(
                Action.DO_NOTHING, 1
        ), movingAgent.transformAction(0));

        assertEquals(new Agent.ActionDataPair(
                Action.MOVE_X_UP, 1
        ), movingAgent.transformAction(1));

        assertEquals(new Agent.ActionDataPair(
                Action.MOVE_X_DOWN, 1
        ), movingAgent.transformAction(2));

        assertEquals(new Agent.ActionDataPair(
                Action.MOVE_Y_UP, 1
        ), movingAgent.transformAction(3));

        assertEquals(new Agent.ActionDataPair(
                Action.MOVE_Y_DOWN, 1
        ), movingAgent.transformAction(4));
    }

}
