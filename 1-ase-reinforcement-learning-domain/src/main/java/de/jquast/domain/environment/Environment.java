package de.jquast.domain.environment;

import de.jquast.domain.shared.Action;
import de.jquast.domain.shared.RewardSource;

public abstract class Environment implements RewardSource {

    /**
     * Führt eine bestimmte Aktion in der Umgebung aus.
     *
     * @param action Die auszuführende Aktion.
     * @return Gibt true zurück, sofern die Aktion gültig ist. false anderenfalls.
     */
    public abstract boolean executeAction(Action action, int data);

    /**
     * Calculates the current state of the environment.
     *
     * @return The current state of the environment.
     */
    public abstract int getCurrentState();

    /**
     * @return
     */
    public abstract int getStateSpace();

    /**
     *
     */
    public abstract void tick();

}
