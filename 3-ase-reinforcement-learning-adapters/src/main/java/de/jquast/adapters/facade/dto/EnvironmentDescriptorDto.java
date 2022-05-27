package de.jquast.adapters.facade.dto;

public record EnvironmentDescriptorDto(String name,
                                       String description,
                                       ActionDto[] supportedCapabilities) {
    @Override
    public String toString() {
        return String.format("%-20s: %s", name, description);
    }
}
