package de.jquast.utils.reflection;

import static org.junit.jupiter.api.Assertions.*;

import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.di.annotations.Mapping;
import de.jquast.utils.model.Human;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.List;

public class ReflectionUtilsTest {

    @Test
    void FindFieldsNameAndAgeShouldNotThrow() {
        assertDoesNotThrow(() -> ReflectionUtils.findField(Human.class, "name"));
        assertDoesNotThrow(() -> ReflectionUtils.findField(Human.class, "age"));
    }

    @Test
    void FindFieldsNameAndAgeShouldNotBeNull() throws NoSuchFieldException {
        assertNotNull(ReflectionUtils.findField(Human.class, "name"));
        assertNotNull(ReflectionUtils.findField(Human.class, "age"));
    }

    @Test
    void FindFieldsNotPresentShouldThrow() {
        assertThrows(NoSuchFieldException.class, () -> ReflectionUtils.findField(Human.class, "birthdate"));
    }

    @Test
    void GetFieldsShouldReturnCorrect() throws NoSuchFieldException, IllegalAccessException {
        Object age = ReflectionUtils.getField(ReflectionUtils.findField(Human.class, "age"), Human.createDummyHuman());
        Object name = ReflectionUtils.getField(ReflectionUtils.findField(Human.class, "name"), Human.createDummyHuman());

        assertEquals(22, age);
        assertEquals("Johannes Quast", name);
    }

    @Test
    void SetFieldsShouldSetPublicName() throws NoSuchFieldException, IllegalAccessException {
        Human dummy = Human.createDummyHuman();

        ReflectionUtils.setField("name", dummy, "Donald Trump");
        assertEquals("Donald Trump", dummy.getName());
        assertEquals(22, dummy.getAge());

        ReflectionUtils.setField(ReflectionUtils.findField(Human.class, "name"), dummy, "Angela Merkel");
        assertEquals("Angela Merkel", dummy.getName());
        assertEquals(22, dummy.getAge());
    }

    @Test
    void SetFieldsShouldSetPrivateAge() throws NoSuchFieldException, IllegalAccessException {
        Human dummy = Human.createDummyHuman();

        ReflectionUtils.setField("age", dummy, 99);
        assertEquals("Johannes Quast", dummy.getName());
        assertEquals(99, dummy.getAge());

        ReflectionUtils.setField(ReflectionUtils.findField(Human.class, "age"), dummy, 100);
        assertEquals("Johannes Quast", dummy.getName());
        assertEquals(100, dummy.getAge());
    }

    @Test
    void FindAnnotatedConstructorsShouldReturnCorrect() {
        List<Constructor<Human>> constructors = ReflectionUtils.findConstructorAnnotatedWith(Human.class, Inject.class);

        assertEquals(1, constructors.size());
        assertEquals(1, constructors.get(0).getParameterCount());
        assertEquals(String.class, constructors.get(0).getParameterTypes()[0]);
    }

    @Test
    void FindAnnotatedConstructorsWithUnusedAnnotationShouldReturnNothing() {
        List<Constructor<Human>> constructors = ReflectionUtils.findConstructorAnnotatedWith(Human.class, Mapping.class);

        assertEquals(0, constructors.size());
    }

}
