package de.jquast.adapters.mapper;

import de.jquast.adapters.facade.dto.ActionDto;
import de.jquast.adapters.facade.dto.AgentDescriptorDto;
import de.jquast.adapters.facade.dto.ConfigItemDto;
import de.jquast.adapters.facade.dto.EnvironmentDescriptorDto;
import de.jquast.adapters.facade.mapper.AgentMapper;
import de.jquast.adapters.facade.mapper.ConfigMapper;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.config.ConfigItem;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.shared.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AgentMapperTest {

    private AgentMapper mapper;

    @BeforeEach
    public void prepare() {
        mapper = new AgentMapper();
    }

    @Test
    public void dtoAttributesShouldHaveSameValue() {
        AgentDescriptor descriptor = new AgentDescriptor(
                "my cool agent",
                "this is a cool agent",
                new Action[]{ Action.DO_NOTHING, Action.MOVE_X_UP },
                2
        );

        Optional<AgentDescriptorDto> dtoOp = mapper.toDto(Optional.of(descriptor));
        assertTrue(dtoOp.isPresent());

        AgentDescriptorDto dto = dtoOp.get();
        assertEquals("my cool agent", dto.name());
        assertEquals("this is a cool agent", dto.description());
        assertEquals(2, dto.actionSpace());
        assertArrayEquals(new ActionDto[] {
                new ActionDto(Action.DO_NOTHING.getId(), Action.DO_NOTHING.name(), Action.DO_NOTHING.getDescription()),
                new ActionDto(Action.MOVE_X_UP.getId(), Action.MOVE_X_UP.name(), Action.MOVE_X_UP.getDescription())
        }, dto.requiredCapabilities());
    }

    @Test
    public void emptyOptionalShouldReturnEmptyDtoOptional() {
        assertTrue(mapper.toDto(Optional.empty()).isEmpty());
    }


}
