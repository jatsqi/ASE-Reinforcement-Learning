package de.jquast.plugin.repository;

import de.jquast.application.service.impl.ConfigServiceImpl;
import de.jquast.domain.exception.PolicyCreationException;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.policy.PolicyDescriptor;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.policy.PolicyRepository;
import de.jquast.domain.shared.ActionValueStore;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPolicyRepository implements PolicyRepository {

    private static final Map<String, PolicyDescriptor> POLICIES = new HashMap<>();

    static {
        String epsilonGreedy = "epsilon-greedy";
        POLICIES.put(epsilonGreedy, new PolicyDescriptor(
                epsilonGreedy,
                "Eine Policy, die mit einer Wahrscheinlichkeit von Epsilon eine zufällige Aktion ausführt."
        ));

        String greedy = "greedy";
        POLICIES.put(greedy, new PolicyDescriptor(
                greedy,
                "Führt immer die aktuell Beste Aktion aus."
        ));
    }

    private final PolicyFactory factory;
    private final ConfigServiceImpl configService;

    @Inject
    public InMemoryPolicyRepository(PolicyFactory factory, ConfigServiceImpl configService) {
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
