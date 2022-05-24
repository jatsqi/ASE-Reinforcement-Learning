package de.jquast.application.fake;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

public class FakeBestAgent extends Agent {

    public FakeBestAgent(Environment environment, ActionSource source, RLSettings settings) {
        super(environment, source, settings);
    }

    @Override
    public ActionDataPair transformAction(int action) {
        return new ActionDataPair(Action.DO_NOTHING, 1);
    }
}
