package de.jquast.utils.cli.command.exception;

import de.jquast.utils.cli.command.analyzer.AnalyzedCommand;

public class CommandCreationException extends Exception {

    private AnalyzedCommand<?> affectedCommand;

    public CommandCreationException(String message, AnalyzedCommand<?> affectedCommand) {
        super(String.format("Es gab einen Fehler beim Erstellen des Befehls: %s", message));
        this.affectedCommand = affectedCommand;
    }

    public AnalyzedCommand<?> getAffectedCommand() {
        return affectedCommand;
    }
}
