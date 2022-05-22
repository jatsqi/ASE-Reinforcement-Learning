package de.jquast.plugin;

import de.jquast.adapters.facade.*;
import de.jquast.adapters.facade.impl.*;
import de.jquast.application.service.*;
import de.jquast.application.service.impl.*;
import de.jquast.domain.agent.AgentFactory;
import de.jquast.domain.agent.AgentRepository;
import de.jquast.domain.algorithm.AlgorithmFactory;
import de.jquast.domain.algorithm.RLAlgorithmRepository;
import de.jquast.domain.config.ConfigRepository;
import de.jquast.domain.environment.EnvironmentFactory;
import de.jquast.domain.environment.EnvironmentRepository;
import de.jquast.domain.factory.*;
import de.jquast.domain.policy.PolicyFactory;
import de.jquast.domain.policy.PolicyRepository;
import de.jquast.domain.policy.visualizer.PolicyVisualizerFactory;
import de.jquast.domain.shared.ActionValueRepository;
import de.jquast.plugin.commands.*;
import de.jquast.plugin.repository.*;
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
        CONTEXT.mapInterface(ActionValueRepository.class, FileSystemActionValueRepository.class);
        CONTEXT.mapInterface(PolicyRepository.class, InMemoryPolicyRepository.class);

        CONTEXT.mapInterface(AgentService.class, AgentServiceImpl.class);
        CONTEXT.mapInterface(ConfigService.class, ConfigServiceImpl.class);
        CONTEXT.mapInterface(EnvironmentService.class, EnvironmentServiceImpl.class);
        CONTEXT.mapInterface(RLAlgorithmService.class, RLAlgorithmServiceImpl.class);
        CONTEXT.mapInterface(ExecutionService.class, ExecutionServiceImpl.class);

        CONTEXT.mapInterface(AgentServiceFacade.class, AgentServiceFacadeImpl.class);
        CONTEXT.mapInterface(ConfigServiceFacade.class, ConfigServiceFacadeImpl.class);
        CONTEXT.mapInterface(EnvironmentServiceFacade.class, EnvironmentServiceFacadeImpl.class);
        CONTEXT.mapInterface(ExecutionServiceFacade.class, ExecutionServiceFacadeImpl.class);
        CONTEXT.mapInterface(RLAlgorithmServiceFacade.class, RLAlgorithmServiceFacadeImpl.class);

        CONTEXT.mapInterface(EnvironmentFactory.class, SimpleEnvironmentFactory.class);
        CONTEXT.mapInterface(AgentFactory.class, SimpleAgentFactory.class);
        CONTEXT.mapInterface(PolicyFactory.class, SimplePolicyFactory.class);
        CONTEXT.mapInterface(AlgorithmFactory.class, SimpleAlgorithmFactory.class);
        CONTEXT.mapInterface(PolicyVisualizerFactory.class, SimplePolicyVisualizerFactory.class);
    }

    public static void main(String[] args) {
        CommandExecutionEngine engine = null;
        try {
            engine = CONTEXT.createNewInstance(CommandExecutionEngine.class);
        } catch (InjectionException e) {
            System.out.println("Beim Erstellen der Engine für das Ausführung von Befehlen ist ein kritischer Fehler aufgetreten.");
            return;
        }
        engine.registerTopLevelCommand(ConfigCommand.class);
        engine.registerTopLevelCommand(AlgorithmCommand.class);
        engine.registerTopLevelCommand(EnvironmentCommand.class);
        engine.registerTopLevelCommand(AgentCommand.class);
        engine.registerTopLevelCommand(RunCommand.class);

        try {
            engine.execute(String.join(" ", args));
        } catch (CommandException e) {
            System.out.printf(e.getMessage());
        }
    }

}
