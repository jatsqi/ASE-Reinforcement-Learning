package de.jquast.utils.cli.command;

import de.jquast.utils.cli.command.analyzer.AnalyzedCommand;

public interface CommandFactory {

    Object createCommandInstance(String rawCommand, AnalyzedCommand<?> cmdMetadata);

}
