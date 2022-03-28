package de.jquast.utils.cli.command;

import de.jquast.utils.cli.command.analyzer.AnalyzedCommand;
import de.jquast.utils.cli.command.exception.CommandCreationException;

public interface CommandFactory {

    Object createCommandInstance(String rawCommand, AnalyzedCommand<?> cmdMetadata) throws CommandCreationException;

}
