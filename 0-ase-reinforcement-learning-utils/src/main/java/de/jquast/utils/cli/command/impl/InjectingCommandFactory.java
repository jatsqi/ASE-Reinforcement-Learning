package de.jquast.utils.cli.command.impl;

import de.jquast.utils.cli.command.CommandFactory;
import de.jquast.utils.cli.command.analyzer.AnalyzedCommand;
import de.jquast.utils.cli.command.analyzer.OptionField;
import de.jquast.utils.cli.command.analyzer.ParameterField;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.cli.command.arguments.ArgumentParser;
import de.jquast.utils.cli.command.arguments.Arguments;
import de.jquast.utils.di.InjectionContext;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.exception.InjectionException;
import de.jquast.utils.reflection.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

public class InjectingCommandFactory implements CommandFactory {

    private final InjectionContext context;

    @Inject
    public InjectingCommandFactory(InjectionContext context) {
        this.context = context;
    }

    @Override
    public Object createCommandInstance(String rawCommand, AnalyzedCommand<?> cmdMetadata) {
        String[] parts = rawCommand.split(" ");
        if (!parts[0].equalsIgnoreCase(cmdMetadata.command().name())) {
            throw new RuntimeException("Command <-> Metadata mismatch!");
        }

        AnalyzedCommand<?> current = cmdMetadata;
        int firstNonMatchingIndex = 1;

        for (int i = 1; i < parts.length; ++i) {
            String part = parts[i];

            for (AnalyzedCommand<?> subCommand : current.analyzedSubCommands()) {
                if (subCommand.command().name().equalsIgnoreCase(part)) {
                    current = subCommand;
                } else {
                    firstNonMatchingIndex = i;
                    break;
                }
            }
        }

        Arguments parsedArguments = ArgumentParser.parseArguments(parts, firstNonMatchingIndex);

        try {
            Object newInstance = context.createNewInstance(current.analyzedType().clazz());

            try {
                injectArguments(newInstance, parsedArguments, current);
                injectParameters(newInstance, parsedArguments, current);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return newInstance;
        } catch (InjectionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void injectArguments(Object obj, Arguments arguments, AnalyzedCommand<?> commandMetadata) throws IllegalAccessException {
        Map<String, OptionField> mappedOptions = new HashMap<>();
        commandMetadata.options().forEach(option -> {
            for (String name : option.optionAnnotation().names())
                mappedOptions.put(name, option);
        });

        for (OptionField optionField : commandMetadata.options()) {
            Option annotation = optionField.optionAnnotation();
            boolean found = false;

            // Suche Option
            for (String name : annotation.names()) {
                if (arguments.options().containsKey(name)) {
                    if (found)
                        throw new RuntimeException("Duplicated Option!");

                    ReflectionUtils.setField(optionField.field(), obj, arguments.options().get(name));
                    found = true;
                }
            }

            if (!found && annotation.required())
                throw new RuntimeException("Options " + String.join(", ", annotation.names()) + " not found.");
        }
    }

    private void injectParameters(Object obj, Arguments arguments, AnalyzedCommand<?> commandMetadata) throws IllegalAccessException {
        for (ParameterField parameterField : commandMetadata.parameters()) {
            Parameter parameter = parameterField.parameterAnnotation();

            if (parameter.index() >= arguments.parameters().size())
                throw new RuntimeException("Not enough parameters!");

            ReflectionUtils.setField(parameterField.field(), obj, arguments.parameters().get(parameter.index()));
        }
    }
}
