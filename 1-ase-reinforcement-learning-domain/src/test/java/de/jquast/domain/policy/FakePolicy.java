package de.jquast.domain.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionValueStore;

public class FakePolicy extends Policy {

    public FakePolicy(ActionValueStore actionValueStore, RLSettings settings) {
        super(actionValueStore, settings);
    }

    public FakePolicy(int stateCount, int actionCount, RLSettings settings) {
        super(stateCount, actionCount, settings);
    }

    @Override
    public int selectAction(int state) {
        return 0;
    }

    @Override
    public void criticiseAction(int oldState, int action, int newState, double reward) {

    }
}
