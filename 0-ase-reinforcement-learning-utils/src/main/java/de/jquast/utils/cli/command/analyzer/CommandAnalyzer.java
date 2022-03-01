package de.jquast.utils.cli.command.analyzer;

import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.cli.command.exception.CommandAnalyzerException;
import de.jquast.utils.di.analyzer.AnalyzedType;
import de.jquast.utils.di.analyzer.TypeAnalyzer;
import de.jquast.utils.reflection.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandAnalyzer {

    /**
     * Cache für bereits analysierte Commands.
     */
    private static final Map<Class<?>, AnalyzedCommand<?>> analyzedCommandCache = new HashMap<>();

    /**
     * Analysiert den Typen {@code clazz} und extrahiert alle notwendigen Metadaten, die den Command betreffen.
     *
     * @param clazz                         Den zu analysierenden Typen.
     * @return                              Ein Objekt mit den extrahierten Metadaten.
     * @throws CommandAnalyzerException     Wenn der Typ keine Command Annotation aufweist.
     */
    public static <T> AnalyzedCommand<T> analyze(Class<?> clazz) throws CommandAnalyzerException {
        if (analyzedCommandCache.containsKey(clazz)) {
            return (AnalyzedCommand<T>) analyzedCommandCache.get(clazz);
        }

        if (!clazz.isAnnotationPresent(Command.class)) {
            throw new CommandAnalyzerException(clazz, "Der Typ besitzt keine Command Annotation.");
        }

        Command metadata = clazz.getAnnotation(Command.class);
        AnalyzedType<T> cmdType = TypeAnalyzer.analyze(clazz);

        List<Field> cmdOptionFields = ReflectionUtils.findDeclaredFieldsAnnotatedWith(clazz, Option.class);
        List<Field> cmdParameterFields = ReflectionUtils.findDeclaredFieldsAnnotatedWith(clazz, Parameter.class);
        List<OptionField> cmdOptions = cmdOptionFields.stream().map(
                field -> new OptionField(field, field.getAnnotation(Option.class))).toList();
        List<ParameterField> cmdParameters = cmdParameterFields.stream().map(
                field -> new ParameterField(field, field.getAnnotation(Parameter.class))).toList();

        AnalyzedCommand<?>[] analyzedSubCommands = new AnalyzedCommand[metadata.subcommands().length];
        for (int i = 0; i < metadata.subcommands().length; ++i) {
            analyzedSubCommands[i] = analyze(metadata.subcommands()[i]);
        }

        AnalyzedCommand<T> analyzedCommand = new AnalyzedCommand<>(
                metadata,
                cmdType,
                cmdOptions,
                cmdParameters,
                analyzedSubCommands);

        analyzedCommandCache.put(clazz, analyzedCommand);
        return analyzedCommand;
    }

}
