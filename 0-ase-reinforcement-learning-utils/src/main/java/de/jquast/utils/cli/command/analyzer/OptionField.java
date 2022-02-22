package de.jquast.utils.cli.command.analyzer;

import de.jquast.utils.cli.command.annotations.Option;

import java.lang.reflect.Field;

public record OptionField(Field field,
                          Option optionAnnotation) {

    public boolean isBooleanOption() {
        return field.getType().equals(boolean.class) || field.getType().equals(Boolean.class);
    }
}
