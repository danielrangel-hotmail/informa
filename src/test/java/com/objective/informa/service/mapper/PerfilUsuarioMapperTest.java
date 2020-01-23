package com.objective.informa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PerfilUsuarioMapperTest {

    private PerfilUsuarioMapper perfilUsuarioMapper;

    @BeforeEach
    public void setUp() {
        perfilUsuarioMapper = new PerfilUsuarioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(perfilUsuarioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(perfilUsuarioMapper.fromId(null)).isNull();
    }
}
