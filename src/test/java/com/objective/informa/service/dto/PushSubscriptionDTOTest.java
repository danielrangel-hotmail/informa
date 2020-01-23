package com.objective.informa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class PushSubscriptionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PushSubscriptionDTO.class);
        PushSubscriptionDTO pushSubscriptionDTO1 = new PushSubscriptionDTO();
        pushSubscriptionDTO1.setId(1L);
        PushSubscriptionDTO pushSubscriptionDTO2 = new PushSubscriptionDTO();
        assertThat(pushSubscriptionDTO1).isNotEqualTo(pushSubscriptionDTO2);
        pushSubscriptionDTO2.setId(pushSubscriptionDTO1.getId());
        assertThat(pushSubscriptionDTO1).isEqualTo(pushSubscriptionDTO2);
        pushSubscriptionDTO2.setId(2L);
        assertThat(pushSubscriptionDTO1).isNotEqualTo(pushSubscriptionDTO2);
        pushSubscriptionDTO1.setId(null);
        assertThat(pushSubscriptionDTO1).isNotEqualTo(pushSubscriptionDTO2);
    }
}
