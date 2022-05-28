package de.jquast.application.agent;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

import java.util.HashMap;
import java.util.Map;

public class MovingAgent2D extends Agent {

    public static final AgentDescriptor MOVING_AGENT_DESCRIPTOR = new AgentDescriptor(
            "2d-moving-agent",
                "Agent, der sich auf einer 2D-Ebene fortbewegen kann",
                        new Action[]{Action.MOVE_X_UP, Action.MOVE_X_UP, Action.MOVE_Y_UP, Action.MOVE_Y_DOWN, Action.DO_NOTHING},
            5);

    private static Map<Integer, Action> ACTION_INT_TO_ACTION_MAPPING;

    static {
        ACTION_INT_TO_ACTION_MAPPING = new HashMap<>();

        ACTION_INT_TO_ACTION_MAPPING.put(0, Action.DO_NOTHING);
        ACTION_INT_TO_ACTION_MAPPING.put(1, Action.MOVE_X_UP);
        ACTION_INT_TO_ACTION_MAPPING.put(2, Action.MOVE_X_DOWN);
        ACTION_INT_TO_ACTION_MAPPING.put(3, Action.MOVE_Y_UP);
        ACTION_INT_TO_ACTION_MAPPING.put(4, Action.MOVE_Y_DOWN);
    }

    public MovingAgent2D(Environment environment, ActionSource source, RLSettings settings) {
        super(environment, source, settings);
    }

    @Override
    public ActionDataPair transformAction(int action) {
        return new ActionDataPair(ACTION_INT_TO_ACTION_MAPPING.get(action), 1);
    }
}
