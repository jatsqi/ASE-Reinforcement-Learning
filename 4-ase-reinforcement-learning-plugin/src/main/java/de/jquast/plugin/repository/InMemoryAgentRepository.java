package de.jquast.plugin.repository;

import de.jquast.application.service.impl.ConfigService;
import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.agent.AgentRepository;
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

        String pullAgent = "pull";
        AGENTS.put(pullAgent, new AgentDescriptor(
                pullAgent,
                "Agent, der an Hebeln ziehen kann",
                new Action[]{Action.DO_NOTHING, Action.PULL},
                AgentDescriptor.AGENT_ACTION_SPACE_MATCHES_STATE_SPACE
        ));

        String movingAgent2d = "2d-moving-agent";
        AGENTS.put(movingAgent2d, new AgentDescriptor(
                movingAgent2d,
                "Agent, der sich auf einer 2D-Ebene fortbewegen kann",
                new Action[]{Action.DO_NOTHING, Action.PULL},
                5
        ));
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
