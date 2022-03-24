package de.jquast.utils.cli.command;

import de.jquast.utils.cli.command.analyzer.AnalyzedCommand;
import de.jquast.utils.cli.command.exception.CommandCreationException;
import de.jquast.utils.cli.command.exception.CommandException;

public interface CommandFactory {

    Object createCommandInstance(String rawCommand, AnalyzedCommand<?> cmdMetadata) throws CommandCreationException;

}
