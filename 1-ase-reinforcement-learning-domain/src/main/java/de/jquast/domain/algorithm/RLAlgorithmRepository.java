package de.jquast.domain.algorithm;

import de.jquast.domain.exception.AlgorithmCreationException;
import de.jquast.domain.shared.ActionSource;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Collection;
import java.util.Optional;

public interface RLAlgorithmRepository {

    Collection<RLAlgorithmDescriptor> getAlgorithmInfos();

    Optional<RLAlgorithmDescriptor> getAlgorithmInfo(String name);

    RLAlgorithm createAlgorithmInstance(RLAlgorithmDescriptor descriptor, ActionValueStore store, ActionSource source) throws AlgorithmCreationException;

}
