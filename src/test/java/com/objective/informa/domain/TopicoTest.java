package com.objective.informa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class TopicoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Topico.class);
        Topico topico1 = new Topico();
        topico1.setId(1L);
        Topico topico2 = new Topico();
        topico2.setId(topico1.getId());
        assertThat(topico1).isEqualTo(topico2);
        topico2.setId(2L);
        assertThat(topico1).isNotEqualTo(topico2);
        topico1.setId(null);
        assertThat(topico1).isNotEqualTo(topico2);
    }
}
