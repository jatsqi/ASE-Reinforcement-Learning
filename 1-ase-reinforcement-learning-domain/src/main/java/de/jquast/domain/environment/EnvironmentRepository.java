package de.jquast.domain.environment;

import de.jquast.domain.exception.EnvironmentCreationException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface EnvironmentRepository {

    Collection<EnvironmentDescriptor> getEnvironments();

    Optional<EnvironmentDescriptor> getEnvironment(String name);

    Environment createEnvironmentInstance(EnvironmentDescriptor descriptor, Optional<String> options) throws EnvironmentCreationException;

}
