package de.jquast.plugin.environments;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;

import java.util.Random;

public class KArmedBandit implements Environment {

    private int lastTriggeredBandit = -1;
    private int banditCount;
    private double[] precomputedBanditRewards;

    public KArmedBandit(int banditCount) {
        this.banditCount = banditCount;
        this.precomputedBanditRewards = new double[banditCount];

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
        return true;
    }

    @Override
    public int getCurrentState() {
        return 0; // Diese Umgebung hat stets den selben State
    }

    @Override
    public int getStateSpace() {
        return 1;
    }

    @Override
    public double getReward() {
        return precomputedBanditRewards[lastTriggeredBandit];
    }

    private void recomputeRewards() {
        Random r = new Random();

        for (int i = 0; i < banditCount; ++i) {
            precomputedBanditRewards[i] = -1 + 2 * r.nextDouble(); // Werte im Bereich [-1, 1]
        }
    }
}
