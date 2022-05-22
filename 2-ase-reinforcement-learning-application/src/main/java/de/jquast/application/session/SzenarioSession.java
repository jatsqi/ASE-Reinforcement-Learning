package de.jquast.application.session;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SzenarioSession {

    private int currentStep = 0;
    private final Szenario szenario;

    private List<SzenarioProgressObserver> observer = new ArrayList<>();

    public SzenarioSession(Szenario szenario) {
        this.szenario = szenario;
    }

    public void start() {
        long currStep = 0;
        execWhenPresent(obs -> obs.onSzenarioStart(this));

        while (currStep < szenario.maxSteps()) {
            final long currentStepCached = currStep;
            execWhenPresent(obs -> obs.preSzenarioStep(this, currentStepCached, szenario.agent().getCurrentAverageReward()));

            szenario.environment().tick();
            szenario.agent().executeNextAction();

            execWhenPresent(obs -> obs.postSzenarioStep(this, currentStepCached, szenario.agent().getCurrentAverageReward()));
            currStep++;
        }

        execWhenPresent(obs -> obs.onSzenarioEnd(this, szenario.agent().getCurrentAverageReward()));
    }

    public SzenarioSession addObserver(SzenarioProgressObserver observer) {
        this.observer.add(observer);
        return this;
    }

    public Szenario getSzenario() {
        return szenario;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    private void execWhenPresent(Consumer<SzenarioProgressObserver> consumer) {
        if (observer.size() > 0) {
            observer.forEach(consumer::accept);
        }
    }
}
