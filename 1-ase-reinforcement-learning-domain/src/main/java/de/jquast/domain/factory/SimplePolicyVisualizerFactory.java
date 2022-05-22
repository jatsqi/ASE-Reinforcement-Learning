package de.jquast.domain.factory;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.impl.GridWorldEnvironment;
import de.jquast.domain.environment.impl.KArmedBanditEnvironment;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.visualizer.PolicyVisualizer;
import de.jquast.domain.policy.visualizer.PolicyVisualizerFactory;
import de.jquast.domain.policy.visualizer.VisualizationFormat;
import de.jquast.domain.shared.Action;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class SimplePolicyVisualizerFactory implements PolicyVisualizerFactory {

    private static Map<Class<?>, VisualizerConstructor> CONSTRUCTORS;

    static {
        CONSTRUCTORS = new HashMap<>();

        CONSTRUCTORS.put(GridWorldEnvironment.class, GridWorldVisualizer::new);
        CONSTRUCTORS.put(KArmedBanditEnvironment.class, KArmedBanditVisualizer::new);
    }

    @Override
    public Optional<PolicyVisualizer> createVisualizer(Agent agent, Policy policy, Environment environment) {
        Optional<VisualizerConstructor> constructor = Optional.ofNullable(CONSTRUCTORS.get(environment.getClass()));
        if (constructor.isEmpty())
            return Optional.empty();

        return Optional.ofNullable(constructor.get().constructVisualizer(agent, policy, environment));
    }

    private interface VisualizerConstructor {
        PolicyVisualizer constructVisualizer(Agent agent, Policy policy, Environment environment);
    }

    public static class KArmedBanditVisualizer extends PolicyVisualizer {

        public KArmedBanditVisualizer(Agent agent, Policy policy, Environment environment) {
            super(agent, policy, environment);
        }

        @Override
        public byte[] visualize(VisualizationFormat format) {
            if (!format.equals(VisualizationFormat.TEXT))
                throw new UnsupportedOperationException("Dieses Format wird momentan nicht unterstützt!");

            StringBuilder pullBuilder = new StringBuilder();
            StringBuilder rewardBuilder = new StringBuilder();
            KArmedBanditEnvironment environment = (KArmedBanditEnvironment) getEnvironment();

            for (int i = 0; i < environment.getBanditCount(); i++) {
                if (agent.transformAction(policy.selectBestAction(i)).action().equals(Action.PULL)) {
                    pullBuilder.append(String.format("%-10s", "[X]"));
                } else {
                    pullBuilder.append(String.format("%-10s", "[ ]"));
                }

                rewardBuilder.append(String.format(Locale.US, "%-10.2f", environment.getPrecomputedBanditRewards()[i]));
            }
            pullBuilder.append("\n");
            pullBuilder.append(rewardBuilder.toString());

            return pullBuilder.toString().getBytes();
        }
    }

    public static class GridWorldVisualizer extends PolicyVisualizer {

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
}
