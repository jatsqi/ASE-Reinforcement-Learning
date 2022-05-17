package de.jquast.plugin.repository;

import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentRepository;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.domain.shared.Action;
import de.jquast.plugin.agent.PullAgent;
import de.jquast.plugin.algorithm.QLearning;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAgentRepository implements AgentRepository {

    private static final Map<String, AgentDescriptor> AGENTS;

    static {
        AGENTS = new HashMap<>();

        String pullAgent = "pull";
        AGENTS.put(pullAgent, new AgentDescriptor(
                pullAgent,
                "Agent, der an Hebeln ziehen kann",
                PullAgent.class,
                new Action[]{ Action.DO_NOTHING, Action.PULL },
                AgentDescriptor.AGENT_ACTION_SPACE_MATCHES_STATE_SPACE
        ));
    }

    @Override
    public Collection<AgentDescriptor> getAgents() {
        return AGENTS.values();
    }

    @Override
    public Optional<AgentDescriptor> getAgent(String name) {
        return Optional.ofNullable(AGENTS.get(name));
    }

}
