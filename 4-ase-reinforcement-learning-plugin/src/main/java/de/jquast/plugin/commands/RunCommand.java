package de.jquast.plugin.commands;

import de.jquast.adapters.facade.ConfigServiceFacade;
import de.jquast.adapters.facade.ExecutionServiceFacade;
import de.jquast.application.config.DefaultConfigItem;
import de.jquast.application.exception.StartSzenarioException;
import de.jquast.application.service.SzenarioExecutionObserver;
import de.jquast.application.session.SzenarioSession;
import de.jquast.domain.policy.visualizer.VisualizationFormat;
import de.jquast.domain.shared.PersistedStoreInfo;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.di.annotations.Inject;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;

@Command(
        name = "run",
        description = "Ein bestimmtes Szenario für einen Agenten starten"
)
public class RunCommand implements Runnable {

    private final ExecutionServiceFacade executionService;
    private final ConfigServiceFacade configService;

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
    public RunCommand(ExecutionServiceFacade executionService, ConfigServiceFacade configService) {
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

            long stepInterval = Long.parseLong(
                    configService.getConfigItem(DefaultConfigItem.MESSAGE_TRAINING_AVERAGE_REWARD_STEPS).value());
            SzenarioExecutionObserver observer = createObserver(stepInterval);

            if (!evalMode) {
                executionService.startTraining(
                        agentName,
                        environmentName,
                        environmentOptions,
                        steps,
                        resumeFromStoreId,
                        Optional.of(observer));
            } else {
                executionService.startEvaluation(
                        agentName,
                        environmentName,
                        environmentOptions,
                        steps,
                        resumeFromStoreId,
                        Optional.of(observer));
            }
        } catch (StartSzenarioException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Erstellt Observer, um Trainingsfortschritt zu beobachten und Konsolenausgaben zu tätigen.
     *
     * @param stepInterval Anzahl der Schritte, nach dem eine neue Nachricht ausgegeben wird.
     * @return Ersteller Observer.
     */
    private SzenarioExecutionObserver createObserver(final long stepInterval) {
        return new SzenarioExecutionObserver() {
            private long lastStep = 0;

            @Override
            public void onSzenarioStart(SzenarioSession session) {
                System.out.println();
                System.out.println(session.getSzenario().settings());
                System.out.println();
            }

            @Override
            public void postSzenarioStep(SzenarioSession session, long currentStep, double averageReward) {
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

            @Override
            public void onSzenarioEnd(SzenarioSession session, double averageReward) {
                System.out.println();
                System.out.println(
                        new String(session.getSzenario().visualizer().visualize(VisualizationFormat.TEXT), StandardCharsets.UTF_8)
                );
                System.out.println();
            }

            @Override
            public void onActionStorePersisted(PersistedStoreInfo info) {
                System.out.printf("Policy wurde unter ID '%d' gespeichert.", info.id());
            }
        };
    }
}
