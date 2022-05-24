package de.jquast.adapters.mapper;

import de.jquast.adapters.facade.dto.ActionDto;
import de.jquast.adapters.facade.dto.ConfigItemDto;
import de.jquast.adapters.facade.dto.EnvironmentDescriptorDto;
import de.jquast.adapters.facade.dto.RLSettingsDto;
import de.jquast.adapters.facade.mapper.ActionMapper;
import de.jquast.adapters.facade.mapper.ConfigMapper;
import de.jquast.adapters.facade.mapper.EnvironmentMapper;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.shared.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfigMapperTest {

    private ConfigMapper mapper;

    @BeforeEach
    public void prepare() {
        mapper = new ConfigMapper();
    }

    @Test
    public void dtoAttributesShouldHaveSameValue() {
        ConfigItem item = new ConfigItem("key_nice", "value_best");
        Optional<ConfigItemDto> dtoOp = mapper.toDto(Optional.of(item));
        assertTrue(dtoOp.isPresent());

        ConfigItemDto dto = dtoOp.get();
        assertEquals("key_nice", dto.key());
        assertEquals("value_best", dto.value());
    }

    @Test
    public void rlSettingsShouldHaveSameValues() {
        RLSettings settings = new RLSettings(
                1.0, 2.0, 3.0, 4.0
        );
        RLSettingsDto dto = mapper.toDto(settings);

        assertEquals(1.0, dto.learningRate());
        assertEquals(2.0, dto.discountFactor());
        assertEquals(3.0, dto.explorationRate());
        assertEquals(4.0, dto.agentRewardStepSize());
    }

    @Test
    public void mappedDomainObjectShouldHaveSameValues() {
        ConfigItemDto itemDto = new ConfigItemDto("key_nice", "value_best");
        ConfigItem item = mapper.fromDto(itemDto);

        assertEquals("key_nice", item.name());
        assertEquals("value_best", item.value());
    }

    @Test
    public void emptyOptionalShouldReturnEmptyDtoOptional() {
        assertTrue(mapper.toDto(Optional.empty()).isEmpty());
    }


}
