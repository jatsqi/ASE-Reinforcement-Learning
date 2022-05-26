package de.jquast.domain.factory;

import de.jquast.domain.algorithm.AlgorithmFactory;
import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.algorithm.impl.QLearning;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleAlgorithmFactory implements AlgorithmFactory {

    private static Map<String, AlgorithmConstructor> ALGORITHM_CONSTRUCTORS;

    static {
        ALGORITHM_CONSTRUCTORS = new HashMap<>();

        ALGORITHM_CONSTRUCTORS.put("qlearning", (descriptor, store, delegate, settings) -> new QLearning(store, delegate, settings));
    }

    @Override
    public Optional<RLAlgorithm> createAlgorithm(RLAlgorithmDescriptor descriptor, ActionValueStore store, ActionSource delegate, RLSettings settings) {
        RLAlgorithm algorithm = null;
        if (ALGORITHM_CONSTRUCTORS.containsKey(descriptor.name()))
            algorithm = ALGORITHM_CONSTRUCTORS.get(descriptor.name()).constructAlgorithm(descriptor, store, delegate, settings);

        return Optional.ofNullable(algorithm);
    }

    private interface AlgorithmConstructor {
        RLAlgorithm constructAlgorithm(RLAlgorithmDescriptor descriptor, ActionValueStore store, ActionSource delegate, RLSettings settings);
    }
}
