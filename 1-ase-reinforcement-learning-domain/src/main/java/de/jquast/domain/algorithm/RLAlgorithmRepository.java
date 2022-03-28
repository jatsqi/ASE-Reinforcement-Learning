package de.jquast.domain.algorithm;

import java.util.Collection;

public interface RLAlgorithmRepository {

    Collection<RLAlgorithm> getAlgorithms();

    RLAlgorithm getAlgorithm(String name);

}
