package de.jquast.adapters.facade;

import de.jquast.adapters.facade.dto.AgentDescriptorDto;

import java.util.Collection;
import java.util.Optional;

public interface AgentServiceFacade {

    Collection<AgentDescriptorDto> getAgents();

    Optional<AgentDescriptorDto> getAgent(String name);

}
