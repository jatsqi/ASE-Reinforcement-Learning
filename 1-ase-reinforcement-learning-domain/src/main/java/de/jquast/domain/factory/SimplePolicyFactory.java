package de.jquast.domain.factory;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyDescriptor;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.policy.impl.EpsilonGreedyPolicy;
import de.jquast.domain.policy.impl.GreedyPolicy;
import de.jquast.domain.shared.ActionValueStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimplePolicyFactory implements PolicyFactory {

    private static Map<String, PolicyConstructor> POLICY_CONSTRUCTORS;

    static {
        POLICY_CONSTRUCTORS = new HashMap<>();

        POLICY_CONSTRUCTORS.put("epsilon-greedy", (descriptor, store, settings) -> new EpsilonGreedyPolicy(store, settings));
        POLICY_CONSTRUCTORS.put("greedy", (descriptor, store, settings) -> new GreedyPolicy(store, settings));
    }

    @Override
    public Optional<Policy> createPolicy(PolicyDescriptor descriptor, ActionValueStore store, RLSettings settings) {
        Policy policy = null;
        if (POLICY_CONSTRUCTORS.containsKey(descriptor.name()))
            policy = POLICY_CONSTRUCTORS.get(descriptor.name()).constructPolicy(descriptor, store, settings);

        return Optional.ofNullable(policy);
    }

    @Override
    public Optional<Policy> createMaximizingPolicy(ActionValueStore store, RLSettings settings) {
        return Optional.of(new GreedyPolicy(store, settings));
    }

    private interface PolicyConstructor {
        Policy constructPolicy(PolicyDescriptor descriptor, ActionValueStore store, RLSettings settings);
    }
}
