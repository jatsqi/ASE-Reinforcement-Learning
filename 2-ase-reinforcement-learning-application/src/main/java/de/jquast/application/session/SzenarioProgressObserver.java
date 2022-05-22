package de.jquast.application.session;

public interface SzenarioProgressObserver {

    default void onSzenarioStart(SzenarioSession session) {};

    default void preSzenarioStep(SzenarioSession session, long currentStep, double averageReward) {};

    default void postSzenarioStep(SzenarioSession session, long currentStep, double averageReward) {};

    default void onSzenarioEnd(SzenarioSession session, double averageReward) {};

}
