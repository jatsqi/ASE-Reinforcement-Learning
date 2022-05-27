package de.jquast.plugin.repository;

import de.jquast.application.service.ConfigService;
import de.jquast.domain.algorithm.AlgorithmFactory;
import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.domain.algorithm.impl.QLearning;
import de.jquast.domain.exception.AlgorithmCreationException;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAlgorithmRepository implements RLAlgorithmRepository {

    private static final Map<String, RLAlgorithmDescriptor> ALGORITHMS;

    static {
        ALGORITHMS = new HashMap<>();

        ALGORITHMS.put(QLearning.Q_LEARNING_ALGORITHM_DESCRIPTOR.name(), QLearning.Q_LEARNING_ALGORITHM_DESCRIPTOR);
    }

    private final AlgorithmFactory factory;
    private final ConfigService configService;

    public InMemoryAlgorithmRepository(AlgorithmFactory factory, ConfigService configService) {
        this.factory = factory;
        this.configService = configService;
    }

    @Override
    public Collection<RLAlgorithmDescriptor> getAlgorithmInfos() {
        return ALGORITHMS.values();
    }

    @Override
    public Optional<RLAlgorithmDescriptor> getAlgorithmInfo(String name) {
        return Optional.ofNullable(ALGORITHMS.get(name));
    }

    @Override
    public RLAlgorithmDescriptor getDefaultAlgorithmInfo() {
        return ALGORITHMS.get("qlearning");
    }

    @Override
    public RLAlgorithm createAlgorithmInstance(RLAlgorithmDescriptor descriptor, ActionValueStore store, ActionSource source) throws AlgorithmCreationException {
        Optional<RLAlgorithm> algorithm = factory.createAlgorithm(descriptor, store, source, configService.getRLSettings());
        if (algorithm.isEmpty())
            throw new AlgorithmCreationException("Fehler beim Erstellen des Algorithmus '%s'.", descriptor.name());

        return algorithm.get();
    }

}
