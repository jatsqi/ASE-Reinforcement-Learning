package de.jquast.domain.agent;

import java.util.Collection;
import java.util.Optional;

public interface AgentRepository {

    Collection<AgentDescriptor> getAgents();

    Optional<AgentDescriptor> getAgent(String name);

}
