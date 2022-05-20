package de.jquast.domain.factory;

import de.jquast.domain.algorithm.AlgorithmFactory;
import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.domain.algorithm.impl.QLearning;

import java.util.Optional;

public class SimpleAlgorithmFactory implements AlgorithmFactory {

    @Override
    public Optional<RLAlgorithm> createAlgorithm(RLAlgorithmDescriptor descriptor, ActionValueStore store, ActionSource delegate, RLSettings settings) {
        RLAlgorithm algorithm = switch (descriptor.name()) {
            case "qlearning" -> createQLearning(store, delegate, settings);
            default -> null;
        };

        return Optional.ofNullable(algorithm);
    }

    private QLearning createQLearning(ActionValueStore store, ActionSource delegate, RLSettings settings) {
        return new QLearning(store, delegate, settings);
    }
}
