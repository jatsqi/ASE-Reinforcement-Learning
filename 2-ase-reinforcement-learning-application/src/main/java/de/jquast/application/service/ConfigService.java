package de.jquast.application.service;

import de.jquast.application.config.DefaultConfigItem;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.config.ConfigItem;

import java.util.Collection;
import java.util.Optional;

public interface ConfigService {

    Collection<ConfigItem> getConfigItems();

    Collection<String> getAvailableConfigKeys();

    Optional<ConfigItem> setConfigItem(String key, String value);

    Optional<ConfigItem> getConfigItem(String name);

    ConfigItem getConfigItem(DefaultConfigItem item);

    RLSettings getRLSettings();

}
