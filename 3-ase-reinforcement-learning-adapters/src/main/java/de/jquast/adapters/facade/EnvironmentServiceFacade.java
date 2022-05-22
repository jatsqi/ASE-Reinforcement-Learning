package de.jquast.adapters.facade;

import de.jquast.adapters.facade.dto.EnvironmentDescriptorDto;

import java.util.Collection;
import java.util.Optional;

public interface EnvironmentServiceFacade {

    Collection<EnvironmentDescriptorDto> getEnvironmentInfos();

    Optional<EnvironmentDescriptorDto> getEnvironmentInfo(String name);

}
