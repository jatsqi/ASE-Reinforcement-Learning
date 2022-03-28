package de.jquast.domain.config;

import java.util.Collection;
import java.util.Optional;

public interface ConfigRepository {

    Collection<ConfigItem> getConfigItems();

    Optional<ConfigItem> getConfigItem(String name);

    boolean isConfigItemPresent(String name);

    boolean setConfigItem(ConfigItem item);

}
