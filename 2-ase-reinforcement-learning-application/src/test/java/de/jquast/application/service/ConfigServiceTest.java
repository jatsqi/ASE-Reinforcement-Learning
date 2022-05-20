package de.jquast.application.service;

import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.config.ConfigRepository;
import de.jquast.domain.config.DefaultConfigItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceTest {

    @Mock
    ConfigRepository repository;

    @InjectMocks
    ConfigService service;

    @Before
    public void setupMocks() {
        MockitoAnnotations.openMocks(this);

        List<ConfigItem> items = Arrays.asList(
                new ConfigItem(DefaultConfigItem.ALGORITHM_LEARNING_RARE.getKey(), DefaultConfigItem.ALGORITHM_LEARNING_RARE.getDefaultValue())
        );

        when(repository.getConfigItems()).thenReturn(items);

        // getConfigItems
        for (ConfigItem item : items) {
            when(repository.getConfigItem(item.name())).thenReturn(Optional.of(item));
        }
        when(repository.getConfigItem(anyString())).thenReturn(Optional.empty());

        when(repository.setConfigItem(any())).thenReturn(true);
    }

    @Test
    public void getConfigItemWithNameShouldReturnCorrectItem() {
        Optional<ConfigItem> keyOp = service.getConfigItem(DefaultConfigItem.ALGORITHM_LEARNING_RARE.getKey());

        assertTrue(keyOp.isPresent());

        ConfigItem item = keyOp.get();
        assertEquals(DefaultConfigItem.ALGORITHM_LEARNING_RARE.getKey(), item.name());
        assertEquals(DefaultConfigItem.ALGORITHM_LEARNING_RARE.getDefaultValue(), item.value());
    }

}
