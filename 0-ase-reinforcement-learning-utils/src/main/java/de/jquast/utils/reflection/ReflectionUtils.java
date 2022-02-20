package de.jquast.utils.reflection;

import java.lang.reflect.Field;

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

}
