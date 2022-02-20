package de.jquast.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static void setField(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getField(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Field findField(Class<?> cls, String name) {
        try {
            cls.getField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
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
