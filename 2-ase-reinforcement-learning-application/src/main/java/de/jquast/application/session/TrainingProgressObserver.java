package de.jquast.application.session;

public interface TrainingProgressObserver {

    void onTrainingStart(long currentStep, long maxStep);

    void preTrainingStep(long currentStep, long maxStep, double averageReward);

    void postTrainingStep(long currentStep, long maxStep, double averageReward);

    void onTrainingEnd(long currentStep, long maxStep, double averageReward);

}
