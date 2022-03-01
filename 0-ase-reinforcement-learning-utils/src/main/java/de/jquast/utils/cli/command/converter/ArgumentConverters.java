package de.jquast.utils.cli.command.converter;

import de.jquast.utils.cli.command.exception.TypeConversionException;

import java.util.Collection;

public interface ArgumentConverters {

    default Object convert(String source, Class<?> target) throws TypeConversionException {
        if (target.equals(String.class))
            return source;

        for (Converter converter : getConverters()) {
            Object converted = converter.convertArgument(source, target);
            if (converted != null)
                return converted;
        }

        throw new TypeConversionException(source, target, "Kein passender Konverter gefunden.");
    }

    Collection<Converter> getConverters();

    void addConverter(Converter converter);

}
