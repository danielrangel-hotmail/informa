package com.objective.informa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class MensagemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MensagemDTO.class);
        MensagemDTO mensagemDTO1 = new MensagemDTO();
        mensagemDTO1.setId(1L);
        MensagemDTO mensagemDTO2 = new MensagemDTO();
        assertThat(mensagemDTO1).isNotEqualTo(mensagemDTO2);
        mensagemDTO2.setId(mensagemDTO1.getId());
        assertThat(mensagemDTO1).isEqualTo(mensagemDTO2);
        mensagemDTO2.setId(2L);
        assertThat(mensagemDTO1).isNotEqualTo(mensagemDTO2);
        mensagemDTO1.setId(null);
        assertThat(mensagemDTO1).isNotEqualTo(mensagemDTO2);
    }
}
