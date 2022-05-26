package de.jquast.utils.cli.command.converter.impl;

import de.jquast.utils.cli.command.converter.Converter;

public class PrimitiveConverter implements Converter {

    @Override
    public Object convertArgument(String source, Class<?> target) {
        if (target.equals(Integer.class) || target.equals(int.class)) {
            return Integer.parseInt(source);
        } else if (target.equals(Float.class) || target.equals(float.class)) {
            return Float.parseFloat(source);
        } else if (target.equals(Long.class) || target.equals(long.class)) {
            return Long.parseLong(source);
        }

        return null;
    }
}
