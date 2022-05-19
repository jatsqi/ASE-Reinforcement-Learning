package de.jquast.application.service;

import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.config.ConfigRepository;
import de.jquast.domain.config.DefaultConfigItem;

public class RLSettingsService {

    private ConfigRepository configRepository;

    public RLSettingsService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public RLSettings getRLSettings() {
        RLSettings settings = new RLSettings(
                getDoubleConfigItem(DefaultConfigItem.ALGORITHM_LEARNING_RARE),
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
