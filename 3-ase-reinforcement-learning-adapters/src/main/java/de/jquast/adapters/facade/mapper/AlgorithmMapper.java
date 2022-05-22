package de.jquast.adapters.facade.mapper;

import de.jquast.adapters.facade.dto.RLAlgorithmDescriptorDto;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;

import java.util.Optional;

public class AlgorithmMapper {

    public RLAlgorithmDescriptorDto toDto(RLAlgorithmDescriptor descriptor) {
        return new RLAlgorithmDescriptorDto(descriptor.name(), descriptor.description());
    }

    public Optional<RLAlgorithmDescriptorDto> toDto(Optional<RLAlgorithmDescriptor> descriptor) {
        if (descriptor.isEmpty())
            return Optional.empty();

        return Optional.of(toDto(descriptor.get()));
    }

}
