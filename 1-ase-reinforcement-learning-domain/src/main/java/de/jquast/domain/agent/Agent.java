package de.jquast.domain.agent;

import de.jquast.domain.shared.Action;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.ActionSource;

public abstract class Agent {

    private final Environment environment;
    private final ActionSource actionSource;

    public Agent(Environment environment, ActionSource source) {
        this.environment = environment;
        this.actionSource = source;
    }

    public abstract void executeNextAction();

    public Environment getEnvironment() {
        return environment;
    }

    public abstract Action[] getAvailableActions();
}
