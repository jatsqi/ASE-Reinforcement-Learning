package de.jquast.domain.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Optional;

public interface PolicyFactory {

    /**
     * Erstellt eine Policy mit Namen {@code name}.
     *
     * @param name     Der Name der Policy.
     * @param store    Die Value-Store der Policy.
     * @param settings Die Reinforcement Learning Einstellungen.
     * @return Eine Policy mit entsprechendem Name, ein leeres Optional andernfalls.
     */
    Optional<Policy> createPolicy(String name, ActionValueStore store, RLSettings settings);

    /**
     * Erstellt eine Policy, die stets versucht, den maximalen Reward zu generieren.
     *
     * @param store    Die Value-Store der Policy.
     * @param settings Die Reinforcement Learning Einstellungen.
     * @return Eine Policy mit entsprechendem Name, ein leeres Optional andernfalls.
     */
    Optional<Policy> createMaximizingPolicy(ActionValueStore store, RLSettings settings);

}
