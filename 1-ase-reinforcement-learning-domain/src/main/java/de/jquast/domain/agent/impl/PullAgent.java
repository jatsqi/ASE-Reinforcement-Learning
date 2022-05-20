package de.jquast.domain.agent.impl;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

public class PullAgent extends Agent {

    public PullAgent(Environment environment, ActionSource source, RLSettings settings) {
        super(environment, source, settings);
    }

    @Override
    protected ActionDataPair transformAction(int action) {
        return new ActionDataPair(Action.PULL, action); // Action = Der Hebel, an dem gezogen wird.
    }

}
