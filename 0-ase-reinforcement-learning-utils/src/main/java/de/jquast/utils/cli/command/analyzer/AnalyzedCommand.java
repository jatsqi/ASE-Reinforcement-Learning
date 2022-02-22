package de.jquast.utils.cli.command.analyzer;

import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.di.analyzer.AnalyzedType;

import java.util.List;

public record AnalyzedCommand<T>(
        Command command,
        AnalyzedType<T> analyzedType,
        List<OptionField> options,
        List<ParameterField> parameters,
        AnalyzedCommand<?>[] analyzedSubCommands) {
}