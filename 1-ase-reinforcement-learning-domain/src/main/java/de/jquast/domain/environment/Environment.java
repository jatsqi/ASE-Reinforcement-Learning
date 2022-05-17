package de.jquast.domain.environment;

public interface Environment {

    /**
     * Führt eine bestimmte Aktion in der Umgebung aus.
     * @param action    Die auszuführende Aktion.
     * @return          Gibt true zurück, sofern die Aktion gültig ist. false anderenfalls.
     */
    boolean executeAction(Action action);

    /**
     * Gibt eine Liste der Aktionen zurück, die in dieser Umgebung unterstüzt werden.
     * @return
     */
    Action[] getSupportedActions();

}
