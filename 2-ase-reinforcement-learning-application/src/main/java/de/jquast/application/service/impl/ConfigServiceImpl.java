package de.jquast.application.service.impl;

import de.jquast.application.config.DefaultConfigItem;
import de.jquast.application.service.ConfigService;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.config.ConfigRepository;
import de.jquast.utils.di.annotations.Inject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    @Inject
    public ConfigServiceImpl(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public Collection<ConfigItem> getConfigItems() {
        return configRepository.getConfigItems();
    }

    public Collection<String> getAvailableConfigKeys() {
        return Arrays.stream(DefaultConfigItem.values()).map(DefaultConfigItem::getKey).toList();
    }

    public Optional<ConfigItem> setConfigItem(String key, String value) {
        if (DefaultConfigItem.findItem(key).isEmpty())
            return Optional.empty();

        ConfigItem item = new ConfigItem(key.toUpperCase(), value);

        if (!configRepository.setConfigItem(item)) {
            return Optional.empty();
        }

        return Optional.of(item);
    }

    public Optional<ConfigItem> getConfigItem(String name) {
        Optional<DefaultConfigItem> item = DefaultConfigItem.findItem(name);
        if (item.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(getConfigItem(item.get()));
    }

    public ConfigItem getConfigItem(DefaultConfigItem item) {
        Optional<ConfigItem> configItem = configRepository.getConfigItem(item.getKey());
        if (configItem.isEmpty())
            return item.createNewItem();

        return configItem.get();
    }

    public RLSettings getRLSettings() {
        RLSettings settings = new RLSettings(
                getDoubleConfigItem(DefaultConfigItem.ALGORITHM_LEARNING_RATE),
                getDoubleConfigItem(DefaultConfigItem.ALGORITHM_DISCOUNT_FACTOR),
                getDoubleConfigItem(DefaultConfigItem.ALGORITHM_EXPLORATION_RATE),
                getDoubleConfigItem(DefaultConfigItem.AGENT_REWARD_UPDATE_STEP_SIZE)
        );

        return settings;
    }

    private double getDoubleConfigItem(DefaultConfigItem item) {
        return Double.parseDouble(
                configRepository.getConfigItem(item.getKey())
                        .orElse(new ConfigItem("", "0.0"))
                        .value());
    }

}
