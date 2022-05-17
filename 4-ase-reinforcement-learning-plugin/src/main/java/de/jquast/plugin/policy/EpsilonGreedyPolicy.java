package de.jquast.plugin.policy;

import de.jquast.domain.policy.Policy;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Random;

public class EpsilonGreedyPolicy extends Policy {

    private static final Random RANDOM = new Random();

    private double epsilon;

    public EpsilonGreedyPolicy(ActionValueStore actionValueStore, double epsilon) {
        super(actionValueStore);

        this.epsilon = epsilon;
    }

    public EpsilonGreedyPolicy(int stateCount, int actionCount, double epsilon) {
        super(stateCount, actionCount);

        this.epsilon = epsilon;
    }

    @Override
    public int selectAction(int state) {
        if (epsilon > RANDOM.nextDouble()) {
            return RANDOM.nextInt(getActionValueStore().getActionCount());
        }

        ActionValueStore.ActionValueEntry maxActionValue = getActionValueStore().getMaxActionValue(state);
        return maxActionValue.action();
    }

    public double getEpsilon() {
        return epsilon;
    }
}
