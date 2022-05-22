package de.jquast.adapters.facade.mapper;

import de.jquast.adapters.facade.dto.ActionDto;
import de.jquast.domain.shared.Action;

public class ActionMapper {

    public ActionDto toDto(Action action) {
        return new ActionDto(
                action.getId(),
                action.name(),
                action.getDescription()
        );
    }

}
