package de.jquast.plugin.algorithm;

import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.shared.Action;

public class QLearning extends RLAlgorithm {

    public QLearning(Policy basePolicy) {
        super(basePolicy);
    }

    @Override
    public int selectAction(int state) {
        return 0;
    }

    @Override
    public void criticiseAction(int oldState, int action, int newState, double reward) {

    }
}
