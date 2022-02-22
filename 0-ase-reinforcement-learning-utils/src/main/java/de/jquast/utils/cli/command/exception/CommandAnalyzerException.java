package de.jquast.utils.cli.command.exception;

public class CommandAnalyzerException extends Exception {

    public CommandAnalyzerException(Class<?> clazz, String message) {
        super(String.format(
                "Beim Analysieren des Kommandotyps '%s' ist ein Fehler aufgetreten: %s",
                clazz.getCanonicalName(), message));
    }
}
