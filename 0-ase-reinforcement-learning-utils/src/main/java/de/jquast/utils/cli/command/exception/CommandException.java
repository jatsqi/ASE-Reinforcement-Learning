package de.jquast.utils.cli.command.exception;

public class CommandException extends Exception {

    public CommandException(String cmd, String message) {
        super(String.format("Beim Ausführung des Kommandos '%s' ist ein Fehler aufgetreten: %s", cmd, message));
    }
}
