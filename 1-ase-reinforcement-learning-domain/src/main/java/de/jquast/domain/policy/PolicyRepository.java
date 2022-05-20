package de.jquast.domain.policy;

import de.jquast.domain.exception.PolicyCreationException;
import de.jquast.domain.shared.ActionValueStore;

import java.util.Collection;
import java.util.Optional;

public interface PolicyRepository {

    Collection<PolicyDescriptor> getPolicyInfos();

    Optional<PolicyDescriptor> getPolicyInfo(String name);

    PolicyDescriptor getDefaultPolicyInfo();

    Policy createPolicyInstance(PolicyDescriptor descriptor, ActionValueStore store) throws PolicyCreationException;

    Policy createMaximizingPolicy(ActionValueStore store) throws PolicyCreationException;

}
