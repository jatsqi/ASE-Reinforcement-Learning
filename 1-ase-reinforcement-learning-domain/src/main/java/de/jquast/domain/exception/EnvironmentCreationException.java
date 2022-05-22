package de.jquast.domain.exception;

public class EnvironmentCreationException extends Exception {
    public EnvironmentCreationException(String message, String envName) {
        super(String.format("Es gab einen Fehler beim Erstellen des Environments '%s': %s", envName, message));
    }
}
