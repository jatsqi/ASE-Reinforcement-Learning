package de.jquast.application.service;

import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.Optional;

public class EnvironmentService {

    private final EnvironmentRepository environmentRepository;

    @Inject
    public EnvironmentService(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    public Collection<EnvironmentDescriptor> getEnvironments() {
        return environmentRepository.getEnvironments();
    }

    public Optional<EnvironmentDescriptor> getEnvironment(String name) {
        return environmentRepository.getEnvironment(name);
    }

}
