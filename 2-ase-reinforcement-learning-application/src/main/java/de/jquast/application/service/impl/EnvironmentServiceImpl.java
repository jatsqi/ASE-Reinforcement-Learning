package de.jquast.application.service.impl;

import de.jquast.application.service.EnvironmentService;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.Optional;

public class EnvironmentServiceImpl implements EnvironmentService {

    private final EnvironmentRepository environmentRepository;

    @Inject
    public EnvironmentServiceImpl(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    public Collection<EnvironmentDescriptor> getEnvironmentsInfo() {
        return environmentRepository.getEnvironments();
    }

    public Optional<EnvironmentDescriptor> getEnvironmentInfo(String name) {
        return environmentRepository.getEnvironment(name);
    }

}
