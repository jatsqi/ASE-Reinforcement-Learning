package de.jquast.plugin.repository;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.domain.exception.EnvironmentCreationException;
import de.jquast.domain.shared.Action;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryEnvironmentRepository implements EnvironmentRepository {

    private static final Map<String, EnvironmentDescriptor> ENVIRONMENTS;

    static {
        ENVIRONMENTS = new HashMap<>();

        String kArmedBandit = "k-armed-bandit";
        ENVIRONMENTS.put(kArmedBandit, new EnvironmentDescriptor(
                kArmedBandit,
                "N Spieleautomaten (einarmige Banditen) mit jeweils einem Hebel.",
                new Action[]{
                        Action.DO_NOTHING,
                        Action.PULL
                }
        ));

        String gridWorld = "grid-world";
        ENVIRONMENTS.put(gridWorld, new EnvironmentDescriptor(
                gridWorld,
                "Eine Welt bestehend aus Kacheln",
                new Action[]{
                        Action.DO_NOTHING,
                        Action.MOVE_X_DOWN, Action.MOVE_X_UP,
                        Action.MOVE_Y_DOWN, Action.MOVE_Y_UP
                }
        ));
    }

    private final EnvironmentFactory factory;

    @Inject
    public InMemoryEnvironmentRepository(EnvironmentFactory factory) {
        this.factory = factory;
    }

    @Override
    public Collection<EnvironmentDescriptor> getEnvironments() {
        return ENVIRONMENTS.values();
    }

    @Override
    public Optional<EnvironmentDescriptor> getEnvironment(String name) {
        return Optional.ofNullable(ENVIRONMENTS.get(name));
    }

    @Override
    public Environment createEnvironmentInstance(EnvironmentDescriptor descriptor, Optional<String> options) throws EnvironmentCreationException {
        // Create & Check Environment
        Map<String, String> envOptionsMap = new HashMap<>();
        if (options.isPresent()) {
            envOptionsMap = parseEnvOptions(options.get());
        }

        Optional<Environment> environmentOp = factory.createEnvironment(descriptor, envOptionsMap);
        if (environmentOp.isEmpty())
            throw new EnvironmentCreationException("Beim Erstellen des Environments '%s' ist ein Fehler aufgetreten. Bitte Parameter überprüfen!", descriptor.name());

        // Unwrap Environment
        return environmentOp.get();
    }

    private Map<String, String> parseEnvOptions(String envOptions) {
        Map<String, String> result = new HashMap<>();

        for (String s : envOptions.split(",")) {
            String[] parts = s.split("=");
            if (parts.length != 2)
                continue;

            result.put(parts[0], parts[1]);
        }

        return result;
    }
}
