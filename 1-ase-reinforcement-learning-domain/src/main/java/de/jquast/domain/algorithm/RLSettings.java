package de.jquast.domain.algorithm;

public record RLSettings(double learningRate, double discountFactor, double explorationRate,
                         double agentRewardStepSize) {

    public RLSettings {
        checkArgumentRangeZeroToOneInclusive(learningRate, "Die Lernrate darf nur im Interval [0, 1] liegen.");
        checkArgumentRangeZeroToOneInclusive(discountFactor, "Der Discount Factor darf nur im Interval [0, 1] liegen.");
        checkArgumentRangeZeroToOneInclusive(explorationRate, "Die Erkundungsrate darf nur im Interval [0, 1] liegen.");
        checkArgumentRangeZeroToOneInclusive(agentRewardStepSize, "Die Agent-Reward-Schrittrate darf nur im Interval [0, 1] liegen.");
    }

    @Override
    public String toString() {
        return "Reinforcement Learning Setting: \n" +
                "   Lernrate: " + learningRate +
                "\n  Discount Factor: " + discountFactor +
                "\n  Erkundungsfaktor: " + explorationRate +
                "\n  Reward Step-Size: " + agentRewardStepSize;
    }

    private static void checkArgumentRangeZeroToOneInclusive(double value, String error) {
        if (value < 0 || value > 1)
            throw new IllegalArgumentException(error);
    }
}
