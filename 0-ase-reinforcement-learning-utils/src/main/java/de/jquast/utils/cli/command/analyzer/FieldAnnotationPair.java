package de.jquast.utils.cli.command.analyzer;

import java.lang.reflect.Field;

public record FieldAnnotationPair<T>(Field field, T annotation) {
}
