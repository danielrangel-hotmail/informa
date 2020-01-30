package com.objective.informa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TopicoMapperTest {

    private TopicoMapper topicoMapper;

    @BeforeEach
    public void setUp() {
        topicoMapper = new TopicoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(topicoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(topicoMapper.fromId(null)).isNull();
    }
}
