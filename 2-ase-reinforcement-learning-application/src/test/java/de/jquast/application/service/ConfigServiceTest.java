package de.jquast.application.service;

import de.jquast.application.config.DefaultConfigItem;
import de.jquast.application.service.impl.ConfigServiceImpl;
import de.jquast.domain.algorithm.RLSettings;
import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.config.ConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ConfigServiceTest {

    @Mock
    ConfigRepository repository;

    @InjectMocks
    ConfigServiceImpl service;

    @BeforeEach
    void setupMocks() {
        MockitoAnnotations.openMocks(this);

        Collection<ConfigItem> items = Arrays.asList(
                new ConfigItem(DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey(), DefaultConfigItem.ALGORITHM_LEARNING_RATE.getDefaultValue())
        );

        // getConfigItems
        when(repository.getConfigItems()).thenReturn(items);

        // getConfigItems
        for (ConfigItem item : items) {
            when(repository.getConfigItem(item.name())).thenReturn(Optional.of(item));
        }

        // setConfigItem
        when(repository.setConfigItem(any())).thenReturn(true);
    }

    @Test
    void getConfigItemWithNameShouldReturnCorrectItem() {
        Optional<ConfigItem> keyOp = service.getConfigItem(DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey());

        assertTrue(keyOp.isPresent());

        ConfigItem item = keyOp.get();
        assertEquals(DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey(), item.name());
        assertEquals(DefaultConfigItem.ALGORITHM_LEARNING_RATE.getDefaultValue(), item.value());
    }

    @Test
    void getConfigItemShouldReturnEmptyWithInvalidKey() {
        Optional<ConfigItem> keyOp = service.getConfigItem("this is invalid");

        assertTrue(keyOp.isEmpty());
    }

    @Test
    void setConfigItemShouldReturnFalseWithInvalidItem() {
        Optional<ConfigItem> keyOp = service.getConfigItem("this is invalid");

        assertTrue(keyOp.isEmpty());
    }

    @Test
    void getConfigItemShouldReturnTrueWithValidItemRegardlessOfCasing() {
        String key = DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey();

        assertTrue(service.getConfigItem(key).isPresent());
        assertTrue(service.getConfigItem(key.toUpperCase()).isPresent());
        assertTrue(service.getConfigItem(key.toLowerCase()).isPresent());
    }

    @Test
    void setConfigItemShouldReturnTrueWithValidItem() {
        Optional<ConfigItem> item = service.setConfigItem(
                DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey(),
                DefaultConfigItem.ALGORITHM_LEARNING_RATE.getDefaultValue());

        assertTrue(item.isPresent());
    }

    @Test
    void getConfigItemsShouldReturnCorrectItems() {
        assertEquals(
                service.getConfigItems(),
                Arrays.asList(
                        new ConfigItem(
                                DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey(),
                                DefaultConfigItem.ALGORITHM_LEARNING_RATE.getDefaultValue())
                )
        );
    }

    @Test
    void getAvailableConfigKeysShouldReturnAllEnumKeys() {
        String[] keys = service.getAvailableConfigKeys().toArray(new String[0]);
        assertEquals(DefaultConfigItem.values().length, keys.length);

        for (int i = 0; i < DefaultConfigItem.values().length; ++i) {
            assertEquals(DefaultConfigItem.values()[i].getKey(), keys[i]);
        }
    }

    @Test
    void getRLSettingsShouldConvertValues() {
        RLSettings settings = service.getRLSettings();

        assertEquals(
                Double.parseDouble(DefaultConfigItem.ALGORITHM_LEARNING_RATE.getDefaultValue()),
                settings.learningRate());
        assertEquals(
                Double.parseDouble(DefaultConfigItem.ALGORITHM_EXPLORATION_RATE.getDefaultValue()),
                settings.explorationRate());
        assertEquals(
                Double.parseDouble(DefaultConfigItem.ALGORITHM_DISCOUNT_FACTOR.getDefaultValue()),
                settings.discountFactor());
        assertEquals(
                Double.parseDouble(DefaultConfigItem.AGENT_REWARD_UPDATE_STEP_SIZE.getDefaultValue()),
                settings.agentRewardStepSize());
    }

}
