package de.jquast.application.service.impl;

import de.jquast.application.service.AgentService;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentRepository;

import java.util.Collection;
import java.util.Optional;

public class AgentServiceImpl implements AgentService {

    private final AgentRepository repository;

    public AgentServiceImpl(AgentRepository repository) {
        this.repository = repository;
    }

    public Collection<AgentDescriptor> getAgents() {
        return repository.getAgentInfos();
    }

    public Optional<AgentDescriptor> getAgent(String name) {
        return repository.getAgentInfo(name);
    }
}
