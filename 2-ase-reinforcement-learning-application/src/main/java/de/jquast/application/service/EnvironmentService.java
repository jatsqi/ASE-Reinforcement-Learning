package de.jquast.application.service;

import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.Optional;

public class EnvironmentService {

    private final EnvironmentRepository environmentRepository;
    private final EnvironmentFactory environmentFactory;

    @Inject
    public EnvironmentService(EnvironmentRepository environmentRepository, EnvironmentFactory factory) {
        this.environmentRepository = environmentRepository;
        this.environmentFactory = factory;
    }

    public Collection<EnvironmentDescriptor> getEnvironmentsInfo() {
        return environmentRepository.getEnvironments();
    }

    public Optional<EnvironmentDescriptor> getEnvironmentInfo(String name) {
        return environmentRepository.getEnvironment(name);
    }

}
