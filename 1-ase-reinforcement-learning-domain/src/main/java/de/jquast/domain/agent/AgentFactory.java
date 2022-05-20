package de.jquast.domain.agent;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.ActionSource;

import java.util.Optional;

public interface AgentFactory {

    Optional<Agent> createAgent(AgentDescriptor descriptor, Environment environment, ActionSource source, RLSettings settings);

}
