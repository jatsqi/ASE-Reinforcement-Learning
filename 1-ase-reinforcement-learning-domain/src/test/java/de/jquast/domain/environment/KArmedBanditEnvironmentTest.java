package de.jquast.domain.environment;

import de.jquast.domain.environment.impl.KArmedBanditEnvironment;
import de.jquast.domain.shared.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KArmedBanditEnvironmentTest {

    private KArmedBanditEnvironment environment;

    @BeforeEach
    void prepare() {
        environment = new KArmedBanditEnvironment(10);
    }

    /**
     * In der Theorie ein nicht deterministischer Test, da er auf dem Zufall der Belohnungswerte innerhalb von
     * KArmedBanditEnvironment beruht.
     */
    @Test
    void allPrecomputedValuesShouldBeBetween1AndMinus1() {
        for (double r : environment.getPrecomputedBanditRewards()) {
            assertTrue(r <= 1 && r >= -1);
        }
    }

    @Test
    void stateSpaceShouldMatchBanditCountTimesTwo() {
        assertEquals(20, environment.getStateSpace());
    }

    @Test
    void initialStateShouldAlwaysBeZero() {
        assertEquals(0, environment.getCurrentState());
    }

    @Test
    void banditCountShouldBeTen() {
        assertEquals(10, environment.getBanditCount());
    }

    @Test
    void environmentShouldTransitionToNextStateOnMoveAction() {
        executeActionAndCheckState(Action.MOVE_X_UP, 1);
        executeActionAndCheckState(Action.MOVE_X_UP, 2);
        executeActionAndCheckState(Action.MOVE_X_DOWN, 1);
    }

    @Test
    void pullActionShouldTransitionEnvironmentToStateBeyondBanditCount() {
        executeActionAndCheckState(Action.PULL, 10);
    }

    @Test
    void environmentTickInTerminalStateShouldResetStateToBanditNumber() {
        executeActionAndCheckState(Action.PULL, 10);
        environment.tick();
        assertEquals(0, environment.getCurrentState());
    }

    @Test
    void unsupportedActionShouldNotChangeState() {
        environment.executeAction(Action.MOVE_Z_DOWN, 1);
        assertEquals(0, environment.getCurrentState());
    }

    @Test
    void environmentMoveActionShouldNotLeaveBoundary() {
        executeActionAndCheckState(Action.MOVE_X_DOWN, 0);
        executeActionAndCheckState(Action.MOVE_X_UP, 1);
        executeActionAndCheckState(Action.MOVE_X_DOWN, 0);
        executeActionAndCheckState(Action.MOVE_X_DOWN, 0);
    }

    @Test
    void getRewardShouldMatchPrecomputedArray() {
        int moveNTimesRight = environment.getStateSpace() / 2 - 1;

        for (int i = 0; i < moveNTimesRight; i++) {
            executeActionAndCheckState(Action.PULL, i + environment.getBanditCount());
            assertEquals(environment.getPrecomputedBanditRewards()[i], environment.getReward());

            environment.tick();
            executeActionAndCheckState(Action.MOVE_X_UP, i + 1);
        }
    }

    @Test
    void allNonTerminalStatesShouldHaveRewardOfZero() {
        int moveNTimesRight = environment.getStateSpace() / 2 - 1;
        for (int i = 0; i < moveNTimesRight; i++) {
            environment.executeAction(Action.MOVE_X_UP, 1);
            assertEquals(0, environment.getReward());
        }
    }

    private void executeActionAndCheckState(Action action, int expectedState) {
        environment.executeAction(action, 1);
        assertEquals(expectedState, environment.getCurrentState());
    }

}
