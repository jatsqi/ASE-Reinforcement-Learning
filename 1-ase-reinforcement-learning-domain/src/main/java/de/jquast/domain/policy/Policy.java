package de.jquast.domain.policy;

import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

public abstract class Policy implements ActionSource {

    private ActionValueStore actionValueStore;

    public Policy(ActionValueStore actionValueStore) {
        this.actionValueStore = actionValueStore;
    }

    public Policy(int stateCount, int actionCount) {
        this(new ActionValueStore(stateCount, actionCount));
    }

    public abstract int selectAction(int state);

    public ActionValueStore getActionValueStore() {
        return actionValueStore;
    }
}
