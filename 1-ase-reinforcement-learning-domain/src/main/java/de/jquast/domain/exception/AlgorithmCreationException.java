package de.jquast.domain.exception;

public class AlgorithmCreationException extends Exception {

    public AlgorithmCreationException(String message, String name) {
        super(String.format(message, name));
    }
}
