package de.jquast.domain.policy;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Optional;

public interface PolicyFactory {

    Optional<Policy> createPolicy(String name, ActionValueStore store, RLSettings settings);

}
