package de.jquast.domain.algorithm.impl;

import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

/**
 * Grundlegender Algorithmus, der Teil der Domäne ist.
 * Dies stellt GRUNDLEGENDE Funktionalität dar, egal wie die Applikation konkret aussieht oder welche Use-Cases diese
 * beinhaltet.
 */
public class QLearning extends RLAlgorithm {

    public static final RLAlgorithmDescriptor Q_LEARNING_ALGORITHM_DESCRIPTOR = new RLAlgorithmDescriptor(
            "qlearning",
            "Off Policy Lernalgorithmus",
            QLearning.class
    );

    public QLearning(ActionValueStore actionValueStore, ActionSource actionSource, RLSettings settings) {
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

        ActionValueStore.ActionValueEntry maxEntry = actionValueStoreDelegate.getMaxActionValue(newState);

        double oldValue = actionValueStoreDelegate.getActionValue(oldState, action);
        double newFutureEstimate = maxEntry.value();
        double newEstimate = oldValue + settings.learningRate() * (reward + settings.discountFactor() * newFutureEstimate - oldValue);

        actionValueStoreDelegate.setActionValue(oldState, action, newEstimate);
    }
}
