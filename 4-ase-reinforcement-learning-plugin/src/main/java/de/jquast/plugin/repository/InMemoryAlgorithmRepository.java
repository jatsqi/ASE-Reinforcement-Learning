package de.jquast.plugin.repository;

import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.plugin.algorithm.QLearning;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAlgorithmRepository implements RLAlgorithmRepository {

    private static final Map<String, RLAlgorithm> ALGORITHMS;

    static {
        ALGORITHMS = new HashMap<>();

        QLearning learning = new QLearning();
        ALGORITHMS.put(learning.getName(), learning);
    }

    @Override
    public Collection<RLAlgorithm> getAlgorithms() {
        return ALGORITHMS.values();
    }

    @Override
    public Optional<RLAlgorithm> getAlgorithm(String name) {
        return Optional.ofNullable(ALGORITHMS.get(name));
    }

}
