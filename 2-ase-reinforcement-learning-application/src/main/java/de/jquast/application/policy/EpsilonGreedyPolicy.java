package de.jquast.application.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyDescriptor;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Random;

public class EpsilonGreedyPolicy extends Policy {

    public static final PolicyDescriptor EPSILON_GREEDY_POLICY_DESCRIPTOR = new PolicyDescriptor(
            "epsilon-greedy",
            "Eine Policy, die mit einer Wahrscheinlichkeit von Epsilon eine zufällige Aktion ausführt."
    );

    private static final Random RANDOM = new Random();

    public EpsilonGreedyPolicy(ActionValueStore actionValueStore, RLSettings settings) {
        super(actionValueStore, settings);

    }

    public EpsilonGreedyPolicy(int stateCount, int actionCount, RLSettings settings) {
        super(stateCount, actionCount, settings);
    }

    @Override
    public int selectAction(int state) {
        if (getSettings().explorationRate() > RANDOM.nextDouble()) {
            return RANDOM.nextInt(getActionValueStore().getActionCount());
        }

        ActionValueStore.ActionValueEntry maxActionValue = getActionValueStore().getMaxActionValue(state);
        return maxActionValue.action();
    }

    @Override
    public void criticiseAction(int oldState, int action, int newState, double reward) {
        // Nichts machen
    }
}
