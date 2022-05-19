package de.jquast.plugin.factory;

import de.jquast.domain.algorithm.AlgorithmFactory;
import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.plugin.algorithm.QLearning;

import java.util.Optional;

public class SimpleAlgorithmFactory implements AlgorithmFactory {

    @Override
    public Optional<RLAlgorithm> createAlgorithm(String name, ActionValueStore store, ActionSource delegate, RLSettings settings) {
        RLAlgorithm algorithm = switch (name) {
            case "qlearning" -> createQLearning(store, delegate, settings);
            default -> null;
        };

        return Optional.ofNullable(algorithm);
    }

    private QLearning createQLearning(ActionValueStore store, ActionSource delegate, RLSettings settings) {
        return new QLearning(store, delegate, settings);
    }
}
