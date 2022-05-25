package de.jquast.domain.environment;

import de.jquast.domain.shared.Action;

import java.util.Objects;

public record EnvironmentDescriptor(
        String name,
        String description,
        // Was ist erforderlich, um das Environment sinnvoll(!) nutzen zu können.
        Action[] requiredCapabilities) {

    @Override
    public String toString() {
        return String.format("%-20s: %s", name, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvironmentDescriptor that = (EnvironmentDescriptor) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
