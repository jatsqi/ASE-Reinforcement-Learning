package de.jquast.domain.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.exception.PolicyCreationException;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Optional;

public interface PolicyFactory {

    /**
     * Erstellt eine Policy mit Namen {@code name}.
     *
     * @param descriptor Die Beschreibung der Policy.
     * @param store      Die Value-Store der Policy.
     * @param settings   Die Reinforcement Learning Einstellungen.
     * @return Eine Policy mit entsprechendem Name, ein leeres Optional andernfalls.
     */
    Optional<Policy> createPolicy(PolicyDescriptor descriptor, ActionValueStore store, RLSettings settings) throws PolicyCreationException;

    /**
     * Erstellt eine Policy, die stets versucht, den maximalen Reward zu generieren.
     *
     * @param store    Die Value-Store der Policy.
     * @param settings Die Reinforcement Learning Einstellungen.
     * @return Eine Policy mit entsprechendem Name, ein leeres Optional andernfalls.
     */
    Optional<Policy> createMaximizingPolicy(ActionValueStore store, RLSettings settings) throws PolicyCreationException;

}
