package de.jquast.plugin.commands;

import de.jquast.application.algorithm.RLAlgorithmService;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.annotations.Inject;

@Command(
        name = "algorithm",
        description = "Gibt "
)
public class AlgorithmCommand implements Runnable {

    private final RLAlgorithmService algorithmService;

    @Parameter(index = 0, description = "Name eines Algorithmus.", required = false)
    public String algorithm;

    @Inject
    public AlgorithmCommand(RLAlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @Override
    public void run() {
        if (algorithm == null) {

        }
    }
}
