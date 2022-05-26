package de.jquast.domain.visualizer;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.policy.Policy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class PolicyVisualizer {

    protected final Agent agent;
    protected final Policy policy;
    protected final Environment environment;

    public PolicyVisualizer(Agent agent, Policy policy, Environment environment) {
        this.agent = agent;
        this.policy = policy;
        this.environment = environment;
    }

    public abstract byte[] visualize(VisualizationFormat format);

    public void saveToFile(Path path, VisualizationFormat format) throws IOException {
        Files.write(path, visualize(format), StandardOpenOption.WRITE);
    }

    public Agent getAgent() {
        return agent;
    }

    public Policy getPolicy() {
        return policy;
    }

    public Environment getEnvironment() {
        return environment;
    }
}
