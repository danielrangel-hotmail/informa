package com.objective.informa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class GrupoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoDTO.class);
        GrupoDTO grupoDTO1 = new GrupoDTO();
        grupoDTO1.setId(1L);
        GrupoDTO grupoDTO2 = new GrupoDTO();
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
        grupoDTO2.setId(grupoDTO1.getId());
        assertThat(grupoDTO1).isEqualTo(grupoDTO2);
        grupoDTO2.setId(2L);
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
        grupoDTO1.setId(null);
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
    }
}
