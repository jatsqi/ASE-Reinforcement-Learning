package de.jquast.plugin.commands;

import de.jquast.application.algorithm.RLAlgorithmService;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.annotations.Inject;

import java.util.Optional;

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

    private static void printAlgorithm(RLAlgorithmDescriptor descriptor) {
        System.out.println(String.format("  %s: %s", descriptor.name(), descriptor.description()));
    }

    @Override
    public void run() {
        Optional<RLAlgorithmDescriptor> toPrint = algorithmService.getAlgorithm(algorithm);

        if (toPrint.isEmpty()) {
            printAlgorithms();
        } else {
            printAlgorithm(toPrint.get());
        }
    }

    private void printAlgorithms() {
        System.out.println("Algorithmen: ");
        algorithmService.getAlgorithms().forEach(AlgorithmCommand::printAlgorithm);
    }
}
