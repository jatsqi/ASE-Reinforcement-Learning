package de.jquast.utils.cli.command.impl;

import de.jquast.utils.cli.command.CommandFactory;
import de.jquast.utils.cli.command.analyzer.AnalyzedCommand;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.exception.CommandException;
import de.jquast.utils.di.InjectionContext;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.exception.InjectionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
        for (int i = 1; i < parts.length; ++i) {
            String part = parts[i];

            for (AnalyzedCommand<?> subCommand : current.analyzedSubCommands()) {
                if (subCommand.command().name().equalsIgnoreCase(part)) {
                    current = subCommand;
                } else {
                    break;
                }
            }
        }



        return null;
    }

    private Object createCommandInstanceRecursively(String[] parts, int currentIndex, AnalyzedCommand<?> current, Object parent) throws InjectionException {
        if (currentIndex >= parts.length || !parts[currentIndex].equalsIgnoreCase(current.command().name())) {
            return parent;
        }

        Constructor target = current.analyzedType().injectionTarget();
        Object[] dependencies = new Object[target.getParameterCount()];
        int startIndex = 0;

        if (parent != null) {
            dependencies[0] = parent;
            startIndex = 1;
        }

        for (int i = startIndex; i < dependencies.length; ++i) {
            dependencies[i] = context.createNewInstance(target.getParameterTypes()[i]);
        }

        try {
            Object obj = target.newInstance(dependencies);
            return createCommandInstanceRecursively(parts, currentIndex + 1, current, obj);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
