package de.jquast.domain.environment;

import java.util.Map;
import java.util.Optional;

public interface EnvironmentFactory {

    Optional<Environment> createEnvironment(EnvironmentDescriptor descriptor, Map<String, Object> parameters);

}
