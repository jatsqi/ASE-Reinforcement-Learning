package de.jquast.adapters.mapper;

import de.jquast.adapters.facade.dto.ConfigItemDto;
import de.jquast.adapters.facade.dto.RLSettingsDto;
import de.jquast.adapters.facade.mapper.ConfigMapper;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.config.ConfigItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigMapperTest {

    private ConfigMapper mapper;

    @BeforeEach
    void prepare() {
        mapper = new ConfigMapper();
    }

    @Test
    void dtoAttributesShouldHaveSameValue() {
        ConfigItem item = new ConfigItem("key_nice", "value_best");
        Optional<ConfigItemDto> dtoOp = mapper.toDto(Optional.of(item));
        assertTrue(dtoOp.isPresent());

        ConfigItemDto dto = dtoOp.get();
        assertEquals("key_nice", dto.key());
        assertEquals("value_best", dto.value());
    }

    @Test
    void rlSettingsShouldHaveSameValues() {
        RLSettings settings = new RLSettings(
                1.0, 0.3, 0.2, 0.8
        );
        RLSettingsDto dto = mapper.toDto(settings);

        assertEquals(1.0, dto.learningRate());
        assertEquals(0.3, dto.discountFactor());
        assertEquals(0.2, dto.explorationRate());
        assertEquals(0.8, dto.agentRewardStepSize());
    }

    @Test
    void mappedDomainObjectShouldHaveSameValues() {
        ConfigItemDto itemDto = new ConfigItemDto("key_nice", "value_best");
        ConfigItem item = mapper.fromDto(itemDto);

        assertEquals("key_nice", item.name());
        assertEquals("value_best", item.value());
    }

    @Test
    void emptyOptionalShouldReturnEmptyDtoOptional() {
        assertTrue(mapper.toDto(Optional.empty()).isEmpty());
    }


}
