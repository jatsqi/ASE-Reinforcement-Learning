package de.jquast.utils.cli.command.impl;

import de.jquast.utils.cli.command.CommandExecutionEngine;
import de.jquast.utils.cli.command.CommandFactory;
import de.jquast.utils.cli.command.analyzer.AnalyzedCommand;
import de.jquast.utils.cli.command.analyzer.CommandAnalyzer;
import de.jquast.utils.cli.command.analyzer.OptionField;
import de.jquast.utils.cli.command.analyzer.ParameterField;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.cli.command.exception.CommandAnalyzerException;
import de.jquast.utils.cli.command.exception.CommandCreationException;
import de.jquast.utils.cli.command.exception.CommandException;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.utils.di.annotations.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class StatelessCommandExecutionEngine implements CommandExecutionEngine {

    private final CommandFactory commandFactory;
    private final Map<String, AnalyzedCommand<?>> commandRegistry = new HashMap<>();

    @Inject
    public StatelessCommandExecutionEngine(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;

        registerTopLevelCommand(DefaultHelpCommand.class);
    }

    @Override
    public void execute(String command) throws CommandException {
        String[] parts = command.split(" ");
        if (parts.length <= 0) {
            throw new CommandException(command, "Der Command darf keine leere Zeichenkette sein.");
        }

        String topLevelCommand = parts[0];
        if (!commandRegistry.containsKey(topLevelCommand)) {
            System.out.println(generateCommandList());
            throw new CommandException(topLevelCommand, String.format("Der angegebene Befehl wurde nicht gefunden. \n%s", generateCommandList()));
        }

        // Command Object erstellen
        AnalyzedCommand<?> analyzedTopLevelCommand = commandRegistry.get(topLevelCommand);
        Object commandInstance = null;
        try {
            commandInstance = commandFactory.createCommandInstance(command, analyzedTopLevelCommand);
        } catch (CommandCreationException e) {
            throw new CommandException(command, String.format("%s", generateUsage(e.getAffectedCommand())));
        }

        if (!Runnable.class.isAssignableFrom(commandInstance.getClass())) {
            throw new CommandException(command, "Das Objekt des Commands ist nicht ausführbar, bitte Runnable implementieren.");
        }

        // Command ausführen
        ((Runnable) commandInstance).run();
    }

    @Override
    public void registerTopLevelCommand(Class<?> clazz) {
        try {
            AnalyzedCommand<?> analyzedCommand = CommandAnalyzer.analyze(clazz);
            commandRegistry.put(analyzedCommand.command().name(), analyzedCommand);
        } catch (CommandAnalyzerException e) {
            e.printStackTrace();
        }
    }

    private String generateCommandList() {
        StringBuilder builder = new StringBuilder();
        builder.append("Liste der zur Verfügung stehenden Befehle: \n");

        for (AnalyzedCommand<?> cmd : commandRegistry.values()) {
            builder.append(String.format("  %-15s%s\n", cmd.command().name(), cmd.command().description()));
        }

        return builder.toString();
    }

    private String generateUsage(AnalyzedCommand<?> cmd) {
        StringBuilder builder = new StringBuilder();

        builder.append("Korrekte Nutzung:\n");
        builder.append("  Parameter:\n");
        for (ParameterField paraField : cmd.parameters()) {
            Parameter parameter = paraField.parameterAnnotation();

            builder.append(String.format("    [#%d] %s\n", parameter.index(), parameter.description()));
        }

        builder.append("  Optionen:\n");
        for (OptionField optionField : cmd.options()) {
            Option option = optionField.optionAnnotation();

            builder.append(String.format("    %-20s %s\n", String.join(", ", option.names()), option.description()));
        }

        return builder.toString();
    }

    @Command(
            name = "help",
            description = "Gibt hilfreiche Informationen aus."
    )
    public class DefaultHelpCommand implements Runnable {

        @Override
        public void run() {
            System.out.println(generateCommandList());
        }
    }

}
