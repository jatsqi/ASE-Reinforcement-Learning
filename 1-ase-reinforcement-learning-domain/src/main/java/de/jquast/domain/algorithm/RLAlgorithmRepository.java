package de.jquast.domain.algorithm;

import java.util.Collection;
import java.util.Optional;

public interface RLAlgorithmRepository {

    Collection<RLAlgorithm> getAlgorithms();

    Optional<RLAlgorithm> getAlgorithm(String name);

}
