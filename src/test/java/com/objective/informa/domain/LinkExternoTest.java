package com.objective.informa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.objective.informa.web.rest.TestUtil;

public class LinkExternoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkExterno.class);
        LinkExterno linkExterno1 = new LinkExterno();
        linkExterno1.setId(1L);
        LinkExterno linkExterno2 = new LinkExterno();
        linkExterno2.setId(linkExterno1.getId());
        assertThat(linkExterno1).isEqualTo(linkExterno2);
        linkExterno2.setId(2L);
        assertThat(linkExterno1).isNotEqualTo(linkExterno2);
        linkExterno1.setId(null);
        assertThat(linkExterno1).isNotEqualTo(linkExterno2);
    }
}
