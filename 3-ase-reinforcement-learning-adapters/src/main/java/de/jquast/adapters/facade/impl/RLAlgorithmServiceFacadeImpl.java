package de.jquast.adapters.facade.impl;

import de.jquast.adapters.facade.RLAlgorithmServiceFacade;
import de.jquast.adapters.facade.dto.RLAlgorithmDescriptorDto;
import de.jquast.adapters.facade.mapper.AlgorithmMapper;
import de.jquast.application.service.RLAlgorithmService;

import java.util.Collection;
import java.util.Optional;

public class RLAlgorithmServiceFacadeImpl implements RLAlgorithmServiceFacade {

    private static final AlgorithmMapper MAPPER = new AlgorithmMapper();

    private final RLAlgorithmService service;

    public RLAlgorithmServiceFacadeImpl(RLAlgorithmService service) {
        this.service = service;
    }

    @Override
    public Collection<RLAlgorithmDescriptorDto> getAlgorithms() {
        return service.getAlgorithms().stream().map(MAPPER::toDto).toList();
    }

    @Override
    public Optional<RLAlgorithmDescriptorDto> getAlgorithm(String name) {
        return MAPPER.toDto(service.getAlgorithm(name));
    }
}
