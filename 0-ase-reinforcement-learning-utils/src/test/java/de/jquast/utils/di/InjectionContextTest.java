package de.jquast.utils.di;

import static org.junit.jupiter.api.Assertions.*;

import de.jquast.utils.exception.InjectionException;
import org.junit.jupiter.api.Test;

public class InjectionContextTest {

    private interface Repository1 {}
    private interface Repository2 {}
    private interface Service {}

    private static class Repository1Impl implements Repository1 {
        public Repository1Impl() {}
    }
    private static class Repository2Impl implements Repository2 {
        public Repository2Impl() {}
    }
    private static class ServiceImpl implements Service {
        public ServiceImpl(Repository1 repository1, Repository2 repository2) {}
    }

    @Test
    void NewInstanceOfInterfaceShouldThrow() {
        InjectionContext context = new InjectionContext();
        assertThrows(InjectionException.class, () -> context.createNewInstance(Repository1.class));
    }

    @Test
    void NewInstanceOfEmptyClassShouldNotThrow() {
        InjectionContext context = new InjectionContext();
        assertDoesNotThrow(() -> context.createNewInstance(Repository1Impl.class));
    }

    @Test
    void NewInstanceShouldThrowIfInterfacesNotMapped() {
        InjectionContext context = new InjectionContext();
        assertThrows(InjectionException.class, () -> context.createNewInstance(ServiceImpl.class));

        context.mapInterface(Repository1.class, Repository1Impl.class);
        assertThrows(InjectionException.class, () -> context.createNewInstance(ServiceImpl.class));

        context.mapInterface(Repository2.class, Repository2Impl.class);
        assertDoesNotThrow(() -> context.createNewInstance(ServiceImpl.class));
    }

    @Test
    void NewInstanceShouldReturnSingletonObjectIfMarkedAsSuch() throws InjectionException {
        InjectionContext context = new InjectionContext();
        context.mapInterface(Repository1.class, Repository1Impl.class);
        context.mapInterface(Repository2.class, Repository2Impl.class);

        ServiceImpl expected = context.createNewInstance(ServiceImpl.class);
        assertNotSame(expected, context.createNewInstance(ServiceImpl.class));

        context.markAsSingleton(ServiceImpl.class);
        expected = context.createNewInstance(ServiceImpl.class);
        assertSame(expected, context.createNewInstance(ServiceImpl.class));
    }

    @Test
    void NewInstanceShouldInjectSameContext() throws InjectionException {
        InjectionContext context = new InjectionContext();
        assertSame(context, context.createNewInstance(InjectionContext.class));
    }

}
