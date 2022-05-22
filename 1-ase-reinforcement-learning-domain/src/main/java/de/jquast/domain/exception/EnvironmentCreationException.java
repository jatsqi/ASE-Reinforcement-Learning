package de.jquast.domain.exception;

public class EnvironmentCreationException extends Exception {
    public EnvironmentCreationException(String message, String envName) {
        super(String.format(message, envName));
    }
}