package de.jquast.adapters.facade.impl;

import de.jquast.adapters.facade.AgentServiceFacade;
import de.jquast.adapters.facade.dto.AgentDescriptorDto;
import de.jquast.adapters.facade.mapper.AgentMapper;
import de.jquast.application.service.AgentService;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class AgentServiceFacadeImpl implements AgentServiceFacade {

    private static final AgentMapper MAPPER = new AgentMapper();

    private final AgentService service;

    @Inject
    public AgentServiceFacadeImpl(AgentService service) {
        this.service = service;
    }

    @Override
    public Collection<AgentDescriptorDto> getAgents() {
        return service.getAgents().stream().map(MAPPER::toDto).toList();
    }

    @Override
    public Optional<AgentDescriptorDto> getAgent(String name) {
        return MAPPER.toDto(service.getAgent(name));
    }
}
