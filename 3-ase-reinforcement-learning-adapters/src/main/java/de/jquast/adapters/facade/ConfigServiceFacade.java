package de.jquast.adapters.facade;

import de.jquast.adapters.facade.dto.ConfigItemDto;
import de.jquast.adapters.facade.dto.RLSettingsDto;
import de.jquast.application.config.DefaultConfigItem;

import java.util.Collection;
import java.util.Optional;

public interface ConfigServiceFacade {

    Collection<ConfigItemDto> getConfigItems();

    Collection<String> getAvailableConfigKeys();

    Optional<ConfigItemDto> setConfigItem(String key, String value);

    Optional<ConfigItemDto> getConfigItem(String name);

    ConfigItemDto getConfigItem(DefaultConfigItem item);

    RLSettingsDto getRLSettings();

}
