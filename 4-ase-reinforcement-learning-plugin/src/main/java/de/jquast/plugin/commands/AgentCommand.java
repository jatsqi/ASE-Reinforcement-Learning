package de.jquast.plugin.commands;

import de.jquast.adapters.facade.AgentServiceFacade;
import de.jquast.adapters.facade.dto.AgentDescriptorDto;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.annotations.Inject;

import java.util.Optional;

@Command(
        name = "agent",
        description = "Informationen über Agenten abrufen",
        help = "tbd"
)
public class AgentCommand implements Runnable {

    @Parameter(index = 0, description = "Name des Agenten", required = false)
    public String name;

    private AgentServiceFacade service;

    @Inject
    public AgentCommand(AgentServiceFacade service) {
        this.service = service;
    }

    @Override
    public void run() {
        Optional<AgentDescriptorDto> descriptor = service.getAgent(name);

        if (descriptor.isEmpty()) {
            printAgents();
        } else {
            System.out.println(descriptor.toString());
        }
    }

    private void printAgents() {
        System.out.println("Agenten:");

        for (AgentDescriptorDto descriptor : service.getAgents()) {
            System.out.println(String.format("  %s", descriptor.toString()));
        }
    }

}
