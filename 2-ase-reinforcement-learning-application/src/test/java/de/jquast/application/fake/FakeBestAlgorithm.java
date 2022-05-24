package de.jquast.application.fake;

import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

public class FakeBestAlgorithm extends RLAlgorithm {
    public FakeBestAlgorithm(ActionValueStore actionValueStore, ActionSource actionSource, RLSettings settings) {
        super(actionValueStore, actionSource, settings);
    }

    @Override
    public int selectAction(int state) {
        return actionSourceDelegate.selectAction(state);
    }

    @Override
    public int selectBestAction(int state) {
        return actionSourceDelegate.selectBestAction(state);
    }

    @Override
    public void criticiseAction(int oldState, int action, int newState, double reward) {
        actionSourceDelegate.criticiseAction(oldState, action, newState, reward);
    }
}
