package de.jquast.plugin.commands;

import de.jquast.adapters.facade.ConfigServiceFacade;
import de.jquast.adapters.facade.dto.ConfigItemDto;
import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Parameter;
import de.jquast.utils.di.annotations.Inject;

import java.util.Optional;

@Command(
        name = "config",
        description = "Verwaltet die Konfiguration der Anwendung.",
        subcommands = {
                ConfigCommand.ConfigSetCommand.class,
                ConfigCommand.ConfigGetCommand.class
        }
)
public class ConfigCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("Bitte 'config get' oder 'config set' nutzen.");
    }

    @Command(
            name = "set",
            description = "Setzt einen bestimmten Eintrag in der Config auf den übergebenen Wert."
    )
    public class ConfigSetCommand implements Runnable {

        private final ConfigServiceFacade configService;
        @Parameter(index = 0, description = "Der betroffene Key, der geändert werden soll.")
        public String key;
        @Parameter(index = 1, description = "Die Value, auf die der Key gesetzt werden soll.")
        public String value;

        @Inject
        public ConfigSetCommand(ConfigServiceFacade service) {
            this.configService = service;
        }

        @Override
        public void run() {
            Optional<ConfigItemDto> created = configService.setConfigItem(key, value);

            if (created.isEmpty()) {
                System.out.println("Ungültiger Key, verfügbare Config Keys: ");
                configService.getAvailableConfigKeys().forEach(System.out::println);
                return;
            }

            ConfigItemDto unwrapped = created.get();
            System.out.printf("Key '%s' erfolgreich auf '%s' gesetzt.%n", unwrapped.key(), unwrapped.value());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Command(
            name = "get",
            description = "Gibt einen bestimmten Key aus."
    )
    public class ConfigGetCommand implements Runnable {

        public final ConfigServiceFacade configService;
        @Parameter(index = 0, description = "Der Key eines Config Items.", required = false)
        public String key;

        @Inject
        public ConfigGetCommand(ConfigServiceFacade service) {
            this.configService = service;
        }

        @Override
        public void run() {
            if (key == null || key.isEmpty()) {
                for (ConfigItemDto item : configService.getConfigItems()) {
                    System.out.println(item);
                }

                return;
            }

            Optional<ConfigItemDto> item = configService.getConfigItem(key.trim());

            if (item.isEmpty()) {
                System.out.println("Ungültiger Key, verfügbare Config Keys: ");
                configService.getAvailableConfigKeys().forEach(System.out::println);
                return;
            }

            ConfigItemDto unwrapped = item.get();
            System.out.println(unwrapped);
        }
    }

}
