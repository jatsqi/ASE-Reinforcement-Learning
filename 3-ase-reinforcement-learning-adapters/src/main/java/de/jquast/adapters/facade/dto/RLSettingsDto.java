package de.jquast.adapters.facade.dto;

public record RLSettingsDto(double learningRate, double discountFactor, double explorationRate,
                            double agentRewardStepSize) {

    @Override
    public String toString() {
        return "Reinforcement Learning Setting: \n" +
                "    Lernrate: " + learningRate +
                "\n  Discount Factor: " + discountFactor +
                "\n  Erkundungsfaktor: " + explorationRate +
                "\n  Reward Step-Size: " + agentRewardStepSize;
    }

}
