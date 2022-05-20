package de.jquast.domain.policy.impl;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionValueStore;

public class GreedyPolicy extends EpsilonGreedyPolicy {

    public GreedyPolicy(ActionValueStore actionValueStore, RLSettings settings) {
        super(actionValueStore, new RLSettings(
                settings.learningRate(),
                settings.discountFactor(),
                0, // Setze Explorations-Rate auf 0, sodass EpsilonGreedyPolicy nicht mehr erkundet.
                settings.agentRewardStepSize()
        ));
    }

    public GreedyPolicy(int stateCount, int actionCount, RLSettings settings) {
        super(stateCount, actionCount, settings);
    }
}
