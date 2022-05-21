package de.jquast.domain.factory;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.impl.GridWorldEnvironment;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.visualizer.PolicyVisualizer;
import de.jquast.domain.policy.visualizer.PolicyVisualizerFactory;
import de.jquast.domain.policy.visualizer.VisualizationFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimplePolicyVisualizerFactory implements PolicyVisualizerFactory {

    private static Map<Class<?>, VisualizerConstructor> CONSTRUCTORS;

    static {
        CONSTRUCTORS = new HashMap<>();

        CONSTRUCTORS.put(GridWorldEnvironment.class, GridWorldVisualizer::new);
    }

    @Override
    public Optional<PolicyVisualizer> createVisualizer(Policy policy, Environment environment) {
        Optional<VisualizerConstructor> constructor = Optional.ofNullable(CONSTRUCTORS.get(environment.getClass()));
        if (constructor.isEmpty())
            return Optional.empty();

        return Optional.ofNullable(constructor.get().constructVisualizer(policy, environment));
    }

    private interface VisualizerConstructor {
        PolicyVisualizer constructVisualizer(Policy policy, Environment environment);
    }

    public static class GridWorldVisualizer extends PolicyVisualizer {

        public GridWorldVisualizer(Policy policy, Environment environment) {
            super(policy, environment);
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
                    builder.append("⚐ ");
                    continue;
                }

                builder.append(actionToDirection(policy.selectBestAction(i)));
                builder.append(" ");
            }

            return builder.toString().getBytes();
        }

        private char actionToDirection(int action) {
            return switch (action) {
                case 0 -> '-';
                case 1 -> 'E';
                case 2 -> 'W';
                case 3 -> 'S';
                case 4 -> 'N';
                default -> '?';
            };
        }
    }
}
