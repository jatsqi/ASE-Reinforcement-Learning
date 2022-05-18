package de.jquast.plugin.agent;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

import java.util.HashMap;
import java.util.Map;

public class MovingAgent2D extends Agent {

    private static Map<Integer, Action> ACTION_INT_TO_ACTION_MAPPING;

    static {
        ACTION_INT_TO_ACTION_MAPPING = new HashMap<>();

        ACTION_INT_TO_ACTION_MAPPING.put(0, Action.DO_NOTHING);
        ACTION_INT_TO_ACTION_MAPPING.put(1, Action.MOVE_X_UP);
        ACTION_INT_TO_ACTION_MAPPING.put(2, Action.MOVE_X_DOWN);
        ACTION_INT_TO_ACTION_MAPPING.put(3, Action.MOVE_Y_UP);
        ACTION_INT_TO_ACTION_MAPPING.put(4, Action.MOVE_Y_DOWN);
    }

    public MovingAgent2D(Environment environment, ActionSource source) {
        super(environment, source);
    }

    @Override
    protected ActionDataPair transformAction(int action) {
        return new ActionDataPair(ACTION_INT_TO_ACTION_MAPPING.get(action), 1);
    }
}
