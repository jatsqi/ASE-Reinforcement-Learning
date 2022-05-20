package de.jquast.plugin.repository;

import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.domain.algorithm.impl.QLearning;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAlgorithmRepository implements RLAlgorithmRepository {

    private static final Map<String, RLAlgorithmDescriptor> ALGORITHMS;

    static {
        ALGORITHMS = new HashMap<>();

        String qLearning = "qlearning";
        ALGORITHMS.put(qLearning, new RLAlgorithmDescriptor(
                qLearning,
                "Off Policy Lernalgorithmus",
                QLearning.class
        ));
    }

    @Override
    public Collection<RLAlgorithmDescriptor> getAlgorithms() {
        return ALGORITHMS.values();
    }

    @Override
    public Optional<RLAlgorithmDescriptor> getAlgorithm(String name) {
        return Optional.ofNullable(ALGORITHMS.get(name));
    }

}
