package de.jquast.domain.agent;

import de.jquast.domain.shared.Action;

public record AgentDescriptor(
        String name,
        String description,
        Class<? extends Agent> clazz,
        Action[] requiredCapabilities,
        int actionSpace) {
}
