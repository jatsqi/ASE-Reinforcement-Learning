package de.jquast.plugin.environments;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;

public class KArmedBandit implements Environment {

    @Override
    public boolean executeAction(Action action, int data) {
        return false;
    }

    @Override
    public int getCurrentState() {
        return 0;
    }

    @Override
    public double getReward() {
        return 0;
    }
}
