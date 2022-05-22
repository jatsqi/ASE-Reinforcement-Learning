package de.jquast.adapters.facade.impl;

import de.jquast.adapters.facade.EnvironmentServiceFacade;
import de.jquast.adapters.facade.dto.EnvironmentDescriptorDto;
import de.jquast.adapters.facade.mapper.EnvironmentMapper;
import de.jquast.application.service.EnvironmentService;

import java.util.Collection;
import java.util.Optional;

public class EnvironmentServiceFacadeImpl implements EnvironmentServiceFacade {

    private static final EnvironmentMapper MAPPER = new EnvironmentMapper();

    private final EnvironmentService service;

    public EnvironmentServiceFacadeImpl(EnvironmentService service) {
        this.service = service;
    }

    @Override
    public Collection<EnvironmentDescriptorDto> getEnvironmentInfos() {
        return service.getEnvironmentsInfo().stream().map(MAPPER::toDto).toList();
    }

    @Override
    public Optional<EnvironmentDescriptorDto> getEnvironmentInfo(String name) {
        return MAPPER.toDto(service.getEnvironmentInfo(name));
    }
}
