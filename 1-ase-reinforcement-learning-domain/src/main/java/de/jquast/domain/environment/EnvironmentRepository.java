package de.jquast.domain.environment;

import java.util.Collection;
import java.util.Optional;

public interface EnvironmentRepository {

    Collection<EnvironmentDescriptor> getEnvironments();

    Optional<EnvironmentDescriptor> getEnvironment(String name);

}
