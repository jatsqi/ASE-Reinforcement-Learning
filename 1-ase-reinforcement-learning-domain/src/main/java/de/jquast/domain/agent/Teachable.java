package de.jquast.domain.agent;

public interface Teachable {

    void setReward(double reward);

    double getReward();

    double addReward(double reward);

    void onActionReceived(int action);

}
