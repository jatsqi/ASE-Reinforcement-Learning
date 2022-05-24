package de.jquast.adapters.mapper;

import de.jquast.adapters.facade.dto.ActionDto;
import de.jquast.adapters.facade.dto.AgentDescriptorDto;
import de.jquast.adapters.facade.dto.RLAlgorithmDescriptorDto;
import de.jquast.adapters.facade.mapper.AgentMapper;
import de.jquast.adapters.facade.mapper.AlgorithmMapper;
import de.jquast.domain.agent.AgentDescriptor;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import de.jquast.domain.shared.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmMapperTest {

    private AlgorithmMapper mapper;

    @BeforeEach
    public void prepare() {
        mapper = new AlgorithmMapper();
    }

    @Test
    public void dtoAttributesShouldHaveSameValue() {
        RLAlgorithmDescriptor descriptor = new RLAlgorithmDescriptor(
                "my cool algorithm",
                "this is a cool algorithm",
                null
        );

        Optional<RLAlgorithmDescriptorDto> dtoOp = mapper.toDto(Optional.of(descriptor));
        assertTrue(dtoOp.isPresent());

        RLAlgorithmDescriptorDto dto = dtoOp.get();
        assertEquals("my cool algorithm", dto.name());
        assertEquals("this is a cool algorithm", dto.description());
    }

    @Test
    public void emptyOptionalShouldReturnEmptyDtoOptional() {
        assertTrue(mapper.toDto(Optional.empty()).isEmpty());
    }


}
