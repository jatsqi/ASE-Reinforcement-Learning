package de.jquast.adapters.facade.impl;

import de.jquast.adapters.facade.ConfigServiceFacade;
import de.jquast.adapters.facade.dto.ConfigItemDto;
import de.jquast.adapters.facade.dto.RLSettingsDto;
import de.jquast.adapters.facade.mapper.ConfigMapper;
import de.jquast.application.config.DefaultConfigItem;
import de.jquast.application.service.ConfigService;
import de.jquast.utils.di.annotations.Inject;

import java.util.Collection;
import java.util.Optional;

public class ConfigServiceFacadeImpl implements ConfigServiceFacade {

    private static final ConfigMapper MAPPER = new ConfigMapper();

    private final ConfigService service;

    @Inject
    public ConfigServiceFacadeImpl(ConfigService service) {
        this.service = service;
    }

    @Override
    public Collection<ConfigItemDto> getConfigItems() {
        return service.getConfigItems().stream().map(MAPPER::toDto).toList();
    }

    @Override
    public Collection<String> getAvailableConfigKeys() {
        return service.getAvailableConfigKeys();
    }

    @Override
    public Optional<ConfigItemDto> setConfigItem(String key, String value) {
        return MAPPER.toDto(service.setConfigItem(key, value));
    }

    @Override
    public Optional<ConfigItemDto> getConfigItem(String name) {
        return MAPPER.toDto(service.getConfigItem(name));
    }

    @Override
    public ConfigItemDto getConfigItem(DefaultConfigItem item) {
        return MAPPER.toDto(service.getConfigItem(item));
    }

    @Override
    public RLSettingsDto getRLSettings() {
        return MAPPER.toDto(service.getRLSettings());
    }
}
