package de.jquast.utils.di;

import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.exception.InjectionException;

import java.lang.reflect.Constructor;

public class TypeAnalyzer {

    /**
     * Analysiert den übergebenen Klassentypen und extrahiert die erforderlichen Metadaten für die Dependency Injection.
     * @param target
     * @param <T>
     * @return
     */
    public static <T> AnalyzedType<T> analyze(Class<T> target) {

    }

    /**
     * Sucht nach einem passenden Constructor, der für die Dependency Injection genutzt werden kann.
     *
     * Sollte der übergebene Klassentyp nur einen Constructor besitzen, so wird dieser zurückgegeben.
     *
     * Sollten mehrere Konstruktoren definiert sein, so wird der Konstruktor zurückgegeben, der mit der Inject
     * Annotation versehen ist.
     *
     * @param target
     * @param <T>
     * @return                      Der gefundene Konstruktor.
     * @throws InjectionException   Wenn kein passender Konstruktor gefunden werden, wird eine Exception geworfen.
     */
    private static <T> Constructor<T> findInjectionTarget(Class<T> target) throws InjectionException {
        Constructor[] constructors = target.getConstructors();

        // Genau ein Konstruktor vorhanden.
        if (constructors.length == 1) {
            return constructors[0];
        // Mehrere Konstruktoren vorhanden, suche nach Konstruktor mit @Inject Annotation.
        } else {
            Constructor result = null;

            for (Constructor c : constructors) {
                if (c.isAnnotationPresent(Inject.class)) {
                    // Es darf nur ein Konstruktor mit @Inject versehen sein.
                    if (result != null) {
                        throw new InjectionException(target, "Mehr als ein möglicher Konstruktor als Kandidat gefunden!");
                    }

                    result = c;
                }
            }

            // Falls Konstruktor gefunden
            if (result != null) {
                return result;
            }
        }

        throw new InjectionException(target, "Kein passender Konstruktor gefunden!");
    }

}
