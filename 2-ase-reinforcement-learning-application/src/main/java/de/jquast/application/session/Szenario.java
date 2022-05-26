package de.jquast.application.session;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.visualizer.PolicyVisualizer;

public record Szenario(
        DescriptorBundle metadata,
        Agent agent,
        Environment environment,
        Policy policy,
        PolicyVisualizer visualizer,
        long maxSteps,
        RLSettings settings) {
}
