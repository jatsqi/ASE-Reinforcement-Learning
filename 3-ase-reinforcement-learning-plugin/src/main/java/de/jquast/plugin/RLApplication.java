package de.jquast.plugin;

import de.jquast.plugin.commands.ConfigCommand;
import de.jquast.utils.cli.command.CommandExecutionEngine;
import de.jquast.utils.cli.command.CommandFactory;
import de.jquast.utils.cli.command.converter.ArgumentConverters;
import de.jquast.utils.cli.command.converter.impl.DefaultArgumentConverters;
import de.jquast.utils.cli.command.exception.CommandException;
import de.jquast.utils.cli.command.impl.InjectingCommandFactory;
import de.jquast.utils.cli.command.impl.StatelessCommandExecutionEngine;
import de.jquast.utils.di.InjectionContext;
import de.jquast.utils.exception.InjectionException;

public class RLApplication {

    private static final InjectionContext CONTEXT;

    static {
        CONTEXT = new InjectionContext();

        CONTEXT.mapInterface(CommandExecutionEngine.class, StatelessCommandExecutionEngine.class);
        CONTEXT.mapInterface(CommandFactory.class, InjectingCommandFactory.class);
        CONTEXT.mapInterface(ArgumentConverters.class, DefaultArgumentConverters.class);
    }

    public static void main(String[] args) throws InjectionException, CommandException {
        CommandExecutionEngine engine = CONTEXT.createNewInstance(CommandExecutionEngine.class);
        engine.registerTopLevelCommand(ConfigCommand.class);

        engine.execute(String.join(" ", args));
    }

}
