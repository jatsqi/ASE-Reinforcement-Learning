package de.jquast.domain.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionValueStoreTest {

    private ActionValueStore store;

    @BeforeEach
    void prepareStore() {
        double[][] valueStore = new double[][]{
                {1.0, 0.0},
                {0.0, 1.0},
                {0.0, 0.0}
        };

        store = new ActionValueStore(valueStore);
    }

    @Test
    void actionValueStoreIntsConstructorShouldInitValuesCorrectly() {
        ActionValueStore store = new ActionValueStore(3, 2);

        assertEquals(2, store.getActionCount());
        assertEquals(3, store.getStateCount());
    }

    @Test
    void actionValueStoreArrayConstructorShouldInitValuesCorrectly() {
        assertEquals(2, store.getActionCount());
        assertEquals(3, store.getStateCount());
    }

    @Test
    void getActionValuesShouldReturnCorrectStateArray() {
        assertArrayEquals(new double[]{
                1.0, 0.0
        }, store.getActionValues(0));

        assertArrayEquals(new double[]{
                0.0, 1.0
        }, store.getActionValues(1));

        assertArrayEquals(new double[]{
                0.0, 0.0
        }, store.getActionValues(2));
    }

    @Test
    void getActionValueShouldReturnCorrectValue() {
        assertEquals(1.0, store.getActionValue(0, 0));
        assertEquals(0.0, store.getActionValue(0, 1));
    }

    @Test
    void setActionValueShouldSetCorrectValues() {
        store.setActionValue(0, 0, 5.0);

        assertEquals(5.0, store.getActionValue(0, 0));
        assertEquals(0.0, store.getActionValue(0, 1));
    }

    @Test
    void resetShouldSetAllValuesToZero() {
        store.reset();

        for (int i = 0; i < store.getStateCount(); i++) {
            for (int j = 0; j < store.getActionCount(); j++) {
                assertEquals(0, store.getActionValue(i, j));
            }
        }
    }

    @Test
    void getMaxActionValueShouldReturnMaximumEntryOfState() {
        checkMaxValue(0, 0, 1.0);
        checkMaxValue(1, 1, 1.0);
        checkMaxValue(2, 0, 0.0);
    }

    private void checkMaxValue(int state, int expectedAction, double expectedValue) {
        ActionValueStore.ActionValueEntry entry = store.getMaxActionValue(state);

        assertEquals(state, entry.state());
        assertEquals(expectedValue, entry.value());
        assertEquals(expectedAction, entry.action());
    }

}
