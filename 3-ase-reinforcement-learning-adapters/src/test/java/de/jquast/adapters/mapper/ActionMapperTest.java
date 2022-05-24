package de.jquast.adapters.mapper;

import de.jquast.adapters.facade.dto.ActionDto;
import de.jquast.adapters.facade.mapper.ActionMapper;
import de.jquast.domain.shared.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionMapperTest {

    @Test
    public void dtoAttributesShouldHaveSameValue() {
        ActionMapper mapper = new ActionMapper();

        for (Action action : Action.values()) {
            ActionDto dto = mapper.toDto(action);

            assertEquals(action.getId(), dto.id());
            assertEquals(action.name(), dto.name());
            assertEquals(action.getDescription(), dto.description());
        }
    }

}
