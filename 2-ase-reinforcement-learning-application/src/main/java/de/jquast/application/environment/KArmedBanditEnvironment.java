package de.jquast.application.environment;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.shared.Action;

import java.util.Random;

public class KArmedBanditEnvironment implements Environment {

    public static final EnvironmentDescriptor K_ARMED_BANDIT_DESCRIPTOR = new EnvironmentDescriptor(
            "k-armed-bandit",
            "N Spieleautomaten (einarmige Banditen) mit jeweils einem Hebel.",
                        new Action[]{Action.DO_NOTHING, Action.PULL, Action.MOVE_X_UP, Action.MOVE_X_DOWN}
        );

    private static final Random RND = new Random();

    private int currentBandit = 0;
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
        if (action.equals(Action.MOVE_X_DOWN) && currentBandit - 1 >= 0)
            currentBandit -= 1;

        if (action.equals(Action.MOVE_X_UP) && currentBandit + 1 < banditCount)
            currentBandit += 1;

        if (action.equals(Action.PULL)) {
            currentState = currentBandit + banditCount;
        } else {
            currentState = currentBandit;
        }

        return true;
    }

    @Override
    public int getCurrentState() {
        return currentState;
    }

    @Override
    public int getStateSpace() {
        return banditCount * 2;
    }

    @Override
    public void tick() {
        currentState = currentBandit;
    }

    @Override
    public double getReward() {
        if (currentState >= banditCount) {
            return precomputedBanditRewards[currentState - banditCount];
        }

        return 0.0;
    }

    public int getBanditCount() {
        return banditCount;
    }

    public double[] getPrecomputedBanditRewards() {
        return precomputedBanditRewards;
    }

    private void recomputeRewards() {
        for (int i = 0; i < banditCount; ++i) {
            precomputedBanditRewards[i] = -1 + 2 * RND.nextDouble(); // Werte im Bereich [-1, 1]
        }
    }
}
