package com.objective.informa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class PostReacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostReacaoDTO.class);
        PostReacaoDTO postReacaoDTO1 = new PostReacaoDTO();
        postReacaoDTO1.setId(1L);
        PostReacaoDTO postReacaoDTO2 = new PostReacaoDTO();
        assertThat(postReacaoDTO1).isNotEqualTo(postReacaoDTO2);
        postReacaoDTO2.setId(postReacaoDTO1.getId());
        assertThat(postReacaoDTO1).isEqualTo(postReacaoDTO2);
        postReacaoDTO2.setId(2L);
        assertThat(postReacaoDTO1).isNotEqualTo(postReacaoDTO2);
        postReacaoDTO1.setId(null);
        assertThat(postReacaoDTO1).isNotEqualTo(postReacaoDTO2);
    }
}
