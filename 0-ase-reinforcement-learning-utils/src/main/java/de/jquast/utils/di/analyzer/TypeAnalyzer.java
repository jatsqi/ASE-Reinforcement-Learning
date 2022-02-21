package de.jquast.utils.di.analyzer;

import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.exception.InjectionException;
import de.jquast.utils.reflection.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TypeAnalyzer {

    // Caches
    private static final Map<Class<?>, AnalyzedType<?>> cachedTypes = new HashMap<>();

    /**
     * Analysiert den übergebenen Klassentypen und extrahiert die erforderlichen Metadaten für die Dependency Injection.
     *
     * @param target
     * @param <T>
     * @return
     */
    public static <T> AnalyzedType<T> analyze(Class<?> target) {
        if (cachedTypes.containsKey(target)) {
            return (AnalyzedType<T>) cachedTypes.get(target);
        }

        try {
            Constructor<T> constructor = findInjectionTarget(target);
            Class<?>[] dependencies = collectTargetDependencyTypes(target);

            AnalyzedType<T> analyzedType = new AnalyzedType<T>(target, constructor, dependencies);
            cachedTypes.put(target, analyzedType);
            return analyzedType;
        } catch (InjectionException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sucht nach einem passenden Constructor, der für die Dependency Injection genutzt werden kann.
     * <p>
     * Sollte der übergebene Klassentyp nur einen Constructor besitzen, so wird dieser zurückgegeben.
     * <p>
     * Sollten mehrere Konstruktoren definiert sein, so wird der Konstruktor zurückgegeben, der mit der Inject
     * Annotation versehen ist.
     *
     * @param target
     * @param <T>
     * @return Der gefundene Konstruktor.
     * @throws InjectionException Wenn kein passender Konstruktor gefunden werden, wird eine Exception geworfen.
     */
    public static <T> Constructor<T> findInjectionTarget(Class<?> target) throws InjectionException {
        Constructor[] constructors = target.getConstructors();

        // Genau ein Konstruktor vorhanden.
        if (constructors.length == 1) {
            return constructors[0];
            // Mehrere Konstruktoren vorhanden, suche nach Konstruktor mit @Inject Annotation.
        } else {
            List<Constructor<T>> annotatedConstructors = ReflectionUtils.findDeclaredConstructorAnnotatedWith(target, Inject.class);

            // Wenn genau ein Konstruktor vorhanden, so wird dieser zurückgegeben.
            if (annotatedConstructors.size() == 1) {
                return annotatedConstructors.get(0);
            }
        }

        throw new InjectionException(target, "Kein passender Konstruktor gefunden!");
    }

    /**
     * Extrahiert die Klassentypen, die benötigt werden, um ein Objekt des Typs {clazz} zu erstellen.
     *
     * @param clazz                 Der zu untersuchende Klassentyp.
     * @return                      Ein Array mit den Klassentypen, die der Target benötigt, um instantiiert werden
     *                              zu können.
     * @throws InjectionException   Wenn kein passender Konstruktor gefunden werden konnte.
     */
    public static <T> Class<?>[] collectTargetDependencyTypes(Class<?> clazz) throws InjectionException {
        Constructor<T> constructor = findInjectionTarget(clazz);
        return constructor.getParameterTypes();
    }

    /**
     * Extrahiert rekursiv die Klassentypen, die für die erzeugung eines Objektes des Typs {clazz} benötigt werden.
     *
     * @param clazz                 Der zu untersuchende Klassentyp.
     * @return                      Ein Array mit den Klassentypen, die benötigt werden, um die komplette Hierarchie
     *                              eines Typs zu instantiieren.
     * @throws InjectionException   Wenn kein passender Konstruktor gefunden werden konnte.
     */
    public static <T> Class<?>[] collectTargetDependencyTypesRecursively(Class<?> clazz) throws InjectionException {
        Constructor<T> constructor = findInjectionTarget(clazz);
        Class<?>[] dependencies = constructor.getParameterTypes();

        for (Class<?> c : constructor.getParameterTypes()) {
            dependencies = Stream.concat(
                    Stream.of(dependencies),
                    Stream.of(collectTargetDependencyTypes(c))).toArray(Class<?>[]::new);
        }

        return dependencies;
    }

}
