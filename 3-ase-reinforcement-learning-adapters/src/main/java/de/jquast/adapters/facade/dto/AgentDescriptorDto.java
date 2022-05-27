package de.jquast.adapters.facade.dto;

public record AgentDescriptorDto(String name,
                                 String description,
                                 ActionDto[] requiredCapabilities,
                                 int actionSpace) {
    @Override
    public String toString() {
        return String.format("%-20s: %s", name, description);
    }
}
