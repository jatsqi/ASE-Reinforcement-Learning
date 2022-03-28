package de.jquast.plugin.commands;

import de.jquast.application.config.ConfigService;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.annotations.Inject;

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

        private final ConfigService configService;

        @Inject
        public ConfigSetCommand(ConfigService service) {
            this.configService = service;
        }

        @Parameter(index = 0, description = "Der betroffene Key, der geändert werden soll.")
        public String key;

        @Parameter(index = 1, description = "Die Value, auf die der Key gesetzt werden soll.")
        public String value;

        @Override
        public void run() {
            configService.setConfigItem(key, value);
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
