package de.jquast.application.service.impl;

import de.jquast.application.service.EnvironmentService;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.Optional;

public class EnvironmentServiceImpl implements EnvironmentService {

    private final EnvironmentRepository environmentRepository;
    private final EnvironmentFactory environmentFactory;

    @Inject
    public EnvironmentServiceImpl(EnvironmentRepository environmentRepository, EnvironmentFactory factory) {
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
