package de.jquast.domain.agent;

import de.jquast.domain.environment.Action;

public record AgentDescriptor(String name, String description, Class<? extends Agent> clazz, Action[] capabilities) {
}
