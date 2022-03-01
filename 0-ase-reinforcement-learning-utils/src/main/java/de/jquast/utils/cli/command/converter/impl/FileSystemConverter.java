package de.jquast.utils.cli.command.converter.impl;

import de.jquast.utils.cli.command.converter.Converter;

import java.io.File;

public class FileSystemConverter implements Converter {

    @Override
    public Object convertArgument(String source, Class<?> target) {
        if (target.equals(File.class)) {
            return new File(source);
        }

        return null;
    }
}
