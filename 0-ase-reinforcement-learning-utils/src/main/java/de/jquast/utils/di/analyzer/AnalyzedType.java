package de.jquast.utils.di.analyzer;

import java.lang.reflect.Constructor;
import java.util.List;

public record AnalyzedType<T>(Class<T> clazz, Constructor<T> injectionTarget, Class<?>[] dependencies) {
}
