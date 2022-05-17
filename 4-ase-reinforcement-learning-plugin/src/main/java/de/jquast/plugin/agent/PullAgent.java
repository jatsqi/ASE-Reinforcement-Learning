package de.jquast.plugin.agent;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

public class PullAgent extends Agent {

    public PullAgent(Environment environment, ActionSource source) {
        super(environment, source);
    }

    @Override
    protected ActionDataPair transformAction(int action) {
        return new ActionDataPair(Action.PULL, action);
    }

}
