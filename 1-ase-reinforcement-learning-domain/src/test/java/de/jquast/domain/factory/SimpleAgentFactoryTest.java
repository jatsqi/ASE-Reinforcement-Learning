package de.jquast.domain.factory;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleAgentFactoryTest {

    private SimpleAgentFactory factory;
    private RLSettings settings;

    @Mock
    private Environment environment;

    @Mock
    private ActionSource source;

    @BeforeEach
    void prepare() {
        factory = new SimpleAgentFactory();
        settings = new RLSettings(
                0.0, 0.0, 0.0, 0.0
        );

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPullAgentShouldReturnValidAgent() {
        AgentDescriptor pull = new AgentDescriptor(
                "pull",
                "",
                new Action[]{},
                0
        );

        Optional<Agent> agentOp = factory.createAgent(pull, environment, source, settings);
        assertTrue(agentOp.isPresent());
    }

    @Test
    void create2DMovingAgentShouldReturnValidAgent() {
        AgentDescriptor pull = new AgentDescriptor(
                "2d-moving-agent",
                "",
                new Action[]{},
                0
        );

        Optional<Agent> agentOp = factory.createAgent(pull, environment, source, settings);
        assertTrue(agentOp.isPresent());
    }

    @Test
    void createUnknownAgentShouldReturnEmptyOptional() {
        AgentDescriptor pull = new AgentDescriptor(
                "unknown??????",
                "",
                new Action[]{},
                0
        );

        Optional<Agent> agentOp = factory.createAgent(pull, environment, source, settings);
        assertTrue(agentOp.isEmpty());
    }

}
