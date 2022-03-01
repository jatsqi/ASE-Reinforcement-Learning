package de.jquast.utils.cli.command.converter;

import java.util.ArrayList;
import java.util.List;

public class ArgumentConverters {

    private List<Converter> converters = new ArrayList<>();

    public ArgumentConverters() {}

    public void addConverter(Converter converter) {
        converters.add(converter);
    }

    public Object convert(String source, Class<?> target) {
        for (Converter converter : converters) {
            Object converted = converter.convertArgument(source, target)
            if (converted != null)
                return converted;
        }

        return null;
    }

}
