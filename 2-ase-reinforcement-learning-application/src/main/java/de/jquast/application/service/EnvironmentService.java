package de.jquast.application.service;

import de.jquast.application.exception.EnvironmentCreationException;
import de.jquast.application.exception.StartAgentTrainingException;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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

    public Environment createEnvironment(
            String envName, Optional<String> envOptions, Optional<String> initFromFile) throws EnvironmentCreationException {
        Optional<EnvironmentDescriptor> environmentDescriptorOp = getEnvironmentInfo(envName);
        if (environmentDescriptorOp.isEmpty())
            throw new EnvironmentCreationException("Das Environment '%s' konnte nicht gefunden werden.", envName);

        EnvironmentDescriptor environmentDescriptor = environmentDescriptorOp.get();

        // Create & Check Environment
        Map<String, String> envOptionsMap = new HashMap<>();
        if (envOptions.isPresent()) {
            envOptionsMap = parseEnvOptions(envOptions.get());

            if (initFromFile.isPresent()) {
                envOptionsMap.put("from", initFromFile.get());
            }
        }

        System.out.println("HELLO FROM MAP " + envOptions.get());

        Optional<Environment> environmentOp = environmentFactory.createEnvironment(environmentDescriptor, envOptionsMap);
        if (environmentOp.isEmpty())
            throw new EnvironmentCreationException("Beim Erstellen des Environments '%s' ist ein Fehler aufgetreten. Bitte Parameter überprüfen!", envName);

        // Unwrap Environment
        return environmentOp.get();
    }

    private Map<String, String> parseEnvOptions(String envOptions) {
        Map<String, String> result = new HashMap<>();

        for (String s : envOptions.split(";")) {
            String[] parts = s.split("=");
            if (parts.length != 2)
                continue;

            result.put(parts[0], parts[1]);
        }

        return result;
    }

}
