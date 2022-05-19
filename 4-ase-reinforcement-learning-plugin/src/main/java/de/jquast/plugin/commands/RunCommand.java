package de.jquast.plugin.commands;

import de.jquast.application.service.ExecutionService;
import de.jquast.domain.agent.Agent;
import de.jquast.domain.policy.PolicyVisualizer;
import de.jquast.domain.policy.VisualizationFormat;
import de.jquast.plugin.algorithm.QLearning;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.di.annotations.Inject;
import exception.StartAgentTrainingException;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Command(
        name = "run",
        description = "Training des Agenten starten"
)
public class RunCommand implements Runnable {

    @Option(names = "--envopts", description = "Optionen für das Environment.", defaultValue = "")
    public String environmentOptions;

    @Option(names = "--agent", description = "Name des Agenten.", required = true)
    public String agentName;

    @Option(names = "--environment", description = "Name des Environment.", required = true)
    public String environmentName;

    @Option(names = "--steps", description = "Anzahl der Trainingschritte.", required = true)
    public int steps;

    private final ExecutionService executionService;

    @Inject
    public RunCommand(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Override
    public void run() {
        try {
            System.out.println("Starte Training mit:");
            System.out.println("    Environment: " + environmentName);
            System.out.println("    Agent: " + agentName);
            System.out.println("    Optionen: " + environmentOptions);
            System.out.println("    Steps: " + steps);

            Optional<PolicyVisualizer> vis = executionService.startAgentTraining(agentName, environmentName, environmentOptions, steps);

            try {
                System.out.println(new String(vis.get().visualize(VisualizationFormat.TEXT), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (StartAgentTrainingException e) {
            System.out.println("ERRRRROR");
            e.printStackTrace();
        }
    }
}
