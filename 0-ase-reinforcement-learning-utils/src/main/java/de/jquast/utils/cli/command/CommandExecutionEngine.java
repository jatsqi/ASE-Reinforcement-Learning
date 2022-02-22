package de.jquast.utils.cli.command;

public interface CommandExecutionEngine {

    boolean execute(String command);

    void registerCommand(Class<?> clazz);

}
