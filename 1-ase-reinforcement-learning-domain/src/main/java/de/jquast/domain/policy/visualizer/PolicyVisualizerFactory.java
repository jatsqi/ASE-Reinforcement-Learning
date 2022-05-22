package de.jquast.domain.policy.visualizer;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.exception.VisualizerCreationException;
import de.jquast.domain.policy.Policy;

import java.util.Optional;

public interface PolicyVisualizerFactory {

    Optional<PolicyVisualizer> createVisualizer(Policy policy, Environment environment) throws VisualizerCreationException;

}
