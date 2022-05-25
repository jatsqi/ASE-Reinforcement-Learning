package de.jquast.domain.algorithm;

import de.jquast.domain.algorithm.impl.QLearning;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class QLearningTest {

    private RLSettings settings;
    private QLearning learning;
    private ActionValueStore store;

    @Mock
    private ActionSource source;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);

        settings = new RLSettings(
                1.0, 1.0, 0.0, 0.0
        );
        store = new ActionValueStore(new double[][]{
                { 0.0, 2.1, 0.3 }, // State 1
                { 0.2, -3.1, -0.2 }, // State 2
                { 0.0, -2.1, 0.2 } // State 3
        });
        learning = new QLearning(store, source, settings);
    }

    @Test
    void selectActionShouldDelegateCall() {
        learning.selectAction(0);
        learning.selectAction(1);
        learning.selectAction(2);

        verify(source, times(1)).selectAction(0);
        verify(source, times(1)).selectAction(1);
        verify(source, times(1)).selectAction(2);
    }

    @Test
    void selectBestActionShouldDelegateCall() {
        learning.selectBestAction(0);
        learning.selectBestAction(1);
        learning.selectBestAction(2);

        verify(source, times(1)).selectBestAction(0);
        verify(source, times(1)).selectBestAction(1);
        verify(source, times(1)).selectBestAction(2);
    }

    @Test
    void criticiseActionShouldDelegateCall() {
        learning.criticiseAction(0, 0, 0, 0.0);

        verify(source, times(1)).criticiseAction(0, 0, 0, 0.0);
    }

    @Test
    void learningShouldAdjustOldStateActionPairCorrectly() {
        // !IMPORTANT! Learning Rate is 1 and Discount Factor is 1, so it should reach target instantly
        learning.criticiseAction(0, 0, 1, 3.141);
        assertEquals(3.341, store.getActionValue(0, 0));

        learning.criticiseAction(1, 2, 2, 0);
        assertEquals(0.2, store.getActionValue(1, 2));
    }

}
