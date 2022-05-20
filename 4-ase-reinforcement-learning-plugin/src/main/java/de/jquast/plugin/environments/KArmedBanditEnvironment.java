package de.jquast.plugin.environments;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;

import java.util.Random;

public class KArmedBanditEnvironment implements Environment {

    private static final Random RND = new Random();

    private int lastTriggeredBandit = 0;
    private int banditCount;
    private int currentState;
    private double[] precomputedBanditRewards;

    public KArmedBanditEnvironment(int banditCount) {
        this.banditCount = banditCount;
        this.precomputedBanditRewards = new double[banditCount];
        this.currentState = 0;

        recomputeRewards();
    }

    @Override
    public boolean executeAction(Action action, int data) {
        if (!action.equals(Action.PULL)) {
            return false;
        }

        if (data >= banditCount || data < 0) {
            return false;
        }

        lastTriggeredBandit = data;
        currentState = 1;
        return true;
    }

    @Override
    public int getCurrentState() {
        return currentState;
    }

    @Override
    public int getStateSpace() {
        return 2;
    }

    @Override
    public void tick() {
        currentState = 0;
    }

    @Override
    public double getReward() {
        return precomputedBanditRewards[lastTriggeredBandit];
    }

    private void recomputeRewards() {
        for (int i = 0; i < banditCount; ++i) {
            precomputedBanditRewards[i] = -1 + 2 * RND.nextDouble(); // Werte im Bereich [-1, 1]
        }
    }
}
