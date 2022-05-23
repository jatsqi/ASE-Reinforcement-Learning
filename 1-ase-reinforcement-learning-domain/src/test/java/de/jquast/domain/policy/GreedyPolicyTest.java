package de.jquast.domain.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.policy.impl.EpsilonGreedyPolicy;
import de.jquast.domain.policy.impl.GreedyPolicy;
import de.jquast.domain.shared.ActionValueStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreedyPolicyTest {

    private GreedyPolicy policy;

    @BeforeEach
    public void prepare() {
        ActionValueStore store = new ActionValueStore(
                new double[][]{
                        { 0.0, 1.0, 2.0 },
                        { 0.0, 3.0, 1.0 },
                        { -1.0, -2.0, -4.0 }
                }
        );

        policy = new GreedyPolicy(store, new RLSettings(
                0.0, 0.0, 1000, 0.0
        ));
    }

    @Test
    public void policyShouldAlwaysSelectBestAction() {
        assertEquals(2, policy.selectAction(0));
        assertEquals(1, policy.selectAction(1));
        assertEquals(0, policy.selectAction(2));
    }

    @Test
    public void emptyGreedyPolicyShouldAlwaysSelectFirstAction() {
        EpsilonGreedyPolicy emptyPolicy = new GreedyPolicy(3, 3, new RLSettings(
                0.0, 0.0, 100, 0.0
        ));

        assertEquals(0, emptyPolicy.selectAction(0));
        assertEquals(0, emptyPolicy.selectAction(1));
        assertEquals(0, emptyPolicy.selectAction(2));
    }

}
