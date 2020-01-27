package com.objective.informa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class PerfilGrupoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilGrupo.class);
        PerfilGrupo perfilGrupo1 = new PerfilGrupo();
        perfilGrupo1.setId(1L);
        PerfilGrupo perfilGrupo2 = new PerfilGrupo();
        perfilGrupo2.setId(perfilGrupo1.getId());
        assertThat(perfilGrupo1).isEqualTo(perfilGrupo2);
        perfilGrupo2.setId(2L);
        assertThat(perfilGrupo1).isNotEqualTo(perfilGrupo2);
        perfilGrupo1.setId(null);
        assertThat(perfilGrupo1).isNotEqualTo(perfilGrupo2);
    }
}
