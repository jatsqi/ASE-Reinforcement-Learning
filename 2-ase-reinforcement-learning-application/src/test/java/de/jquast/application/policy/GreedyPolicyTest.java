package de.jquast.application.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionValueStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreedyPolicyTest {

    private GreedyPolicy policy;

    @BeforeEach
    void prepare() {
        ActionValueStore store = new ActionValueStore(
                new double[][]{
                        {0.0, 1.0, 2.0},
                        {0.0, 3.0, 1.0},
                        {-1.0, -2.0, -4.0}
                }
        );

        policy = new GreedyPolicy(store, new RLSettings(
                0.0, 0.0, 1, 0.0
        ));
    }

    @Test
    void policyShouldAlwaysSelectBestAction() {
        assertEquals(2, policy.selectAction(0));
        assertEquals(1, policy.selectAction(1));
        assertEquals(0, policy.selectAction(2));
    }

    @Test
    void emptyGreedyPolicyShouldAlwaysSelectFirstAction() {
        EpsilonGreedyPolicy emptyPolicy = new GreedyPolicy(3, 3, new RLSettings(
                0.0, 0.0, 1, 0.0
        ));

        assertEquals(0, emptyPolicy.selectAction(0));
        assertEquals(0, emptyPolicy.selectAction(1));
        assertEquals(0, emptyPolicy.selectAction(2));
    }

}
