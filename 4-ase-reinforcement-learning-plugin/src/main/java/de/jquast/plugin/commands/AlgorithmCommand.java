package de.jquast.plugin.commands;

import de.jquast.adapters.facade.RLAlgorithmServiceFacade;
import de.jquast.adapters.facade.dto.RLAlgorithmDescriptorDto;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.annotations.Inject;

import java.util.Optional;

@Command(
        name = "algorithm",
        description = "Gibt Informationen über die unterstützten Algorithmen aus."
)
public class AlgorithmCommand implements Runnable {

    private final RLAlgorithmServiceFacade algorithmService;

    @Parameter(index = 0, description = "Name eines Algorithmus.", required = false)
    public String algorithm;

    @Inject
    public AlgorithmCommand(RLAlgorithmServiceFacade algorithmService) {
        this.algorithmService = algorithmService;
    }

    private static void printAlgorithm(RLAlgorithmDescriptorDto descriptor) {
        System.out.println(String.format("  %s: %s", descriptor.name(), descriptor.description()));
    }

    @Override
    public void run() {
        Optional<RLAlgorithmDescriptorDto> toPrint = algorithmService.getAlgorithm(algorithm);

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
