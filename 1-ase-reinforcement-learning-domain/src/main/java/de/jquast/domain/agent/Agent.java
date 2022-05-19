package de.jquast.domain.agent;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.ActionSource;

public abstract class Agent {

    protected final Environment environment;
    protected final ActionSource actionSource;
    protected final RLSettings settings;

    protected int totalActionsTaken;
    protected double averageReward;

    public Agent(Environment environment, ActionSource source, RLSettings settings) {
        this.environment = environment;
        this.actionSource = source;
        this.settings = settings;

        this.totalActionsTaken = 0;
        this.averageReward = 0.0;
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

    protected void updateRewardEstimates(int action, double receivedReward) {
        averageReward = averageReward + settings.agentRewardStepSize() * (receivedReward - averageReward);
    }

    public void executeNextAction() {
        // State cachen
        int oldState = environment.getCurrentState();

        // Aktion von Policy holen
        int action = collectActionFromSource();

        // Aktion ausführen
        ActionDataPair toExecute = transformAction(action);
        executeTransformedAction(toExecute);
        this.totalActionsTaken++;

        // Neuen State holen
        int newState = environment.getCurrentState();

        // Feedback senden
        double receivedReward = environment.getReward();
        criticiseSource(oldState, action, newState, receivedReward);
        updateRewardEstimates(action, receivedReward);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public ActionSource getActionSource() {
        return actionSource;
    }

    public double getCurrentAverageReward() {
        return averageReward;
    }

    public record ActionDataPair(Action action, int data) {}
}
