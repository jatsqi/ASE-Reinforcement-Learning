package de.jquast.application.service;

import de.jquast.domain.algorithm.RLAlgorithmDescriptor;

import java.util.Collection;
import java.util.Optional;

public interface RLAlgorithmService {
    Collection<RLAlgorithmDescriptor> getAlgorithms();

    Optional<RLAlgorithmDescriptor> getAlgorithm(String name);
}
