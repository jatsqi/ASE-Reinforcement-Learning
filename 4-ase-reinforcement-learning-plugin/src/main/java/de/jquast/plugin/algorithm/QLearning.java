package de.jquast.plugin.algorithm;

import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.policy.Policy;

public class QLearning implements RLAlgorithm {

    @Override
    public String getName() {
        return "qlearning";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Policy getPolicy() {
        return null;
    }

    @Override
    public int getTimeSteps() {
        return 0;
    }
}
