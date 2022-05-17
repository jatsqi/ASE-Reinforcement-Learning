package de.jquast.plugin.algorithm;

import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

public class QLearning extends RLAlgorithm {

    public QLearning(ActionValueStore actionValueStore, ActionSource actionSource, RLSettings settings) {
        super(actionValueStore, actionSource, settings);
    }

    @Override
    public int selectAction(int state) {
        return actionSourceDelegate.selectAction(state);
    }

    @Override
    public void criticiseAction(int oldState, int action, int newState, double reward) {
        ActionValueStore.ActionValueEntry maxEntry = actionValueStoreDelegate.getMaxActionValue(newState);

        double oldValue = actionValueStoreDelegate.getActionValue(oldState, action);
        double newFutureEstimate = maxEntry.value();
        double newEstimate = oldValue + settings.learningRate() * (reward + settings.discountFactor() * newFutureEstimate - oldValue);

        actionValueStoreDelegate.setActionValue(oldState, action, newEstimate);
    }
}
