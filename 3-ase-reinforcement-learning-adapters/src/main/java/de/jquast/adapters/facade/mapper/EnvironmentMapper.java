package de.jquast.adapters.facade.mapper;

import de.jquast.adapters.facade.dto.ActionDto;
import de.jquast.adapters.facade.dto.EnvironmentDescriptorDto;
import de.jquast.domain.environment.EnvironmentDescriptor;

import java.util.Arrays;
import java.util.Optional;

public class EnvironmentMapper {

    private static final ActionMapper MAPPER = new ActionMapper();

    public EnvironmentDescriptorDto toDto(EnvironmentDescriptor descriptor) {
        return new EnvironmentDescriptorDto(
                descriptor.name(),
                descriptor.description(),
                Arrays.stream(descriptor.requiredCapabilities()).map(MAPPER::toDto).toList().toArray(new ActionDto[0])
        );
    }

    public Optional<EnvironmentDescriptorDto> toDto(Optional<EnvironmentDescriptor> descriptor) {
        if (descriptor.isEmpty())
            return Optional.empty();

        return Optional.of(toDto(descriptor.get()));
    }

}
