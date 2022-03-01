package de.jquast.utils.cli.command.impl;

import de.jquast.utils.cli.command.CommandExecutionEngine;
import de.jquast.utils.cli.command.CommandFactory;
import de.jquast.utils.cli.command.analyzer.AnalyzedCommand;
import de.jquast.utils.cli.command.analyzer.CommandAnalyzer;
import de.jquast.utils.cli.command.exception.CommandAnalyzerException;
import de.jquast.utils.cli.command.exception.CommandException;
import de.jquast.utils.di.annotations.Inject;

import java.util.HashMap;
import java.util.Map;

public class StatelessCommandExecutionEngine implements CommandExecutionEngine {

    private final CommandFactory commandFactory;
    private final Map<String, AnalyzedCommand<?>> commandRegistry = new HashMap<>();

    @Inject
    public StatelessCommandExecutionEngine(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void execute(String command) throws CommandException {
        String[] parts = command.split(" ");
        if (parts.length <= 0) {
            throw new CommandException(command, "Der Command darf keine leere Zeichenkette sein.");
        }

        String topLevelCommand = parts[0];
        if (!commandRegistry.containsKey(topLevelCommand)) {
            throw new CommandException(topLevelCommand, "Der angegebene Befehl wurde nicht gefunden.");
        }

        // Command Object erstellen
        AnalyzedCommand<?> analyzedTopLevelCommand = commandRegistry.get(topLevelCommand);
        Object commandInstance = commandFactory.createCommandInstance(command, analyzedTopLevelCommand);

        if (!commandInstance.getClass().isAssignableFrom(Runnable.class)) {
            throw new CommandException(command, "Das Objekt des Commands ist nicht ausführbar, bitte Runnable implementieren.");
        }

        // Command ausführen
        ((Runnable) commandInstance).run();
    }

    @Override
    public void registerCommand(Class<?> clazz) {
        try {
            AnalyzedCommand<?> analyzedCommand = CommandAnalyzer.analyze(clazz);
            commandRegistry.put(analyzedCommand.command().name(), analyzedCommand);
        } catch (CommandAnalyzerException e) {
            e.printStackTrace();
        }
    }
}
