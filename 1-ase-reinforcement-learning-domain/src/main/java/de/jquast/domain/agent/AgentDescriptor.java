package de.jquast.domain.agent;

import de.jquast.domain.shared.Action;

import java.util.Arrays;
import java.util.Objects;

public record AgentDescriptor(
        String name,
        String description,
        Class<? extends Agent> clazz,
        Action[] requiredCapabilities,
        int actionSpace) {

    public static int AGENT_ACTION_SPACE_MATCHES_STATE_SPACE = -1;

    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
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
