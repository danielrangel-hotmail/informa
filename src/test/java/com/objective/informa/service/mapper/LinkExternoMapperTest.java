package com.objective.informa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LinkExternoMapperTest {

    private LinkExternoMapper linkExternoMapper;

    @BeforeEach
    public void setUp() {
        linkExternoMapper = new LinkExternoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(linkExternoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(linkExternoMapper.fromId(null)).isNull();
    }
}
