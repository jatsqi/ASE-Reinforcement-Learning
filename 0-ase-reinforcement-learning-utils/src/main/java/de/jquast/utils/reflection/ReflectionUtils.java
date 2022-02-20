package de.jquast.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    /**
     * Überschreibt das übergebene Feld {@code field} des Objektes {@code target} mit dem Wert aus {@code value}.
     *
     * @param field                     Das zu überschreibende Feld.
     * @param target                    Das betroffene Objekt.
     * @param value                     Der neue Wert des Feldes.
     * @throws IllegalAccessException   Wenn auf das Feld nicht zugegriffen werden kann.
     */
    public static void setField(Field field, Object target, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(target, value);
    }

    /**
     * Überschreibt das Feld mit dem Namen {@code name} des Objektes {@code target} mit dem Wert aus {@code value}.
     *
     * @param name                      Der Name des zu überschreibenden Feldes.
     * @param target                    Das betroffene Objekt.
     * @param value                     Der neue Wert des Feldes.
     * @throws NoSuchFieldException     Wenn das Feld nicht gefunden wurde.
     * @throws IllegalAccessException   Wenn auf das Feld nicht zugegriffen werden kann.
     */
    public static void setField(String name, Object target, Object value) throws NoSuchFieldException, IllegalAccessException {
        setField(findField(target.getClass(), name), target, value);
    }

    /**
     * Gibt den Wert des Feldes {@code field} des Objektes {@code target} zurück.
     *
     * @param field                     Das zu lesende Feld.
     * @param target                    Das Objekt, welches das zu lesende Feld beinhaltet.
     * @return                          Den Wert des Feldes, welches in dem Objekt gespeichert ist.
     * @throws IllegalAccessException   Wenn auf das Feld nicht zugegriffen werden kann.
     */
    public static Object getField(Field field, Object target) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(target);
    }

    /**
     * Gibt den Wert des Feldes {@code field} des Objektes {@code target} zurück.
     *
     * @param name                      Der Name des zu lesenden Feldes.
     * @param target                    Das Objekt, welches das zu lesende Feld beinhaltet.
     * @return                          Den Wert des Feldes, welches in dem Objekt gespeichert ist.
     * @throws IllegalAccessException   Wenn auf das Feld nicht zugegriffen werden kann.
     */
    public static Object getField(String name, Object target) throws NoSuchFieldException, IllegalAccessException {
        return getField(findField(target.getClass(), name), target);
    }

    /**
     * Durchsucht den Klassentyp {@code cls} nach einem öffentlichen oder privaten Feld mit den Namen {@code name}.
     * Sollte kein Feld gefunden werden, wird rekursiv die Klassenhierarchie nach öffentlichen Feldern mit
     * entsprechendem Namen durchsucht.
     *
     * @param cls                   Der Klassentyp, von dem aus die Suche gestartet werden soll.
     * @param name                  Der Name des Feldes.
     * @return                      Das gefundene Feld.
     * @throws NoSuchFieldException Wenn kein entsprechendes Feld gefunden wurde.
     */
    public static Field findField(Class<?> cls, String name) throws NoSuchFieldException {
        try {
            Field field = cls.getField(name);
            return field;
        } catch (NoSuchFieldException e) {
            return cls.getDeclaredField(name);
        }
    }

    /**
     * Durchsucht den Klassentyp {@code cls} und seine Hierarchie rekursiv nach einem öffentlichen Konstruktor,
     * der mit einer bestimmten Annotation {@code annotation} versehen ist.
     *
     * @param cls           Der Klassentyp, der durchsucht wird.
     * @param annotation    Die Annotation, die vorhanden sein muss.
     * @return              Der gefundene Konstruktor. NULL, wenn kein passender Konstruktor gefunden wurde.
     */
    public static <T> List<Constructor<T>> findConstructorAnnotatedWith(Class<T> cls, Class<? extends Annotation> annotation) {
        List<Constructor<T>> constructors = new ArrayList<>();

        for (Constructor c : cls.getConstructors()) {
            if (c.isAnnotationPresent(annotation)) {
                constructors.add(c);
            }
        }

        return constructors;
    }

    /**
     * Durchsucht den Klassentyp {@code cls} nach einem öffentlichen oder privaten Konstruktor, der mit einer
     * bestimmten Annotation {@code annotation} versehen ist.
     *
     * @param cls           Der Klassentyp, der durchsucht wird.
     * @param annotation    Die Annotation, die vorhanden sein muss.
     * @return              Der gefundene Konstruktor. NULL, wenn kein passender Konstruktor gefunden wurde.
     */
    public static <T> List<Constructor<T>> findDeclaredConstructorAnnotatedWith(Class<T> cls, Class<? extends Annotation> annotation) {
        List<Constructor<T>> constructors = new ArrayList<>();

        for (Constructor c : cls.getDeclaredConstructors()) {
            if (c.isAnnotationPresent(annotation)) {
                constructors.add(c);
            }
        }

        return constructors;
    }

}
