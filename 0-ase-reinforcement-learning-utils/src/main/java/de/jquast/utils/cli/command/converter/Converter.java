package de.jquast.utils.cli.command.converter;

public interface Converter {

    Object convertArgument(String source, Class<?> target);

}
