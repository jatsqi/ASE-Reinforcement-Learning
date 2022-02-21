package de.jquast.utils.di;

import de.jquast.utils.di.analyzer.AnalyzedType;
import de.jquast.utils.di.analyzer.TypeAnalyzer;
import de.jquast.utils.di.annotations.Singleton;
import de.jquast.utils.exception.InjectionException;

import java.lang.reflect.Constructor;
import java.util.*;

public class InjectionContext {

    /**
     * Wenn ein Interface als Dependency angefordert wird, muss dieses zuvor auf eine instantiierbare Klasse gemapped
     * werden.
     */
    private Map<Class<?>, Class> interfaceMappings = new HashMap<>();

    /**
     * Falls bestimmte Klassen nur einmalig instantiiert werden sollen, wird dies hier als Key vorgemerkt.
     * Die Value in der Map wird nach dem erstmaligen erstellen mit der Instanz des Singletons ersetzt.
     */
    private Map<Class<?>, Object> reservedSingletons = new HashMap<>();

    /**
     * Erstellt eine neue Instanz des Typs {@code type}.
     *
     * @param type  Der Typ des zu erstellenden Objektes.
     * @return      Neu erstellte Instanz mit allen angeforderten Abhängigkeiten.
     */
    public <T> T createNewInstance(Class<?> type) throws InjectionException {
        // Wenn der Kontext eine Instanz von sich selbst erstellen soll, dann gibt er sich selbst zurück.
        if (type.equals(getClass())) {
            return (T) this;
        }

        Class<?> toCreate = type;

        if (type.isInterface()) {
            if (interfaceMappings.containsKey(type)) {
                toCreate = interfaceMappings.get(type);
            } else {
                throw new InjectionException(type, "Für dieses Interface ist kein Mapping definiert.");
            }
        }

        // Prüfen, ob Singleton bereits erstellt wurde.
        if (reservedSingletons.getOrDefault(toCreate, null) != null) {
            return (T) reservedSingletons.get(toCreate);
        }

        // Analysiere Typ, der letztendlich erstellt werden soll.
        AnalyzedType<T> analyzedType = TypeAnalyzer.analyze(toCreate);

        Constructor<T> target = analyzedType.injectionTarget();
        Object[] createdDependencies = new Object[target.getParameterCount()];

        for(int i = 0; i < target.getParameterCount(); ++i) {
            createdDependencies[i] = createNewInstance(target.getParameterTypes()[i]);
        }

        try {
            T createdInstance = target.newInstance(createdDependencies);
            if (isSingleton(toCreate)) {
                reservedSingletons.put(toCreate, createdInstance);
            }

            return createdInstance;
        } catch (Exception e) {
            throw new InjectionException(toCreate, "Beim Erstellen der Instanz ist ein Fehler aufgetreten: " + e.getMessage());
        }
    }

    /**
     *
     * @param interf
     * @param clazz
     * @return
     */
    public boolean mapInterface(Class<?> interf, Class<?> clazz) {
        if (clazz.isInterface()) {
            return false;
        }

        interfaceMappings.put(interf, clazz);
        return true;
    }

    /**
     *
     * @param clazz
     */
    public void markAsSingleton(Class<?> clazz) {
        reservedSingletons.put(clazz, null);
    }

    /**
     *
     * @param clazz
     * @return
     */
    public boolean isSingleton(Class<?> clazz) {
        return reservedSingletons.containsKey(clazz) || clazz.isAnnotationPresent(Singleton.class);
    }

}
