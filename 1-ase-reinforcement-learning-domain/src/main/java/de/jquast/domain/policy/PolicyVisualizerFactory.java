package de.jquast.domain.policy;

import de.jquast.domain.environment.Environment;

import java.util.Optional;

public interface PolicyVisualizerFactory {

    Optional<PolicyVisualizer> createVisualizer(Policy policy, Environment environment);

}
