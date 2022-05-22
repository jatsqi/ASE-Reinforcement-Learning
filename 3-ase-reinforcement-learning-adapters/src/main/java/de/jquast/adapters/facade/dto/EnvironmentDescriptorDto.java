package de.jquast.adapters.facade.dto;

public record EnvironmentDescriptorDto(String name,
                                       String description,
                                       ActionDto[] supportedCapabilities) {
}
