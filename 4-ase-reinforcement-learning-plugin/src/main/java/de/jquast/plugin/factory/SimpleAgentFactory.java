package de.jquast.plugin.factory;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.ActionSource;
import de.jquast.plugin.agent.MovingAgent2D;
import de.jquast.plugin.agent.PullAgent;

import java.util.Optional;

public class SimpleAgentFactory implements AgentFactory {

    @Override
    public Optional<Agent> createAgent(String name, Environment environment, ActionSource source, RLSettings settings) {
        Agent agent = switch (name) {
            case "pull" -> createPullAgent(environment, source, settings);
            case "2d-moving-agent" -> create2DMoveAgent(environment, source, settings);
            default -> null;
        };

        return Optional.ofNullable(agent);
    }

    private PullAgent createPullAgent(Environment environment, ActionSource source, RLSettings settings) {
        return new PullAgent(environment, source, settings);
    }

    private MovingAgent2D create2DMoveAgent(Environment environment, ActionSource source, RLSettings settings) {
        return new MovingAgent2D(environment, source, settings);
    }
}
