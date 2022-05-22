package de.jquast.domain.agent.impl;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

import java.util.HashMap;
import java.util.Map;

public class FlatMovingPullAgent extends Agent {

    private static Map<Integer, Action> ACTION_INT_TO_ACTION_MAPPING;

    static {
        ACTION_INT_TO_ACTION_MAPPING = new HashMap<>();

        ACTION_INT_TO_ACTION_MAPPING.put(0, Action.DO_NOTHING);
        ACTION_INT_TO_ACTION_MAPPING.put(1, Action.MOVE_X_UP);
        ACTION_INT_TO_ACTION_MAPPING.put(2, Action.MOVE_X_DOWN);
        ACTION_INT_TO_ACTION_MAPPING.put(3, Action.PULL);
    }

    public FlatMovingPullAgent(Environment environment, ActionSource source, RLSettings settings) {
        super(environment, source, settings);
    }

    @Override
    protected ActionDataPair transformAction(int action) {
        return new ActionDataPair(ACTION_INT_TO_ACTION_MAPPING.get(action), 1);
    }

}
