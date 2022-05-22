package de.jquast.application.service;

import de.jquast.domain.agent.AgentDescriptor;

import java.util.Collection;
import java.util.Optional;

public interface AgentService {

    Collection<AgentDescriptor> getAgents();

    Optional<AgentDescriptor> getAgent(String name);

}
