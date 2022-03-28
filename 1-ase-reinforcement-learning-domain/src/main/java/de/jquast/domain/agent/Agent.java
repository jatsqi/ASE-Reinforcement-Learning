package de.jquast.domain.agent;

public abstract class Agent implements Teachable {

    private double reward;

    abstract void onEpisodeBegin();

    abstract void onEpisodeEnd();

    @Override
    public void setReward(double reward) {
        this.reward = reward;
    }

    @Override
    public double getReward() {
        return reward;
    }

    @Override
    public double addReward(double reward) {
        this.reward += reward;

        return this.reward;
    }
}
