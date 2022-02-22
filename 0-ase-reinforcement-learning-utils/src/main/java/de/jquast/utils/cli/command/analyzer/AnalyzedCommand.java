package de.jquast.utils.cli.command.analyzer;

import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.analyzer.AnalyzedType;

import java.lang.reflect.Field;
import java.util.List;

public record AnalyzedCommand<T>(
        Command command,
        AnalyzedType<T> analyzedType,
        List<FieldAnnotationPair<Option>> options,
        List<FieldAnnotationPair<Parameter>> parameters,
        AnalyzedCommand<?>[] analyzedSubCommands) {
}