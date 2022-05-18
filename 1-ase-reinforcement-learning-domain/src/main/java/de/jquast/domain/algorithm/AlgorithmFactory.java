package de.jquast.domain.algorithm;

import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Optional;

public interface AlgorithmFactory {

    Optional<RLAlgorithm> createAlgorithm(String name, ActionValueStore store, ActionSource delegate, RLSettings settings);

}
