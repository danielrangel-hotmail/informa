package com.objective.informa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class TopicoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicoDTO.class);
        TopicoDTO topicoDTO1 = new TopicoDTO();
        topicoDTO1.setId(1L);
        TopicoDTO topicoDTO2 = new TopicoDTO();
        assertThat(topicoDTO1).isNotEqualTo(topicoDTO2);
        topicoDTO2.setId(topicoDTO1.getId());
        assertThat(topicoDTO1).isEqualTo(topicoDTO2);
        topicoDTO2.setId(2L);
        assertThat(topicoDTO1).isNotEqualTo(topicoDTO2);
        topicoDTO1.setId(null);
        assertThat(topicoDTO1).isNotEqualTo(topicoDTO2);
    }
}
