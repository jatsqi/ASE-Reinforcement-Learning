package de.jquast.plugin.repository;

import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.domain.shared.Action;
import de.jquast.plugin.environments.GridWorldEnvironment;
import de.jquast.plugin.environments.KArmedBanditEnvironment;

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

    @Override
    public Collection<EnvironmentDescriptor> getEnvironments() {
        return ENVIRONMENTS.values();
    }

    @Override
    public Optional<EnvironmentDescriptor> getEnvironment(String name) {
        return Optional.ofNullable(ENVIRONMENTS.get(name));
    }
}
