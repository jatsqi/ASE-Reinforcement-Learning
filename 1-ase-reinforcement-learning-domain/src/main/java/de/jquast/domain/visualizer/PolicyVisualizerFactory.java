package de.jquast.domain.visualizer;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.exception.VisualizerCreationException;
import de.jquast.domain.policy.Policy;

import java.util.Optional;

public interface PolicyVisualizerFactory {

    Optional<PolicyVisualizer> createVisualizer(Agent agent, Policy policy, Environment environment) throws VisualizerCreationException;

}
