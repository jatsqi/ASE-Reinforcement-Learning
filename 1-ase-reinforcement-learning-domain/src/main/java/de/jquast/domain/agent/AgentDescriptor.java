package de.jquast.domain.agent;

import de.jquast.domain.shared.Action;

import java.util.Arrays;

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
}
