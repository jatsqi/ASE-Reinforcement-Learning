package de.jquast.plugin.factory;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.ActionSource;
import de.jquast.plugin.agent.MovingAgent2D;
import de.jquast.plugin.agent.PullAgent;

import java.util.Optional;

public class SimpleAgentFactory implements AgentFactory {

    @Override
    public Optional<Agent> createAgent(String name, Environment environment, ActionSource source) {
        Agent agent = switch (name) {
            case "pull" -> createPullAgent(environment, source);
            case "2d-moving-agent" -> create2DMoveAgent(environment, source);
            default -> null;
        };

        return Optional.of(agent);
    }

    private PullAgent createPullAgent(Environment environment, ActionSource source) {
        return new PullAgent(environment, source);
    }

    private MovingAgent2D create2DMoveAgent(Environment environment, ActionSource source) {
        return new MovingAgent2D(environment, source);
    }
}
