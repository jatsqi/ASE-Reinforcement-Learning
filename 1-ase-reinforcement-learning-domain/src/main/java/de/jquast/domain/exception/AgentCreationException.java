package de.jquast.domain.exception;

public class AgentCreationException extends Exception {
    public AgentCreationException(String message, String agentName) {
        super(String.format(message, agentName));
    }
}
