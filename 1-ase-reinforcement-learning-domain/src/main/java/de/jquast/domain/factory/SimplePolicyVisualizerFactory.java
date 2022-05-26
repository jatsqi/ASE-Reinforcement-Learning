package de.jquast.domain.factory;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.impl.GridWorldEnvironment;
import de.jquast.domain.environment.impl.KArmedBanditEnvironment;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.visualizer.PolicyVisualizer;
import de.jquast.domain.visualizer.PolicyVisualizerFactory;
import de.jquast.domain.visualizer.impl.GridWorldVisualizer;
import de.jquast.domain.visualizer.impl.KArmedBanditPolicyVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimplePolicyVisualizerFactory implements PolicyVisualizerFactory {

    private static Map<Class<?>, VisualizerConstructor> CONSTRUCTORS;

    static {
        CONSTRUCTORS = new HashMap<>();

        CONSTRUCTORS.put(GridWorldEnvironment.class, GridWorldVisualizer::new);
        CONSTRUCTORS.put(KArmedBanditEnvironment.class, KArmedBanditPolicyVisualizer::new);
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

}
