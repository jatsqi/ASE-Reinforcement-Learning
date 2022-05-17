package de.jquast.plugin.repository;

import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.domain.shared.Action;
import de.jquast.plugin.algorithm.QLearning;
import de.jquast.plugin.environments.KArmedBandit;

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
                "Spielautomaten",
                KArmedBandit.class,
                new Action[] {
                        Action.DO_NOTHING,
                        Action.PULL
                }
        ));
    }

    @Override
    public Collection<EnvironmentDescriptor> getEnvironments() {
        return null;
    }

    @Override
    public Optional<EnvironmentDescriptor> getEnvironment(String name) {
        return Optional.empty();
    }
}
