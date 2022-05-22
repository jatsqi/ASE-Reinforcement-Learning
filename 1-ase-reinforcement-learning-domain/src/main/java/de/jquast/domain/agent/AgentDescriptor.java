package de.jquast.domain.agent;

import de.jquast.domain.shared.Action;

import java.util.Objects;

public record AgentDescriptor(
        String name,
        String description,
        Action[] requiredCapabilities,
        int actionSpace) {

    @Override
    public String toString() {
        return String.format("%-20s: %s", name, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentDescriptor that = (AgentDescriptor) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
