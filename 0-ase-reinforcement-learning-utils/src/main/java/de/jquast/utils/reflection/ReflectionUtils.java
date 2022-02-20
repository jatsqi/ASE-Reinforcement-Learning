package de.jquast.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static void setField(Field field, Object target, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(target, value);
    }

    public static Object getField(Field field, Object target) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(target);
    }

    public static void setField(String name, Object target, Object value) throws NoSuchFieldException, IllegalAccessException {
        setField(findField(target.getClass(), name), target, value);
    }

    public static Object getField(String name, Object target) throws NoSuchFieldException, IllegalAccessException {
        return getField(findField(target.getClass(), name), target);
    }

    public static Field findField(Class<?> cls, String name) throws NoSuchFieldException {
        try {
            Field field = cls.getField(name);
            return field;
        } catch (NoSuchFieldException e) {
            return cls.getDeclaredField(name);
        }
    }

    public static <T> List<Constructor<T>> findConstructorAnnotatedWith(Class<T> cls, Class<? extends Annotation> annotation) {
        List<Constructor<T>> constructors = new ArrayList<>();

        for (Constructor c : cls.getConstructors()) {
            if (c.isAnnotationPresent(annotation)) {
                constructors.add(c);
            }
        }

        return constructors;
    }

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
