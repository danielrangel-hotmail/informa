package com.objective.informa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class GrupoMapperTest {

    private GrupoMapper grupoMapper;

    @BeforeEach
    public void setUp() {
        grupoMapper = new GrupoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(grupoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(grupoMapper.fromId(null)).isNull();
    }
}
