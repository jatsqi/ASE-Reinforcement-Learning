package de.jquast.adapters.facade.dto;

public record AgentDescriptorDto(String name,
                                 String description,
                                 String[] requiredCapabilities,
                                 int actionSpace) {
}
