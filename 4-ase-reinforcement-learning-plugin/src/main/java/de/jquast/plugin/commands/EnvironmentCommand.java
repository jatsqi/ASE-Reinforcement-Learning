package de.jquast.plugin.commands;

import de.jquast.adapters.facade.EnvironmentServiceFacade;
import de.jquast.adapters.facade.dto.EnvironmentDescriptorDto;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.annotations.Inject;

import java.util.Optional;

@Command(
        name = "env",
        description = "Gibt Informationen über die unterstützten Umgebungen aus."
)
public class EnvironmentCommand implements Runnable {

    @Parameter(index = 0, description = "Name des Environments", required = false)
    public String name;

    private EnvironmentServiceFacade service;

    @Inject
    public EnvironmentCommand(EnvironmentServiceFacade service) {
        this.service = service;
    }

    @Override
    public void run() {
        Optional<EnvironmentDescriptorDto> descriptor = service.getEnvironmentInfo(name);

        if (descriptor.isEmpty()) {
            printEnvironments();
        } else {
            System.out.println(descriptor.toString());
        }
    }

    private void printEnvironments() {
        System.out.println("Environments:");

        for (EnvironmentDescriptorDto descriptor : service.getEnvironmentInfos()) {
            System.out.println(String.format("  %s", descriptor.toString()));
        }
    }
}
