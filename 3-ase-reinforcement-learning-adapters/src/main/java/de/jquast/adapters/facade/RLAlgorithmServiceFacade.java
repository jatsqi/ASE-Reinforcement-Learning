package de.jquast.adapters.facade;

import de.jquast.adapters.facade.dto.RLAlgorithmDescriptorDto;

import java.util.Collection;
import java.util.Optional;

public interface RLAlgorithmServiceFacade {
    Collection<RLAlgorithmDescriptorDto> getAlgorithms();

    Optional<RLAlgorithmDescriptorDto> getAlgorithm(String name);
}
