package de.jquast.application.agent;

import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.agent.AgentRepository;

import java.util.Collection;
import java.util.Optional;

public class AgentService implements AgentRepository {

    private final AgentRepository repository;

    public AgentService(AgentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<AgentDescriptor> getAgents() {
        return repository.getAgents();
    }

    @Override
    public Optional<AgentDescriptor> getAgent(String name) {
        return repository.getAgent(name);
    }
}
