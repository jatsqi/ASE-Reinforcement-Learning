package de.jquast.plugin.commands;

import de.jquast.application.service.ExecutionService;
import de.jquast.domain.agent.Agent;
import de.jquast.plugin.algorithm.QLearning;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.di.annotations.Inject;
import exception.StartAgentTrainingException;

@Command(
        name = "run",
        description = "Training des Agenten starten"
)
public class RunCommand implements Runnable {

    @Option(names = "--envopts", description = "Optionen für das Environment.")
    public String environmentOptions;

    private final ExecutionService executionService;

    @Inject
    public RunCommand(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Override
    public void run() {
        try {
            Agent agent = executionService.startAgentTraining("pull", "k-armed-bandit", "bandits=10", 10000);
            QLearning learning = (QLearning) agent.getActionSource();

            System.out.println("Best: " + learning.getActionValueStoreDelegate().getMaxActionValue(0));
        } catch (StartAgentTrainingException e) {
            e.printStackTrace();
        }
    }
}
