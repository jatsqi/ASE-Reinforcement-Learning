package de.jquast.plugin.factory;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.plugin.policy.EpsilonGreedyPolicy;

import java.util.Optional;

public class SimplePolicyFactory implements PolicyFactory {

    @Override
    public Optional<Policy> createPolicy(String name, ActionValueStore store, RLSettings settings) {
        return Optional.of(createEpsilonGreedyPolicy(store, settings)); // Aktuell soll nur eine Policy (ohne Namen) unterstützt werden
    }

    private EpsilonGreedyPolicy createEpsilonGreedyPolicy(ActionValueStore store, RLSettings settings) {
        return new EpsilonGreedyPolicy(store, settings);
    }
}
