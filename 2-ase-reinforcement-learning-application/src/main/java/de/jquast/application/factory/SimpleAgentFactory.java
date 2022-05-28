package de.jquast.application.factory;

import de.jquast.application.agent.FlatMovingPullAgent;
import de.jquast.application.agent.MovingAgent2D;
import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.ActionSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleAgentFactory implements AgentFactory {

    private static Map<String, AgentConstructor> AGENT_CONSTRUCTORS;

    static {
        AGENT_CONSTRUCTORS = new HashMap<>();

        AGENT_CONSTRUCTORS.put(
                FlatMovingPullAgent.PULL_AGENT_DESCRIPTOR.name(),
                (descriptor, environment, source, settings) -> new FlatMovingPullAgent(environment, source, settings));
        AGENT_CONSTRUCTORS.put(
                MovingAgent2D.MOVING_AGENT_DESCRIPTOR.name(),
                (descriptor, environment, source, settings) -> new MovingAgent2D(environment, source, settings));
    }

    @Override
    public Optional<Agent> createAgent(AgentDescriptor descriptor, Environment environment, ActionSource source, RLSettings settings) {
        Agent agent = null;
        if (AGENT_CONSTRUCTORS.containsKey(descriptor.name()))
            agent = AGENT_CONSTRUCTORS.get(descriptor.name()).constructAgent(descriptor, environment, source, settings);

        return Optional.ofNullable(agent);
    }

    private interface AgentConstructor {
        Agent constructAgent(AgentDescriptor descriptor, Environment environment, ActionSource source, RLSettings settings);
    }
}
