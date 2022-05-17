package de.jquast.domain.environment;

public record EnvironmentDescriptor(
        String name,
        String description,
        Class<? extends Environment> clazz,
        Action[] supportedCapabilities) {
}
