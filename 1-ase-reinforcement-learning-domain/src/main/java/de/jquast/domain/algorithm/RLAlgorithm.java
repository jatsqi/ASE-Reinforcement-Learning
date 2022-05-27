package de.jquast.domain.algorithm;

import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

public abstract class RLAlgorithm implements ActionSource {

    protected final RLSettings settings;
    protected ActionValueStore actionValueStoreDelegate;
    protected ActionSource actionSourceDelegate;

    protected RLAlgorithm(ActionValueStore actionValueStore, ActionSource actionSource, RLSettings settings) {
        this.actionValueStoreDelegate = actionValueStore;
        this.actionSourceDelegate = actionSource;
        this.settings = settings;
    }

    public RLSettings getSettings() {
        return settings;
    }

}
