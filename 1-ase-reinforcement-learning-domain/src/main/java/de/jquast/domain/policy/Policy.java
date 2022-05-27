package de.jquast.domain.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

public abstract class Policy implements ActionSource {

    private final RLSettings settings;
    private ActionValueStore actionValueStore;

    protected Policy(ActionValueStore actionValueStore, RLSettings settings) {
        this.actionValueStore = actionValueStore;
        this.settings = settings;
    }

    protected Policy(int stateCount, int actionCount, RLSettings settings) {
        this(new ActionValueStore(stateCount, actionCount), settings);
    }

    @Override
    public int selectBestAction(int state) {
        return actionValueStore.getMaxActionValue(state).action();
    }

    public ActionValueStore getActionValueStore() {
        return actionValueStore;
    }

    public RLSettings getSettings() {
        return settings;
    }
}
