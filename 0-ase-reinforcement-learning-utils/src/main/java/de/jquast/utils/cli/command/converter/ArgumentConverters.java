package de.jquast.utils.cli.command.converter;

import de.jquast.utils.cli.command.exception.TypeConversionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArgumentConverters {

    public Object convert(String source, Class<?> target) throws TypeConversionException {
        if (target.equals(String.class))
            return source;

        for (Converter converter : getConverters()) {
            Object converted = converter.convertArgument(source, target);
            if (converted != null)
                return converted;
        }

        throw new TypeConversionException(source, target, "Kein passender Konverter gefunden.");
    }

    public abstract Collection<Converter> getConverters();

    public abstract void addConverter(Converter converter);

}
