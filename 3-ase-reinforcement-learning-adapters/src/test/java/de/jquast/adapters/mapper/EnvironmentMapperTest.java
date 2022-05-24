package de.jquast.adapters.mapper;

import de.jquast.adapters.facade.dto.ActionDto;
import de.jquast.adapters.facade.dto.EnvironmentDescriptorDto;
import de.jquast.adapters.facade.mapper.EnvironmentMapper;
import de.jquast.domain.environment.EnvironmentDescriptor;
import de.jquast.domain.shared.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EnvironmentMapperTest {

    private EnvironmentMapper mapper;

    @BeforeEach
    public void prepare() {
        mapper = new EnvironmentMapper();
    }

    @Test
    public void dtoAttributesShouldHaveSameValue() {
        EnvironmentDescriptor descriptor = new EnvironmentDescriptor(
                "my cool environment",
                "this is a cool environment",
                new Action[]{ Action.DO_NOTHING, Action.MOVE_X_UP }
        );

        Optional<EnvironmentDescriptorDto> dtoOp = mapper.toDto(Optional.of(descriptor));
        assertTrue(dtoOp.isPresent());

        EnvironmentDescriptorDto dto = dtoOp.get();
        assertEquals("my cool environment", dto.name());
        assertEquals("this is a cool environment", dto.description());
        assertArrayEquals(new ActionDto[] {
                new ActionDto(Action.DO_NOTHING.getId(), Action.DO_NOTHING.name(), Action.DO_NOTHING.getDescription()),
                new ActionDto(Action.MOVE_X_UP.getId(), Action.MOVE_X_UP.name(), Action.MOVE_X_UP.getDescription())
        }, dto.supportedCapabilities());
    }

    @Test
    public void emptyOptionalShouldReturnEmptyDtoOptional() {
        assertTrue(mapper.toDto(Optional.empty()).isEmpty());
    }

}
