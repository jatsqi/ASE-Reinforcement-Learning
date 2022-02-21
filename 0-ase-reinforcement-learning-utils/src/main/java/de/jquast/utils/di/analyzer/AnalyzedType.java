package de.jquast.utils.di.analyzer;

import java.lang.reflect.Constructor;

public record AnalyzedType<T>(Class<?> clazz, Constructor<T> injectionTarget, Class<?>[] dependencies) {
}
