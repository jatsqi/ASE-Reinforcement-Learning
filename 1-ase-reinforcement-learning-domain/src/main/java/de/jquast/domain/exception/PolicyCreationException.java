package de.jquast.domain.exception;

public class PolicyCreationException extends Exception {

    public PolicyCreationException(String message, String policy) {
        super(String.format(message, policy));
    }
}
