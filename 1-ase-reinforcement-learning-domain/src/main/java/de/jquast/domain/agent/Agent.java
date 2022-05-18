package de.jquast.domain.agent;

import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

public abstract class Agent {

    protected final Environment environment;
    protected final ActionSource actionSource;

    public Agent(Environment environment, ActionSource source) {
        this.environment = environment;
        this.actionSource = source;
    }

    protected abstract ActionDataPair transformAction(int action);

    protected int collectActionFromSource() {
        return actionSource.selectAction(environment.getCurrentState());
    }

    protected void executeTransformedAction(ActionDataPair toExecute) {
        environment.executeAction(toExecute.action, toExecute.data);
    }

    protected void criticiseSource(int oldState, int action, int newState, double reward) {
        actionSource.criticiseAction(oldState, action, newState, reward);
    }

    public void executeNextAction() {
        // State cachen
        int oldState = environment.getCurrentState();

        // Aktion von Policy holen
        int action = collectActionFromSource();

        // Aktion ausführen
        ActionDataPair toExecute = transformAction(action);
        executeTransformedAction(toExecute);

        // Neuen State holen
        int newState = environment.getCurrentState();

        // Feedback senden
        criticiseSource(oldState, action, newState, environment.getReward());
    }

    public Environment getEnvironment() {
        return environment;
    }

    public ActionSource getActionSource() {
        return actionSource;
    }

    public record ActionDataPair(Action action, int data) {}
}
