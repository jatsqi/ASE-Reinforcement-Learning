package de.jquast.plugin.algorithm;

import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

public class QLearning extends RLAlgorithm {

    private double discountFactor = 1.0;

    public QLearning(ActionValueStore actionValueStore, ActionSource actionSource, double learningRate, double discountFactor) {
        super(actionValueStore, actionSource, learningRate);

        this.discountFactor = discountFactor;
    }

    @Override
    public int selectAction(int state) {
        return actionSourceDelegate.selectAction(state);
    }

    @Override
    public void criticiseAction(int oldState, int action, int newState, double reward) {
        double highestValue = Double.MIN_VALUE;
        int hAction = 0;
        double[] prevEstimates = actionValueStoreDelegate.getActionValues(newState);

        for (int i = 0; i < prevEstimates.length; ++i) {
            if (prevEstimates[i] > highestValue) {
                highestValue = prevEstimates[i];
                hAction = i;
            }
        }

        double oldValue = actionValueStoreDelegate.getActionValue(oldState, action);
        double newFutureEstimate = actionValueStoreDelegate.getActionValue(newState, hAction);
        double newEstimate = oldValue * learningRate * (reward + discountFactor * newFutureEstimate - oldValue);
        actionValueStoreDelegate.setActionValue(oldState, action, newEstimate);
    }
}
