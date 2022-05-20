package de.jquast.application.service;

import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
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

    public Collection<RLAlgorithmDescriptor> getAlgorithms() {
        return algorithmRepository.getAlgorithmInfos();
    }

    public Optional<RLAlgorithmDescriptor> getAlgorithm(String name) {
        return algorithmRepository.getAlgorithmInfo(name);
    }
}
