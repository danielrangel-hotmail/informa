package com.objective.informa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class PerfilGrupoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilGrupoDTO.class);
        PerfilGrupoDTO perfilGrupoDTO1 = new PerfilGrupoDTO();
        perfilGrupoDTO1.setId(1L);
        PerfilGrupoDTO perfilGrupoDTO2 = new PerfilGrupoDTO();
        assertThat(perfilGrupoDTO1).isNotEqualTo(perfilGrupoDTO2);
        perfilGrupoDTO2.setId(perfilGrupoDTO1.getId());
        assertThat(perfilGrupoDTO1).isEqualTo(perfilGrupoDTO2);
        perfilGrupoDTO2.setId(2L);
        assertThat(perfilGrupoDTO1).isNotEqualTo(perfilGrupoDTO2);
        perfilGrupoDTO1.setId(null);
        assertThat(perfilGrupoDTO1).isNotEqualTo(perfilGrupoDTO2);
    }
}
