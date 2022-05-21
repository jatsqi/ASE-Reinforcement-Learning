package de.jquast.application.session;

import de.jquast.domain.agent.Agent;
import de.jquast.domain.environment.Environment;
import de.jquast.domain.policy.visualizer.PolicyVisualizer;

import java.util.function.Consumer;

public class SzenarioSession {

    private final Agent agent;
    private final Environment environment;
    private final PolicyVisualizer visualizer;

    private int currentStep = 0;
    private final long maxSteps;

    private SzenarioProgressObserver observer;

    public SzenarioSession(Agent agent, Environment environment, PolicyVisualizer visualizer, long maxSteps) {
        this.agent = agent;
        this.environment = environment;
        this.maxSteps = maxSteps;
        this.visualizer = visualizer;
    }

    public SzenarioSession(Szenario szenario) {
        this(
                szenario.agent(),
                szenario.environment(),
                szenario.visualizer(),
                szenario.maxSteps()
        );
    }

    public void start() {
        long currStep = 0;
        execWhenPresent(obs -> obs.onTrainingStart(this));

        while (currStep < maxSteps) {
            final long currentStepCached = currStep;
            execWhenPresent(obs -> obs.preTrainingStep(this, currentStepCached, agent.getCurrentAverageReward()));

            environment.tick();
            agent.executeNextAction();

            execWhenPresent(obs -> obs.postTrainingStep(this, currentStepCached, agent.getCurrentAverageReward()));
            currStep++;
        }

        execWhenPresent(obs -> obs.postTrainingStep(this, currentStep, agent.getCurrentAverageReward()));
    }

    public void setObserver(SzenarioProgressObserver observer) {
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

    private void execWhenPresent(Consumer<SzenarioProgressObserver> consumer) {
        if (observer != null) {
            consumer.accept(observer);
        }
    }
}
