package de.jquast.application.fake;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.visualizer.PolicyVisualizer;
import de.jquast.domain.policy.visualizer.VisualizationFormat;

public class FakeBestVisualizer extends PolicyVisualizer {
    public FakeBestVisualizer(Agent agent, Policy policy, Environment environment) {
        super(agent, policy, environment);
    }

    @Override
    public byte[] visualize(VisualizationFormat format) {
        return new byte[0];
    }
}
