package com.objective.informa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class PerfilUsuarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilUsuario.class);
        PerfilUsuario perfilUsuario1 = new PerfilUsuario();
        perfilUsuario1.setId(1L);
        PerfilUsuario perfilUsuario2 = new PerfilUsuario();
        perfilUsuario2.setId(perfilUsuario1.getId());
        assertThat(perfilUsuario1).isEqualTo(perfilUsuario2);
        perfilUsuario2.setId(2L);
        assertThat(perfilUsuario1).isNotEqualTo(perfilUsuario2);
        perfilUsuario1.setId(null);
        assertThat(perfilUsuario1).isNotEqualTo(perfilUsuario2);
    }
}
