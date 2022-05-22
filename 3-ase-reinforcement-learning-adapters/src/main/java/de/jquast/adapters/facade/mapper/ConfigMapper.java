package de.jquast.adapters.facade.mapper;

import de.jquast.adapters.facade.dto.ConfigItemDto;
import de.jquast.adapters.facade.dto.RLSettingsDto;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.config.ConfigItem;

import java.util.Optional;

public class ConfigMapper {

    public ConfigItemDto toDto(ConfigItem item) {
        return new ConfigItemDto(item.name(), item.value());
    }

    public Optional<ConfigItemDto> toDto(Optional<ConfigItem> optionalConfigItem) {
        if (optionalConfigItem.isEmpty())
            return Optional.empty();

        return Optional.of(toDto(optionalConfigItem.get()));
    }

    public RLSettingsDto toDto(RLSettings settings) {
        return new RLSettingsDto(settings.learningRate(), settings.discountFactor(), settings.explorationRate(), settings.agentRewardStepSize());
    }

    public ConfigItem fromDto(ConfigItemDto configItemDto) {
        return new ConfigItem(configItemDto.key(), configItemDto.value());
    }

}
