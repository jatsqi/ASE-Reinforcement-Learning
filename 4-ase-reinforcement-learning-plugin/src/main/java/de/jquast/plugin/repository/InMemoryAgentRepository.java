package de.jquast.plugin.repository;

import de.jquast.application.service.ConfigService;
import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.agent.AgentRepository;
import de.jquast.domain.agent.impl.FlatMovingPullAgent;
import de.jquast.domain.agent.impl.MovingAgent2D;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.exception.AgentCreationException;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAgentRepository implements AgentRepository {

    private static final Map<String, AgentDescriptor> AGENTS;

    static {
        AGENTS = new HashMap<>();

        AGENTS.put(FlatMovingPullAgent.PULL_AGENT_DESCRIPTOR.name(), FlatMovingPullAgent.PULL_AGENT_DESCRIPTOR);
        AGENTS.put(MovingAgent2D.MOVING_AGENT_DESCRIPTOR.name(), MovingAgent2D.MOVING_AGENT_DESCRIPTOR);
    }

    private final ConfigService configService;
    private final AgentFactory agentFactory;

    @Inject
    public InMemoryAgentRepository(ConfigService configService, AgentFactory agentFactory) {
        this.configService = configService;
        this.agentFactory = agentFactory;
    }

    @Override
    public Collection<AgentDescriptor> getAgentInfos() {
        return AGENTS.values();
    }

    @Override
    public Optional<AgentDescriptor> getAgentInfo(String name) {
        return Optional.ofNullable(AGENTS.get(name));
    }

    @Override
    public Agent createAgentInstance(AgentDescriptor descriptor, Environment environment, ActionSource source) throws AgentCreationException {
        Optional<Agent> agent = agentFactory.createAgent(descriptor, environment, source, configService.getRLSettings());
        if (agent.isEmpty())
            throw new AgentCreationException("Fehler beim Erstellen des Agenten '%s'.", descriptor.name());

        return agent.get();
    }

}
