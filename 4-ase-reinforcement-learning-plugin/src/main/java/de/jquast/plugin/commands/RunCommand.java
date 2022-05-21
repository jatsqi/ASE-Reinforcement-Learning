package de.jquast.plugin.commands;

import de.jquast.application.config.DefaultConfigItem;
import de.jquast.application.service.ConfigService;
import de.jquast.application.service.ExecutionService;
import de.jquast.application.session.SzenarioProgressObserver;
import de.jquast.application.session.SzenarioSession;
import de.jquast.domain.policy.visualizer.VisualizationFormat;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.di.annotations.Inject;
import de.jquast.application.exception.StartAgentTrainingException;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Command(
        name = "run",
        description = "Ein bestimmtes Szenario für einen Agenten starten"
)
public class RunCommand implements Runnable {

    private final ExecutionService executionService;
    private final ConfigService configService;

    @Option(names = "--envopts", description = "Optionen für das Environment.", defaultValue = "")
    public String environmentOptions;
    @Option(names = "--agent", description = "Name des Agenten.", required = true)
    public String agentName;
    @Option(names = "--environment", description = "Name des Environment.", required = true)
    public String environmentName;
    @Option(names = "--steps", description = "Anzahl der Trainingschritte.", required = true)
    public int steps;
    @Option(names = "--resume", description = "Benutze Values eines vorherigen Trainings", defaultValue = "-1")
    public int resumeFromStoreId;
    @Option(names = "--eval", description = "Schalter, ob der Agent trainiert werden soll, oder ob der übergebene Store nur ausgeführt wird.")
    public boolean evalMode;

    @Inject
    public RunCommand(ExecutionService executionService, ConfigService configService) {
        this.executionService = executionService;
        this.configService = configService;
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

            SzenarioSession session;
            if (!evalMode) {
                session = executionService.createTrainingSession(
                        agentName,
                        environmentName,
                        environmentOptions,
                        steps,
                        resumeFromStoreId);
            } else {
                session = executionService.createEvaluationSession(
                        agentName,
                        environmentName,
                        environmentOptions,
                        steps,
                        resumeFromStoreId);
            }

            long stepInterval = Long.parseLong(
                    configService.getConfigItem(DefaultConfigItem.MESSAGE_TRAINING_AVERAGE_REWARD_STEPS).value());

            session.setObserver(new SzenarioProgressObserver() {
                private long lastStep = 0;

                @Override
                public void postTrainingStep(SzenarioSession session, long currentStep, double averageReward) {
                    if (currentStep - lastStep >= stepInterval) {
                        lastStep = currentStep;
                        System.out.printf(
                                Locale.US,
                                "Schritt %d/%d (%.2f%%), Durchschnittlicher Reward %f%n",
                                currentStep,
                                steps,
                                (float) currentStep / steps * 100,
                                averageReward);
                    }
                }
            });

            session.start();
            System.out.println(new String(session.getVisualizer().visualize(VisualizationFormat.TEXT), StandardCharsets.UTF_8));
        } catch (StartAgentTrainingException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }
}
