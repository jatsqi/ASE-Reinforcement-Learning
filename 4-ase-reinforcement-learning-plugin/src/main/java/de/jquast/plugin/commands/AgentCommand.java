package de.jquast.plugin.commands;

import de.jquast.application.service.impl.AgentServiceImpl;
import de.jquast.domain.agent.AgentDescriptor;
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

    private AgentServiceImpl service;

    @Inject
    public AgentCommand(AgentServiceImpl service) {
        this.service = service;
    }

    @Override
    public void run() {
        Optional<AgentDescriptor> descriptor = service.getAgent(name);

        if (descriptor.isEmpty()) {
            printAgents();
        } else {
            System.out.println(descriptor.toString());
        }
    }

    private void printAgents() {
        System.out.println("Agenten:");

        for (AgentDescriptor descriptor : service.getAgents()) {
            System.out.println(String.format("  %s", descriptor.toString()));
        }
    }

}
