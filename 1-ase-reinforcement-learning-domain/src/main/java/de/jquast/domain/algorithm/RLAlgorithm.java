package de.jquast.domain.algorithm;

import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

public abstract class RLAlgorithm implements ActionSource {

    protected ActionValueStore actionValueStoreDelegate;
    protected ActionSource actionSourceDelegate;
    protected final double learningRate;

    public RLAlgorithm(ActionValueStore actionValueStore, ActionSource actionSource, double learningDate) {
        this.actionValueStoreDelegate = actionValueStore;
        this.actionSourceDelegate = actionSource;
        this.learningRate = learningDate;
    }
}
