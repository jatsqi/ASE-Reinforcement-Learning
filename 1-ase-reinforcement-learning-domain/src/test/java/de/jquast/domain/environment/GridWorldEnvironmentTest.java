package de.jquast.domain.environment;

import de.jquast.domain.environment.impl.GridWorldEnvironment;
import de.jquast.domain.shared.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GridWorldEnvironmentTest {

    private GridWorldEnvironment environment;
    private int[][] grid;


    @BeforeEach
    void prepare() {
        grid = new int[2][3];
        grid[0][0] = GridWorldEnvironment.STATE_TERMINAL;
        grid[1][0] = GridWorldEnvironment.STATE_SPAWN;
        grid[0][1] = GridWorldEnvironment.STATE_NORMAL;
        grid[1][1] = GridWorldEnvironment.STATE_BOMB;
        grid[0][2] = GridWorldEnvironment.STATE_FORBIDDEN;
        grid[1][2] = GridWorldEnvironment.STATE_FORBIDDEN;

        environment = Mockito.spy(new GridWorldEnvironment(grid));
    }

    @Test
    void constructorShouldDetermineCorrectAttributes() {
        // Spawn Pos
        assertEquals(1, environment.getCurrentState());

        // Metadata
        assertEquals(2, environment.getWidth());
        assertEquals(3, environment.getHeight());
        assertEquals(6, environment.getStateSpace());
    }

    @Test
    void actionsShouldTransitionEnvironmentToNewState() {
        execActionAndCompareState(Action.MOVE_X_DOWN, 0);
        execActionAndCompareState(Action.MOVE_Y_UP, 2);
        execActionAndCompareState(Action.MOVE_Y_DOWN, 0);
        execActionAndCompareState(Action.MOVE_X_UP, 1);
        execActionAndCompareState(Action.DO_NOTHING, 1);
    }

    @Test
    void unsupportedActionsShouldReturnFalseAndNotChangeState() {
        execInvalidActionAndCompareState(Action.PULL);
        execInvalidActionAndCompareState(Action.MOVE_Z_UP);
        execInvalidActionAndCompareState(Action.MOVE_Z_UP);
    }

    @Test
    void negativeRewardShouldBeCorrectOnFields() {
        assertEquals(-0.01, environment.getReward());

        environment.executeAction(Action.MOVE_Y_UP, 1);
        assertEquals(-5.0, environment.getReward());
    }

    @Test
    void positiveRewardShouldBeCorrectOnTerminalField() {
        environment.executeAction(Action.MOVE_X_DOWN, 1);
        assertEquals(5.0, environment.getReward());
    }

    @Test
    void positionShouldResetOnTerminalOrBombState() {
        environment.executeAction(Action.MOVE_X_DOWN, 1);
        assertEquals(0, environment.getCurrentState());
        environment.tick();
        assertEquals(1, environment.getCurrentState());

        environment.executeAction(Action.MOVE_Y_UP, 1);
        assertEquals(3, environment.getCurrentState());
        environment.tick();
        assertEquals(1, environment.getCurrentState());
    }

    @Test
    void environmentShouldNotBeAbleToMoveToForbiddenState() {
        environment.executeAction(Action.MOVE_X_DOWN, 1);
        environment.executeAction(Action.MOVE_Y_UP, 1);
        assertEquals(2, environment.getCurrentState());

        environment.executeAction(Action.MOVE_Y_UP, 1);
        assertEquals(2, environment.getCurrentState());
    }

    @Test
    void environmentShouldNotBeAbleToMoveOutOfGrid() {
        environment.executeAction(Action.MOVE_X_UP, 1);
        assertEquals(1, environment.getCurrentState());

        environment.executeAction(Action.MOVE_X_UP, 1);
        assertEquals(1, environment.getCurrentState());
    }

    private void execActionAndCompareState(Action action, int expectedState) {
        assertTrue(environment.executeAction(action, 1));
        assertEquals(expectedState, environment.getCurrentState());
    }

    private void execInvalidActionAndCompareState(Action action) {
        int preState = environment.getCurrentState();
        environment.executeAction(action, 1);

        assertEquals(preState, environment.getCurrentState());
    }

}
