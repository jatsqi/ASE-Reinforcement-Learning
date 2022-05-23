package de.jquast.domain.agent;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

public class FakeAgent extends Agent {
    public FakeAgent(Environment environment, ActionSource source, RLSettings settings) {
        super(environment, source, settings);
    }

    @Override
    public ActionDataPair transformAction(int action) {
        return new ActionDataPair(Action.DO_NOTHING, 0);
    }
}
