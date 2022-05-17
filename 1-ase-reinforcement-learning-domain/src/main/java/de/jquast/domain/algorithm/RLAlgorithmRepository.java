package de.jquast.domain.algorithm;

import java.util.Collection;
import java.util.Optional;

public interface RLAlgorithmRepository {

    Collection<RLAlgorithmDescriptor> getAlgorithms();

    Optional<RLAlgorithmDescriptor> getAlgorithm(String name);

}
