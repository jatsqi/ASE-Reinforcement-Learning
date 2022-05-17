package de.jquast.domain.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

public abstract class Policy implements ActionSource {

    private ActionValueStore actionValueStore;
    private final RLSettings settings;

    public Policy(ActionValueStore actionValueStore, RLSettings settings) {
        this.actionValueStore = actionValueStore;
        this.settings = settings;
    }

    public Policy(int stateCount, int actionCount, RLSettings settings) {
        this(new ActionValueStore(stateCount, actionCount), settings);
    }

    public abstract int selectAction(int state);

    public ActionValueStore getActionValueStore() {
        return actionValueStore;
    }

    public RLSettings getSettings() {
        return settings;
    }
}
