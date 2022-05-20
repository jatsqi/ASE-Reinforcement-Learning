package de.jquast.plugin.commands;

import de.jquast.application.service.ExecutionService;
import de.jquast.domain.policy.PolicyVisualizer;
import de.jquast.domain.policy.VisualizationFormat;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.application.exception.StartAgentTrainingException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Command(
        name = "run",
        description = "Ein bestimmtes Szenario für einen Agenten starten"
)
public class RunCommand implements Runnable {

    private final ExecutionService executionService;
    @Option(names = "--envopts", description = "Optionen für das Environment.", defaultValue = "")
    public String environmentOptions;
    @Option(names = "--agent", description = "Name des Agenten.", required = true)
    public String agentName;
    @Option(names = "--environment", description = "Name des Environment.", required = true)
    public String environmentName;
    @Option(names = "--steps", description = "Anzahl der Trainingschritte.", required = true)
    public int steps;
    @Option(names = "--init-from", description = "Init Environment from file.")
    public String initFromFile;
    @Option(names = "--resume", description = "Benutze Values eines vorherigen Trainings", defaultValue = "-1")
    public int resumeFromStoreId;
    @Option(names = "--eval", description = "Schalter, ob der Agent trainiert werden soll, oder ob der übergebene Store nur ausgeführt wird.")
    public boolean evalMode;

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
            System.out.println("    Store-ID: " + (resumeFromStoreId == -1 ? null : resumeFromStoreId));

            Optional<PolicyVisualizer> visualizer;
            if (!evalMode) {
                visualizer = executionService.startAgentTraining(
                        agentName,
                        environmentName,
                        environmentOptions,
                        steps,
                        initFromFile,
                        resumeFromStoreId);
            } else {
                visualizer = executionService.startAgentEvaluation(
                        agentName,
                        environmentName,
                        environmentOptions,
                        steps,
                        initFromFile,
                        resumeFromStoreId);
            }

            if (visualizer.isEmpty()) {
                System.out.println("Leider konnte keine Visualisierung für die Trainierte Policy erstellt werden :(");
                return;
            }

            System.out.println();
            System.out.println(new String(visualizer.get().visualize(VisualizationFormat.TEXT), StandardCharsets.UTF_8));
        } catch (StartAgentTrainingException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }
}
