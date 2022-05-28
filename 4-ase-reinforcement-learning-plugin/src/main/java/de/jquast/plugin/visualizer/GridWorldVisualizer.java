package de.jquast.plugin.visualizer;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.application.environment.GridWorldEnvironment;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.shared.Action;
import de.jquast.domain.visualizer.PolicyVisualizer;
import de.jquast.domain.visualizer.VisualizationFormat;

public class GridWorldVisualizer extends PolicyVisualizer {

    public GridWorldVisualizer(Agent agent, Policy policy, Environment environment) {
        super(agent, policy, environment);
    }

    @Override
    public byte[] visualize(VisualizationFormat format) {
        if (!format.equals(VisualizationFormat.TEXT))
            throw new UnsupportedOperationException("Dieses Format wird momentan nicht unterstützt!");

        StringBuilder builder = new StringBuilder();
        GridWorldEnvironment gridWorld = (GridWorldEnvironment) environment;

        for (int i = 0; i < gridWorld.getStateSpace(); i++) {
            if (i > 0 && i % gridWorld.getWidth() == 0) {
                builder.append("\n");
            }

            if (gridWorld.getGrid()[i % gridWorld.getWidth()][i / gridWorld.getWidth()] == GridWorldEnvironment.STATE_TERMINAL) {
                builder.append("G ");
                continue;
            }

            builder.append(actionToDirection(agent.transformAction(policy.selectBestAction(i)).action()));
            builder.append(" ");
        }

        return builder.toString().getBytes();
    }

    private char actionToDirection(Action action) {
        return switch (action) {
            case DO_NOTHING -> '-';
            case MOVE_X_UP -> 'E';
            case MOVE_X_DOWN -> 'W';
            case MOVE_Y_UP -> 'S';
            case MOVE_Y_DOWN -> 'N';
            default -> '?';
        };
    }
}
