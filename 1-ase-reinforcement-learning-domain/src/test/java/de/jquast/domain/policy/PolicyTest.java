package de.jquast.domain.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionValueStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class PolicyTest {

    private ActionValueStore store;
    private RLSettings settings;
    private Policy policy;

    @BeforeEach
    void prepare() {
        store = new ActionValueStore(new double[][]{
                { 0.0, 1.0, 2.0 },
                { 0.0, 3.0, 1.0 },
                { -1.0, -2.0, -4.0 }
        });

        settings = new RLSettings(
                0.0, 0.0, 0.0, 0.0
        );

        policy = new FakePolicy(store, settings);
    }

    @Test
    void selectBestActionShouldReturnMaximizedAction() {
        assertEquals(2, policy.selectBestAction(0));
        assertEquals(1, policy.selectBestAction(1));
        assertEquals(0, policy.selectBestAction(2));
    }

    @Test
    void detailedConstructorShouldInitStoreCorrectly() {
        FakePolicy policy = new FakePolicy(10, 9, settings);
        assertEquals(9, policy.getActionValueStore().getActionCount());
        assertEquals(10, policy.getActionValueStore().getStateCount());
    }

    @Test
    void allGetterShouldReturnIdentity() {
        assertSame(settings, policy.getSettings());
        assertSame(store, policy.getActionValueStore());
    }

}
