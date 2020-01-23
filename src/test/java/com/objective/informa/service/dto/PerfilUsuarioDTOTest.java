package com.objective.informa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class PerfilUsuarioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilUsuarioDTO.class);
        PerfilUsuarioDTO perfilUsuarioDTO1 = new PerfilUsuarioDTO();
        perfilUsuarioDTO1.setId(1L);
        PerfilUsuarioDTO perfilUsuarioDTO2 = new PerfilUsuarioDTO();
        assertThat(perfilUsuarioDTO1).isNotEqualTo(perfilUsuarioDTO2);
        perfilUsuarioDTO2.setId(perfilUsuarioDTO1.getId());
        assertThat(perfilUsuarioDTO1).isEqualTo(perfilUsuarioDTO2);
        perfilUsuarioDTO2.setId(2L);
        assertThat(perfilUsuarioDTO1).isNotEqualTo(perfilUsuarioDTO2);
        perfilUsuarioDTO1.setId(null);
        assertThat(perfilUsuarioDTO1).isNotEqualTo(perfilUsuarioDTO2);
    }
}
