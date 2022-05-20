package de.jquast.domain.shared;

public interface ActionSource {

    /**
     * Selektiert eine passende Aktion im State.
     *
     * @param state Der State.
     * @return Die gewählte Aktion.
     */
    int selectAction(int state);

    /**
     * @param state
     * @return
     */
    int selectBestAction(int state);

    /**
     * Gibt der Action Source Feedback über getroffene Aktion.
     *
     * @param oldState Der vorherige Zustand vor der Aktion.
     * @param action   Die auszuführende Aktion.
     * @param newState Der neue Zustand nach dem Ausführen der Aktion.
     * @param reward   Der erhaltene Reward.
     */
    void criticiseAction(int oldState, int action, int newState, double reward);

    /**
     * Gibt den durchschnittlichen Reward zurück, der von dieser Action Source zu erwarten ist.
     *
     * @return Der durchschnittliche Reward.
     */
    //double getAverageReward();

}
