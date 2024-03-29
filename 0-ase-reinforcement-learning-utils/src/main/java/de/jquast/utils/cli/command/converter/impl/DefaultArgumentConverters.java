package de.jquast.utils.cli.command.converter.impl;

import de.jquast.utils.cli.command.converter.ArgumentConverters;
import de.jquast.utils.cli.command.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultArgumentConverters implements ArgumentConverters {

    private List<Converter> converters = new ArrayList<>();

    public DefaultArgumentConverters() {
        addConverter(new FileSystemConverter());
        addConverter(new PrimitiveConverter());
    }

    @Override
    public Collection<Converter> getConverters() {
        return converters;
    }

    @Override
    public void addConverter(Converter converter) {
        converters.add(converter);
    }

}
