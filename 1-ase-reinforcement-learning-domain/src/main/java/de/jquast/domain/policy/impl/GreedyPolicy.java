package de.jquast.domain.policy.impl;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.policy.PolicyDescriptor;
import de.jquast.domain.shared.ActionValueStore;

public class GreedyPolicy extends EpsilonGreedyPolicy {

    public static final PolicyDescriptor GREEDY_POLICY_DESCRIPTOR = new PolicyDescriptor(
            "greedy",
            "Führt immer die aktuell Beste Aktion aus."
    );

    public GreedyPolicy(ActionValueStore actionValueStore, RLSettings settings) {
        super(actionValueStore, new RLSettings(
                settings.learningRate(),
                settings.discountFactor(),
                0, // Setze Explorations-Rate auf 0, sodass EpsilonGreedyPolicy nicht mehr erkundet.
                settings.agentRewardStepSize()
        ));
    }

    public GreedyPolicy(int stateCount, int actionCount, RLSettings settings) {
        this(new ActionValueStore(
                stateCount, actionCount
        ), settings);
    }
}
