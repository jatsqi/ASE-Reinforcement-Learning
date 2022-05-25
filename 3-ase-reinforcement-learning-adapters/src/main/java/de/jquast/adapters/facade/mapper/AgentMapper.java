package de.jquast.adapters.facade.mapper;

import de.jquast.adapters.facade.dto.ActionDto;
import de.jquast.adapters.facade.dto.AgentDescriptorDto;
import de.jquast.domain.agent.AgentDescriptor;

import java.util.Arrays;
import java.util.Optional;

public class AgentMapper {

    private static final ActionMapper MAPPER = new ActionMapper();

    public AgentDescriptorDto toDto(AgentDescriptor descriptor) {
        return new AgentDescriptorDto(
                descriptor.name(),
                descriptor.description(),
                Arrays.stream(descriptor.availableCapabilities()).map(MAPPER::toDto).toList().toArray(new ActionDto[0]),
                descriptor.actionSpace()
        );
    }

    public Optional<AgentDescriptorDto> toDto(Optional<AgentDescriptor> descriptorOptional) {
        if (descriptorOptional.isEmpty())
            return Optional.empty();

        return Optional.of(toDto(descriptorOptional.get()));
    }

}
