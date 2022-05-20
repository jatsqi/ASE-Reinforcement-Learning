package de.jquast.plugin.repository;

import de.jquast.application.config.DefaultConfigItem;
import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.config.ConfigRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class PropertiesConfigRepository implements ConfigRepository {

    private static final String FILE_NAME = "config.properties";

    private Map<String, ConfigItem> configItems = new HashMap<>();

    public PropertiesConfigRepository() {
        for (DefaultConfigItem item : DefaultConfigItem.values()) {
            configItems.put(item.getKey(), new ConfigItem(item.getKey(), item.getDefaultValue()));
        }
    }

    @Override
    public Collection<ConfigItem> getConfigItems() {
        refreshConfigItems();

        return configItems.values();
    }

    @Override
    public Optional<ConfigItem> getConfigItem(String name) {
        refreshConfigItems();

        return Optional.ofNullable(configItems.get(name.toUpperCase()));
    }

    @Override
    public boolean isConfigItemPresent(String name) {
        return configItems.containsKey(name.toUpperCase());
    }

    @Override
    public boolean setConfigItem(ConfigItem item) {
        refreshConfigItems();
        configItems.put(item.name().toUpperCase().replace("=", "").trim(), item);
        saveConfigItems();

        return true;
    }

    private void refreshConfigItems() {
        if (!Files.exists(Paths.get(FILE_NAME))) {
            return;
        }

        try {
            Scanner scanner = new Scanner(new File(FILE_NAME));
            String line = null;

            while (scanner.hasNext() && (line = scanner.nextLine()) != null) {
                String[] parts = line.split("=");

                configItems.put(parts[0].toUpperCase(), new ConfigItem(parts[0], parts[1]));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveConfigItems() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {


            for (ConfigItem item : configItems.values()) {
                writer.write(String.format("%s=%s\n", item.name(), item.value()));
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
