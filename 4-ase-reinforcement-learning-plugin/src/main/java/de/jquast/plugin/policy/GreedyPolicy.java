package de.jquast.plugin.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionValueStore;

public class GreedyPolicy extends EpsilonGreedyPolicy {

    public GreedyPolicy(ActionValueStore actionValueStore, RLSettings settings) {
        super(actionValueStore, settings);
    }

    public GreedyPolicy(int stateCount, int actionCount, RLSettings settings) {
        super(stateCount, actionCount, settings);
    }
}
