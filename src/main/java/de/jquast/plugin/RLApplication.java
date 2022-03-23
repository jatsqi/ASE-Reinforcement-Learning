package de.jquast.plugin;

import de.jquast.utils.cli.command.CommandExecutionEngine;
import de.jquast.utils.cli.command.CommandFactory;
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
    }

    public static void main(String[] args) throws InjectionException {
        CommandExecutionEngine engine = CONTEXT.createNewInstance(CommandExecutionEngine.class);


    }

}
