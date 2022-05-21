package de.jquast.application.session;

import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.policy.PolicyDescriptor;

public record DescriptorBundle(AgentDescriptor agentDescriptor, EnvironmentDescriptor environmentDescriptor, PolicyDescriptor policyDescriptor) {
}
