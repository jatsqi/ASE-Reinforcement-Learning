package de.jquast.application.service;

import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentRepository;

import java.util.Collection;
import java.util.Optional;

public class AgentService {

    private final AgentRepository repository;

    public AgentService(AgentRepository repository) {
        this.repository = repository;
    }

    public Collection<AgentDescriptor> getAgents() {
        return repository.getAgentInfos();
    }

    public Optional<AgentDescriptor> getAgent(String name) {
        return repository.getAgentInfo(name);
    }
}
