package de.jquast.plugin.repository;

import de.jquast.application.service.ConfigService;
import de.jquast.domain.exception.PolicyCreationException;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyDescriptor;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.policy.PolicyRepository;
import de.jquast.domain.policy.impl.EpsilonGreedyPolicy;
import de.jquast.domain.policy.impl.GreedyPolicy;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPolicyRepository implements PolicyRepository {

    private static final Map<String, PolicyDescriptor> POLICIES;

    static {
        POLICIES = new HashMap<>();
        POLICIES.put(EpsilonGreedyPolicy.EPSILON_GREEDY_POLICY_DESCRIPTOR.name(), EpsilonGreedyPolicy.EPSILON_GREEDY_POLICY_DESCRIPTOR);
        POLICIES.put(GreedyPolicy.GREEDY_POLICY_DESCRIPTOR.name(), GreedyPolicy.GREEDY_POLICY_DESCRIPTOR);
    }

    private final PolicyFactory factory;
    private final ConfigService configService;

    @Inject
    public InMemoryPolicyRepository(PolicyFactory factory, ConfigService configService) {
        this.factory = factory;
        this.configService = configService;
    }

    @Override
    public Collection<PolicyDescriptor> getPolicyInfos() {
        return POLICIES.values();
    }

    @Override
    public Optional<PolicyDescriptor> getPolicyInfo(String name) {
        return Optional.ofNullable(POLICIES.get(name));
    }

    @Override
    public PolicyDescriptor getDefaultPolicyInfo() {
        return POLICIES.get("epsilon-greedy");
    }

    @Override
    public PolicyDescriptor getMaximizingPolicyInfo() {
        return POLICIES.get("greedy");
    }

    @Override
    public Policy createPolicyInstance(PolicyDescriptor descriptor, ActionValueStore store) throws PolicyCreationException {
        Optional<Policy> policy = factory.createPolicy(descriptor, store, configService.getRLSettings());
        if (policy.isEmpty())
            throw new PolicyCreationException("Fehler beim Erstellen der Policy '%s'", descriptor.name());

        return policy.get();
    }

}
