package com.objective.informa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PostReacaoMapperTest {

    private PostReacaoMapper postReacaoMapper;

    @BeforeEach
    public void setUp() {
        postReacaoMapper = new PostReacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(postReacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(postReacaoMapper.fromId(null)).isNull();
    }
}
