package de.jquast.application.fake;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.shared.ActionValueStore;

public class FakeBestPolicy extends Policy {
    public FakeBestPolicy(ActionValueStore actionValueStore, RLSettings settings) {
        super(actionValueStore, settings);
    }

    public FakeBestPolicy(int stateCount, int actionCount, RLSettings settings) {
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
