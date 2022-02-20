package de.jquast.utils.exception;

public class InjectionException extends Exception {

    public InjectionException(Class<?> clazz, String message) {
        super(String.format("Fehler beim Injecten der Abhängigkeiten für Klassentyp %s: %s", clazz.getCanonicalName(), message));
    }
}
