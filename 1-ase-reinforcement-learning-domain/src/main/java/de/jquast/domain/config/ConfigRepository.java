package de.jquast.domain.config;

import java.util.Collection;

public interface ConfigRepository {

    Collection<ConfigItem> getConfigItems();

    ConfigItem getConfigItem(String name);

    boolean isConfigItemPresent(String name);

    boolean setConfigItem(ConfigItem item);

}
