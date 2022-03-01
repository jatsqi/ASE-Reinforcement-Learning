package de.jquast.utils.cli.command.exception;

public class TypeConversionException extends Exception {

    public TypeConversionException(String source, Class<?> target, String message) {
        super(String.format("Fehler beim Konvertieren vom Argument '%s' nach %s: %s", source, target.getCanonicalName(), message));
    }
}
