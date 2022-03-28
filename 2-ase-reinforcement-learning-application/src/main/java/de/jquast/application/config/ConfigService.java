package de.jquast.application.config;

import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.config.ConfigItems;
import de.jquast.domain.config.ConfigRepository;
import de.jquast.utils.di.annotations.Inject;

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

    public boolean setConfigItem(String key, String value) {
        return configRepository.setConfigItem(new ConfigItem(key, value));
    }

    public Optional<ConfigItem> getConfigItem(String name) {
        try {
            ConfigItems item = ConfigItems.valueOf(name);
            return getConfigItem(item);
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    public Optional<ConfigItem> getConfigItem(ConfigItems item) {
        return configRepository.getConfigItem(item.getKey());
    }

}
