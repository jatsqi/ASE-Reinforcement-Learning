package de.jquast.adapters.mapper;

import de.jquast.adapters.facade.dto.RLAlgorithmDescriptorDto;
import de.jquast.adapters.facade.mapper.AlgorithmMapper;
import de.jquast.domain.algorithm.RLAlgorithmDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlgorithmMapperTest {

    private AlgorithmMapper mapper;

    @BeforeEach
    void prepare() {
        mapper = new AlgorithmMapper();
    }

    @Test
    void dtoAttributesShouldHaveSameValue() {
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
    void emptyOptionalShouldReturnEmptyDtoOptional() {
        assertTrue(mapper.toDto(Optional.empty()).isEmpty());
    }


}
