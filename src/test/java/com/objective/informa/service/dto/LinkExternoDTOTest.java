package com.objective.informa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class LinkExternoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkExternoDTO.class);
        LinkExternoDTO linkExternoDTO1 = new LinkExternoDTO();
        linkExternoDTO1.setId(1L);
        LinkExternoDTO linkExternoDTO2 = new LinkExternoDTO();
        assertThat(linkExternoDTO1).isNotEqualTo(linkExternoDTO2);
        linkExternoDTO2.setId(linkExternoDTO1.getId());
        assertThat(linkExternoDTO1).isEqualTo(linkExternoDTO2);
        linkExternoDTO2.setId(2L);
        assertThat(linkExternoDTO1).isNotEqualTo(linkExternoDTO2);
        linkExternoDTO1.setId(null);
        assertThat(linkExternoDTO1).isNotEqualTo(linkExternoDTO2);
    }
}
