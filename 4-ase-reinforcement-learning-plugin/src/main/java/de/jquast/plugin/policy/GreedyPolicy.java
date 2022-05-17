package de.jquast.plugin.policy;

import de.jquast.domain.shared.ActionValueStore;

public class GreedyPolicy extends EpsilonGreedyPolicy {

    public GreedyPolicy(ActionValueStore actionValueStore) {
        super(actionValueStore, 0);
    }

    public GreedyPolicy(int stateCount, int actionCount) {
        super(stateCount, actionCount, 0);
    }
}
