package de.jquast.domain.agent;

public interface Teachable {

    double getReward();

    void setReward(double reward);

    double addReward(double reward);

    void onActionReceived(int action);

}
