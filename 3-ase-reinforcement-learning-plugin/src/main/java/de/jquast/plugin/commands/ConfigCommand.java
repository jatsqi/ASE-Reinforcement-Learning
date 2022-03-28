package de.jquast.plugin.commands;

import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;

@Command(
        name = "config",
        description = "Verwaltet die Konfiguration der Anwendung.",
        subcommands = {ConfigCommand.ConfigSetCommand.class, ConfigCommand.ConfigDelCommand.class}
)
public class ConfigCommand {

    @Command(
            name = "set",
            description = "Setzt einen bestimmten Eintrag in der Config auf den übergebenen Wert."
    )
    public class ConfigSetCommand implements Runnable {

        @Parameter(index = 0, description = "Der betroffene Key, der geändert werden soll.")
        public String key;

        @Parameter(index = 1, description = "Die Value, auf die der Key gesetzt werden soll.")
        public String value;

        @Override
        public void run() {
            System.out.println(key);
            System.out.println(value);
        }
    }

    @Command(
            name = "del",
            description = "Löscht einen bestimmten Eintrag aus der Config."
    )
    public class ConfigDelCommand implements Runnable {

        @Override
        public void run() {

        }
    }

}
