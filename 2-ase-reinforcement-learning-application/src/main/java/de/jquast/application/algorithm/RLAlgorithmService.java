package de.jquast.application.algorithm;

import de.jquast.domain.algorithm.RLAlgorithm;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.Optional;

public class RLAlgorithmService {

    private final RLAlgorithmRepository algorithmRepository;

    @Inject
    public RLAlgorithmService(RLAlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    public Collection<RLAlgorithm> getAlgorithms() {
        return algorithmRepository.getAlgorithms();
    }

    public Optional<RLAlgorithm> getAlgorithm(String name) {
        return algorithmRepository.getAlgorithm(name);
    }
}
