package com.objective.informa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MensagemMapperTest {

    private MensagemMapper mensagemMapper;

    @BeforeEach
    public void setUp() {
        mensagemMapper = new MensagemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(mensagemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mensagemMapper.fromId(null)).isNull();
    }
}
