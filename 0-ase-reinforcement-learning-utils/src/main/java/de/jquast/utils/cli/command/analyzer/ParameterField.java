package de.jquast.utils.cli.command.analyzer;

import de.jquast.utils.cli.command.annotations.Parameter;

import java.lang.reflect.Field;

public record ParameterField(Field field,
                             Parameter parameterAnnotation) {

}
