package de.jquast.utils.cli.command.converter;

import de.jquast.utils.cli.command.exception.TypeConversionException;

import java.util.ArrayList;
import java.util.List;

public class ArgumentConverters {

    private List<Converter> converters = new ArrayList<>();

    public ArgumentConverters() {}

    public void addConverter(Converter converter) {
        converters.add(converter);
    }

    public Object convert(String source, Class<?> target) throws TypeConversionException {
        for (Converter converter : converters) {
            Object converted = converter.convertArgument(source, target)
            if (converted != null)
                return converted;
        }

        throw new TypeConversionException(source, target, "Kein passender Konverter gefunden.");
    }

}
