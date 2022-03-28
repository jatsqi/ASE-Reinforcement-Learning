package de.jquast.utils.cli.command;

import de.jquast.utils.cli.command.exception.CommandException;

public interface CommandExecutionEngine {

    void execute(String command) throws CommandException;

    void registerTopLevelCommand(Class<?> clazz);

}
