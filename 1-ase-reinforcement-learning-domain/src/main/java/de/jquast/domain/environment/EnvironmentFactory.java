package de.jquast.domain.environment;

import de.jquast.domain.exception.EnvironmentCreationException;

import java.util.Map;
import java.util.Optional;

public interface EnvironmentFactory {

    Optional<Environment> createEnvironment(EnvironmentDescriptor descriptor, Map<String, String> parameters) throws EnvironmentCreationException;

}
