package com.objective.informa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PushSubscriptionMapperTest {

    private PushSubscriptionMapper pushSubscriptionMapper;

    @BeforeEach
    public void setUp() {
        pushSubscriptionMapper = new PushSubscriptionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(pushSubscriptionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pushSubscriptionMapper.fromId(null)).isNull();
    }
}
