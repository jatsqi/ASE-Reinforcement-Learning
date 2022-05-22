package de.jquast.application.service;

import de.jquast.domain.environment.EnvironmentDescriptor;

import java.util.Collection;
import java.util.Optional;

public interface EnvironmentService {

    Collection<EnvironmentDescriptor> getEnvironmentsInfo();

    Optional<EnvironmentDescriptor> getEnvironmentInfo(String name);

}
