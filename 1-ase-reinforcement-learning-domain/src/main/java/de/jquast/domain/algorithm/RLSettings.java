package de.jquast.domain.algorithm;

public record RLSettings(double learningRate, double discountFactor, double explorationRate,
                         double agentRewardStepSize) {

    public RLSettings {
        if (learningRate < 0 || learningRate > 1)
            throw new IllegalArgumentException("Die Lernrate darf nur im Interval [0, 1] liegen.");

        if (discountFactor < 0 || discountFactor > 1)
            throw new IllegalArgumentException("Der Discount Factor darf nur im Interval [0, 1] liegen.");

        if (explorationRate < 0 || explorationRate > 1)
            throw new IllegalArgumentException("Die Erkundungsrate darf nur im Interval [0, 1] liegen.");

        if (agentRewardStepSize < 0 || agentRewardStepSize > 1)
            throw new IllegalArgumentException("Die Agent-Reward-Schrittrate darf nur im Interval [0, 1] liegen.");
    }

    @Override
    public String toString() {
        return "Reinforcement Learning Setting: \n" +
                "   Lernrate: " + learningRate +
                "\n  Discount Factor: " + discountFactor +
                "\n  Erkundungsfaktor: " + explorationRate +
                "\n  Reward Step-Size: " + agentRewardStepSize;
    }
}
