package de.jquast.domain.agent;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.exception.AgentCreationException;
import de.jquast.domain.shared.ActionSource;

import java.util.Collection;
import java.util.Optional;

public interface AgentRepository {

    Collection<AgentDescriptor> getAgentInfos();

    Optional<AgentDescriptor> getAgentInfo(String name);

    Agent createAgentInstance(AgentDescriptor descriptor, Environment environment, ActionSource source) throws AgentCreationException;

}
