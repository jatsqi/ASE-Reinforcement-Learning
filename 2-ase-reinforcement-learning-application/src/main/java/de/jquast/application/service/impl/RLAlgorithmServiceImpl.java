package de.jquast.application.service.impl;

import de.jquast.application.service.RLAlgorithmService;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.Optional;

public class RLAlgorithmServiceImpl implements RLAlgorithmService {

    private final RLAlgorithmRepository algorithmRepository;

    @Inject
    public RLAlgorithmServiceImpl(RLAlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    public Collection<RLAlgorithmDescriptor> getAlgorithms() {
        return algorithmRepository.getAlgorithmInfos();
    }

    public Optional<RLAlgorithmDescriptor> getAlgorithm(String name) {
        return algorithmRepository.getAlgorithmInfo(name);
    }
}
