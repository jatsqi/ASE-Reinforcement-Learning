package de.jquast.domain.factory;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyDescriptor;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.domain.policy.impl.EpsilonGreedyPolicy;
import de.jquast.domain.policy.impl.GreedyPolicy;

import java.util.Optional;

public class SimplePolicyFactory implements PolicyFactory {

    @Override
    public Optional<Policy> createPolicy(PolicyDescriptor descriptor, ActionValueStore store, RLSettings settings) {
        return Optional.of(createEpsilonGreedyPolicy(store, settings)); // Aktuell soll nur eine Policy (ohne Namen) unterstützt werden
    }

    @Override
    public Optional<Policy> createMaximizingPolicy(ActionValueStore store, RLSettings settings) {
        return Optional.of(createGreedyPolicy(store, settings));
    }

    private EpsilonGreedyPolicy createEpsilonGreedyPolicy(ActionValueStore store, RLSettings settings) {
        return new EpsilonGreedyPolicy(store, settings);
    }

    private GreedyPolicy createGreedyPolicy(ActionValueStore store, RLSettings settings) {
        return new GreedyPolicy(store, settings);
    }
}
