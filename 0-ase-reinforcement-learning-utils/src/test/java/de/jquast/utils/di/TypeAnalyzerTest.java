package de.jquast.utils.di;

import de.jquast.utils.di.analyzer.TypeAnalyzer;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.exception.InjectionException;
import de.jquast.utils.model.Human;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class TypeAnalyzerTest {

    @Test
    void InjectionTargetHumanShouldBeNameConstructor() throws InjectionException {
        Constructor<Human> humanConstructor = TypeAnalyzer.findInjectionTarget(Human.class);

        assertEquals(1, humanConstructor.getParameterCount());
        assertEquals(String.class, humanConstructor.getParameterTypes()[0]);
    }

    @Test
    void FindInjectionTargetShouldThrowOnNonInjectable() {
        assertThrows(InjectionException.class, () -> TypeAnalyzer.findInjectionTarget(ClassWithoutConstructors.class));
        assertThrows(InjectionException.class, () -> TypeAnalyzer.findInjectionTarget(ClassWithMultipleAnnotatedConstructors.class));
        assertThrows(InjectionException.class, () -> TypeAnalyzer.findInjectionTarget(ClassWithMultipleButNotAnnotatedConstructors.class));
    }

    @Test
    void FindInjectionTargetShouldChooseSingleConstructor() throws InjectionException {
        Constructor<ClassWithOneConstructor> constructor = TypeAnalyzer.findInjectionTarget(ClassWithOneConstructor.class);

        assertEquals(0, constructor.getParameterCount());
    }

    @Test
    void TargetDependencyTypesShouldBeEmptyWithDefaultConstructor() throws InjectionException {
        assertEquals(0, TypeAnalyzer.collectTargetDependencyTypes(ClassWithOneConstructor.class).length);
    }

    @Test
    void TargetDependencyTypesShouldContainTopLevel() throws InjectionException {
        Class<?>[] types = TypeAnalyzer.collectTargetDependencyTypes(ServiceWithDependencyOnRepositoryAndService.class);

        assertEquals(2, types.length);
        assertArrayEquals(new Class<?>[]{RepositoryWithDefaultConstructor.class, ServiceWithDependencyOnRepository.class}, types);
    }

    @Test
    void RecursiveTargetDependencyTypesShouldContainEverything() throws InjectionException {
        Class<?>[] types = TypeAnalyzer.collectTargetDependencyTypesRecursively(ServiceWithDependencyOnRepositoryAndService.class);

        assertEquals(3, types.length);
        assertArrayEquals(new Class<?>[]{RepositoryWithDefaultConstructor.class, ServiceWithDependencyOnRepository.class, DifferentRepositoryWithDefaultConstructor.class}, types);
    }

    /**
     * Diese Dummy Klasse definiert keinen Konstruktor und ist somit nicht für die Dependency Injection geeignet.
     */
    private static class ClassWithoutConstructors {
    }

    /**
     * Diese Dummy Klasse definiert mehrere Konstruktoren, die mit der Injected Annotation versehen sind.
     */
    private static class ClassWithMultipleAnnotatedConstructors {
        @Inject
        public ClassWithMultipleAnnotatedConstructors() {
        }

        @Inject
        public ClassWithMultipleAnnotatedConstructors(String s) {
        }
    }

    /**
     * Diese Dummy Klasse definiert mehrere Konstruktoren, wobei keiner entsprechend annotiert wurde.
     */
    private static class ClassWithMultipleButNotAnnotatedConstructors {
        public ClassWithMultipleButNotAnnotatedConstructors() {
        }

        public ClassWithMultipleButNotAnnotatedConstructors(String s) {
        }
    }

    /**
     * Diese Dummy Klasse definiert genau einen Konstruktor, der automatisch für die Dependency Injection ausgewählt wird.
     */
    private static class ClassWithOneConstructor {
        public ClassWithOneConstructor() {
        }
    }

    /**
     * Diese drei Dummy Klassen dienen zum Testen der rekursiven Auflösung der Abhängigkeiten.
     */
    private static class ServiceWithDependencyOnRepositoryAndService {
        public ServiceWithDependencyOnRepositoryAndService(
                RepositoryWithDefaultConstructor repository,
                ServiceWithDependencyOnRepository service) {
        }
    }

    private static class ServiceWithDependencyOnRepository {
        public ServiceWithDependencyOnRepository(DifferentRepositoryWithDefaultConstructor repository) {
        }
    }

    private static class RepositoryWithDefaultConstructor {
        public RepositoryWithDefaultConstructor() {
        }
    }

    private static class DifferentRepositoryWithDefaultConstructor {
        public DifferentRepositoryWithDefaultConstructor() {
        }
    }

}
