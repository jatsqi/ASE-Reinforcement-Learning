package de.jquast.application.config;

import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.config.DefaultConfigItem;
import de.jquast.domain.config.ConfigRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ConfigService {

    private final ConfigRepository configRepository;

    @Inject
    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public Collection<ConfigItem> getConfigItems() {
        return configRepository.getConfigItems();
    }

    public Collection<String> getAvailableConfigKeys() {
        return Arrays.stream(DefaultConfigItem.values()).map(DefaultConfigItem::getKey).toList();
    }

    public Optional<ConfigItem> setConfigItem(String key, String value) {
        ConfigItem item = new ConfigItem(key.toUpperCase(), value);

        if (!configRepository.setConfigItem(item)) {
            return Optional.empty();
        }

        return Optional.of(item);
    }

    public Optional<ConfigItem> getConfigItem(String name) {
        try {
            DefaultConfigItem item = DefaultConfigItem.valueOf(name);
            return Optional.ofNullable(getConfigItem(item));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    public ConfigItem getConfigItem(DefaultConfigItem item) {
        Optional<ConfigItem> configItem = configRepository.getConfigItem(item.getKey());
        if (configItem.isEmpty())
            return item.createNewItem();

        return configItem.get();
    }

}
