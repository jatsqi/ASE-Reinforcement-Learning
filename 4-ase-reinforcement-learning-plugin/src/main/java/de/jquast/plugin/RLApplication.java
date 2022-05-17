package de.jquast.plugin;

import de.jquast.domain.agent.AgentRepository;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.domain.config.ConfigRepository;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.plugin.commands.AgentCommand;
import de.jquast.plugin.commands.AlgorithmCommand;
import de.jquast.plugin.commands.ConfigCommand;
import de.jquast.plugin.commands.EnvironmentCommand;
import de.jquast.plugin.repository.InMemoryAgentRepository;
import de.jquast.plugin.repository.InMemoryAlgorithmRepository;
import de.jquast.plugin.repository.InMemoryEnvironmentRepository;
import de.jquast.plugin.repository.PropertiesConfigRepository;
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
        CONTEXT.mapInterface(ConfigRepository.class, PropertiesConfigRepository.class);
        CONTEXT.mapInterface(RLAlgorithmRepository.class, InMemoryAlgorithmRepository.class);
        CONTEXT.mapInterface(EnvironmentRepository.class, InMemoryEnvironmentRepository.class);
        CONTEXT.mapInterface(AgentRepository.class, InMemoryAgentRepository.class);
    }

    public static void main(String[] args) throws InjectionException, CommandException {
        CommandExecutionEngine engine = CONTEXT.createNewInstance(CommandExecutionEngine.class);
        engine.registerTopLevelCommand(ConfigCommand.class);
        engine.registerTopLevelCommand(AlgorithmCommand.class);
        engine.registerTopLevelCommand(EnvironmentCommand.class);
        engine.registerTopLevelCommand(AgentCommand.class);

        engine.execute(String.join(" ", args));
    }

}
