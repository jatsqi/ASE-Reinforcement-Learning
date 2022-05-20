package de.jquast.domain.environment;

import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.RewardSource;

public interface Environment extends RewardSource {

    /**
     * Führt eine bestimmte Aktion in der Umgebung aus.
     *
     * @param action Die auszuführende Aktion.
     * @return Gibt true zurück, sofern die Aktion gültig ist. false anderenfalls.
     */
    boolean executeAction(Action action, int data);

    /**
     * Calculates the current state of the environment.
     *
     * @return The current state of the environment.
     */
    int getCurrentState();

    /**
     * @return
     */
    int getStateSpace();

    /**
     *
     */
    void tick();

}
