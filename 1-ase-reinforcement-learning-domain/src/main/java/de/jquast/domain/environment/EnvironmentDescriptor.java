package de.jquast.domain.environment;

import de.jquast.domain.shared.Action;

public record EnvironmentDescriptor(
        String name,
        String description,
        Class<? extends Environment> clazz,
        Action[] supportedCapabilities) {

    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }
}
