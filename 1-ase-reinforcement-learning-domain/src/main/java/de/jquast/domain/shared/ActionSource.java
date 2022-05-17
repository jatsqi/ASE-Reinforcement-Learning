package de.jquast.domain.shared;

public interface ActionSource {

    /**
     * Selektiert eine passende Aktion im State.
     *
     * @param state Der State.
     * @return Die gewählte Aktion.
     */
    int selectAction(int state);

    void criticiseAction(int oldState, int action, int newState, double reward);

}
