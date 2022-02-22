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

    private static final Map<Class<?>, AnalyzedCommand<?>> analyzedCommandCache = new HashMap<>();

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
        List<FieldAnnotationPair<Option>> cmdOptions = cmdOptionFields.stream().map(
                field -> new FieldAnnotationPair<Option>(field, field.getAnnotation(Option.class))).toList();
        List<FieldAnnotationPair<Parameter>> cmdParameters = cmdOptionFields.stream().map(
                field -> new FieldAnnotationPair<Parameter>(field, field.getAnnotation(Parameter.class))).toList();

        AnalyzedCommand<T> analyzedCommand = new AnalyzedCommand<>(metadata, cmdType, cmdOptions, cmdParameters);
        analyzedCommandCache.put(clazz, analyzedCommand);
        return analyzedCommand;
    }

}
