package de.jquast.plugin.commands;

import de.jquast.application.service.impl.EnvironmentServiceImpl;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.annotations.Inject;

import java.util.Optional;

@Command(
        name = "env",
        description = "tbd",
        help = "tbd"
)
public class EnvironmentCommand implements Runnable {

    @Parameter(index = 0, description = "Name des Environments", required = false)
    public String name;

    private EnvironmentServiceImpl service;

    @Inject
    public EnvironmentCommand(EnvironmentServiceImpl service) {
        this.service = service;
    }

    @Override
    public void run() {
        Optional<EnvironmentDescriptor> descriptor = service.getEnvironmentInfo(name);

        if (descriptor.isEmpty()) {
            printEnvironments();
        } else {
            System.out.println(descriptor.toString());
        }
    }

    private void printEnvironments() {
        System.out.println("Environments:");

        for (EnvironmentDescriptor descriptor : service.getEnvironmentsInfo()) {
            System.out.println(String.format("  %s", descriptor.toString()));
        }
    }
}
