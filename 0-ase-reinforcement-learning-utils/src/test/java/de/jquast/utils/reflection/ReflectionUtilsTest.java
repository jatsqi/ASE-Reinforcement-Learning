package de.jquast.utils.reflection;

import static org.junit.jupiter.api.Assertions.*;

import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.di.annotations.Mapping;
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
        Object age = ReflectionUtils.getField(ReflectionUtils.findField(Human.class, "age"), getTestHuman());
        Object name = ReflectionUtils.getField(ReflectionUtils.findField(Human.class, "name"), getTestHuman());

        assertEquals(age, 22);
        assertEquals(name, "Johannes Quast");
    }

    @Test
    void SetFieldsShouldSetPublicName() throws NoSuchFieldException, IllegalAccessException {
        Human dummy = getTestHuman();

        ReflectionUtils.setField("name", dummy, "Donald Trump");
        assertEquals(dummy.getName(), "Donald Trump");
        assertEquals(dummy.getAge(), 22);

        ReflectionUtils.setField(ReflectionUtils.findField(Human.class, "name"), dummy, "Angela Merkel");
        assertEquals(dummy.getName(), "Angela Merkel");
        assertEquals(dummy.getAge(), 22);
    }

    @Test
    void SetFieldsShouldSetPrivateAge() throws NoSuchFieldException, IllegalAccessException {
        Human dummy = getTestHuman();

        ReflectionUtils.setField("age", dummy, 99);
        assertEquals(dummy.getName(), "Johannes Quast");
        assertEquals(dummy.getAge(), 99);

        ReflectionUtils.setField(ReflectionUtils.findField(Human.class, "age"), dummy, 100);
        assertEquals(dummy.getName(), "Johannes Quast");
        assertEquals(dummy.getAge(), 100);
    }

    @Test
    void FindAnnotatedConstructorsShouldReturnCorrect() {
        List<Constructor<Human>> constructors = ReflectionUtils.findConstructorAnnotatedWith(Human.class, Inject.class);

        assertEquals(constructors.size(), 1);
        assertEquals(constructors.get(0).getParameterCount(), 1);
        assertEquals(constructors.get(0).getParameterTypes()[0], String.class);
    }

    @Test
    void FindAnnotatedConstructorsWithUnusedAnnotationShouldReturnNothing() {
        List<Constructor<Human>> constructors = ReflectionUtils.findConstructorAnnotatedWith(Human.class, Mapping.class);

        assertEquals(constructors.size(), 0);
    }

    private Human getTestHuman() {
        return new Human("Johannes Quast", 22);
    }

}
