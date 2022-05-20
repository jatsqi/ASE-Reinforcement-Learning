package de.jquast.application.service;

import de.jquast.application.config.DefaultConfigItem;
import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.config.ConfigRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceTest {

    @Mock
    ConfigRepository repository;

    @InjectMocks
    ConfigService service;

    @Before
    public void setupMocks() {
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
    public void getConfigItemWithNameShouldReturnCorrectItem() {
        Optional<ConfigItem> keyOp = service.getConfigItem(DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey());

        assertTrue(keyOp.isPresent());

        ConfigItem item = keyOp.get();
        assertEquals(DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey(), item.name());
        assertEquals(DefaultConfigItem.ALGORITHM_LEARNING_RATE.getDefaultValue(), item.value());
    }

    @Test
    public void getConfigItemShouldReturnEmptyWithInvalidKey() {
        Optional<ConfigItem> keyOp = service.getConfigItem("this is invalid");

        assertTrue(keyOp.isEmpty());
    }

    @Test
    public void setConfigItemShouldReturnFalseWithInvalidItem() {
        Optional<ConfigItem> keyOp = service.getConfigItem("this is invalid");

        assertTrue(keyOp.isEmpty());
    }

    @Test
    public void getConfigItemShouldReturnTrueWithValidItemRegardlessOfCasing() {
        String key = DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey();

        assertTrue(service.getConfigItem(key).isPresent());
        assertTrue(service.getConfigItem(key.toUpperCase()).isPresent());
        assertTrue(service.getConfigItem(key.toLowerCase()).isPresent());
    }

    @Test
    public void setConfigItemShouldReturnTrueWithValidItem() {
        Optional<ConfigItem> item = service.setConfigItem(
                DefaultConfigItem.ALGORITHM_LEARNING_RATE.getKey(),
                DefaultConfigItem.ALGORITHM_LEARNING_RATE.getDefaultValue());

        assertTrue(item.isPresent());
    }

    @Test
    public void getConfigItemsShouldReturnCorrectItems() {
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
    public void getAvailableConfigKeysShouldReturnAllEnumKeys() {
        String[] keys = service.getAvailableConfigKeys().toArray(new String[0]);
        assertEquals(DefaultConfigItem.values().length, keys.length);

        for (int i = 0; i < DefaultConfigItem.values().length; ++i) {
            assertEquals(DefaultConfigItem.values()[i].getKey(), keys[i]);
        }
    }

}
