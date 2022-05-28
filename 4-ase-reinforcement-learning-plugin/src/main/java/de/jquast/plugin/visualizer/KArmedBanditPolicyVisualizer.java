package de.jquast.plugin.visualizer;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.application.environment.KArmedBanditEnvironment;
import de.jquast.domain.policy.Policy;
import de.jquast.domain.shared.Action;
import de.jquast.domain.visualizer.PolicyVisualizer;
import de.jquast.domain.visualizer.VisualizationFormat;

import java.util.Locale;

public class KArmedBanditPolicyVisualizer extends PolicyVisualizer {

    public KArmedBanditPolicyVisualizer(Agent agent, Policy policy, Environment environment) {
        super(agent, policy, environment);
    }

    @Override
    public byte[] visualize(VisualizationFormat format) {
        if (!format.equals(VisualizationFormat.TEXT))
            throw new UnsupportedOperationException("Dieses Format wird momentan nicht unterstützt!");

        StringBuilder pullBuilder = new StringBuilder();
        StringBuilder rewardBuilder = new StringBuilder();
        KArmedBanditEnvironment environment = (KArmedBanditEnvironment) getEnvironment();

        for (int i = 0; i < environment.getBanditCount(); i++) {
            if (agent.transformAction(policy.selectBestAction(i)).action().equals(Action.PULL)) {
                pullBuilder.append(String.format("%-10s", "[X]"));
            } else {
                pullBuilder.append(String.format("%-10s", "[ ]"));
            }

            rewardBuilder.append(String.format(Locale.US, "%-10.2f", environment.getPrecomputedBanditRewards()[i]));
        }
        pullBuilder.append("\n");
        pullBuilder.append(rewardBuilder.toString());

        return pullBuilder.toString().getBytes();
    }
}
