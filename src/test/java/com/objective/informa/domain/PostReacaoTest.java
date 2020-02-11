package com.objective.informa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class PostReacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostReacao.class);
        PostReacao postReacao1 = new PostReacao();
        postReacao1.setId(1L);
        PostReacao postReacao2 = new PostReacao();
        postReacao2.setId(postReacao1.getId());
        assertThat(postReacao1).isEqualTo(postReacao2);
        postReacao2.setId(2L);
        assertThat(postReacao1).isNotEqualTo(postReacao2);
        postReacao1.setId(null);
        assertThat(postReacao1).isNotEqualTo(postReacao2);
    }
}
