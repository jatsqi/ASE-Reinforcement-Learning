package de.jquast.domain.factory;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.agent.impl.MovingAgent2D;
import de.jquast.domain.agent.impl.FlatMovingPullAgent;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.ActionSource;

import java.util.Optional;

public class SimpleAgentFactory implements AgentFactory {

    @Override
    public Optional<Agent> createAgent(AgentDescriptor descriptor, Environment environment, ActionSource source, RLSettings settings) {
        Agent agent = switch (descriptor.name()) {
            case "pull" -> createPullAgent(environment, source, settings);
            case "2d-moving-agent" -> create2DMoveAgent(environment, source, settings);
            default -> null;
        };

        return Optional.ofNullable(agent);
    }

    private FlatMovingPullAgent createPullAgent(Environment environment, ActionSource source, RLSettings settings) {
        return new FlatMovingPullAgent(environment, source, settings);
    }

    private MovingAgent2D create2DMoveAgent(Environment environment, ActionSource source, RLSettings settings) {
        return new MovingAgent2D(environment, source, settings);
    }
}
