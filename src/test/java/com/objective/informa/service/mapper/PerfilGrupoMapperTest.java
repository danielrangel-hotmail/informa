package com.objective.informa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PerfilGrupoMapperTest {

    private PerfilGrupoMapper perfilGrupoMapper;

    @BeforeEach
    public void setUp() {
        perfilGrupoMapper = new PerfilGrupoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(perfilGrupoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(perfilGrupoMapper.fromId(null)).isNull();
    }
}
