package de.jquast.application.session;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.policy.visualizer.PolicyVisualizer;

import java.util.function.Consumer;

public class TrainingSession {

    private final Agent agent;
    private final Environment environment;
    private final PolicyVisualizer visualizer;

    private int currentStep = 0;
    private final long maxSteps;

    private TrainingProgressObserver observer;

    public TrainingSession(Agent agent, Environment environment, PolicyVisualizer visualizer, long maxSteps) {
        this.agent = agent;
        this.environment = environment;
        this.maxSteps = maxSteps;
        this.visualizer = visualizer;
    }

    public void start() {
        long currStep = 0;
        execWhenPresent(obs -> obs.onTrainingStart(currentStep, maxSteps));

        while (currStep < maxSteps) {
            final long currentStepCached = currStep;
            execWhenPresent(obs -> obs.preTrainingStep(currentStepCached, maxSteps, agent.getCurrentAverageReward()));

            environment.tick();
            agent.executeNextAction();

            execWhenPresent(obs -> obs.postTrainingStep(currentStepCached, maxSteps, agent.getCurrentAverageReward()));
            currStep++;
        }

        execWhenPresent(obs -> obs.postTrainingStep(currentStep, maxSteps, agent.getCurrentAverageReward()));
    }

    public void setObserver(TrainingProgressObserver observer) {
        this.observer = observer;
    }

    public Agent getAgent() {
        return agent;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public long getMaxSteps() {
        return maxSteps;
    }

    public PolicyVisualizer getVisualizer() {
        return visualizer;
    }

    private void execWhenPresent(Consumer<TrainingProgressObserver> consumer) {
        if (observer != null) {
            consumer.accept(observer);
        }
    }
}
