package de.jquast.application.session;

public interface SzenarioProgressObserver {

    default void onTrainingStart(SzenarioSession session) {};

    default void preTrainingStep(SzenarioSession session, long currentStep, double averageReward) {};

    default void postTrainingStep(SzenarioSession session, long currentStep, double averageReward) {};

    default void onTrainingEnd(SzenarioSession session, double averageReward) {};

}
