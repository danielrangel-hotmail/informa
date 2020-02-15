package com.objective.informa.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.objective.informa.service.dto.PostDTO;
import com.objective.informa.service.post.PostServiceTestData;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration
public class PostMapperTest {

	private PostServiceTestData data = new PostServiceTestData();

	@Before
    public void setUp() {
    	this.data.setup();
	}	

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(data.postMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(data.postMapper.fromId(null)).isNull();
    }
    
    @Test
    @WithMockUser
    public void testPostNormal() {
    	data.setupPostNovoComArquivosELinks("user");
    	PostDTO postDTO = data.postMapper.toDto(data.post);
    	assertThat(postDTO.getConteudo()).isEqualTo(PostServiceTestData.CONTEUDO1).as("CONTEUDO");
    	assertThat(postDTO.getArquivos().size()).isEqualTo(1).as("ARQUIVOS SIZE");
    	assertThat(postDTO.getLinksExternos().size()).isEqualTo(1).as("LINKS");
    }
    
    @Test
    @WithMockUser
    public void testPostRemovido() {
    	data.setupPostNovoComArquivosELinks("user");
    	data.post.setRemovido(true);
    	PostDTO postDTO = data.postMapper.toDto(data.post);
    	assertThat(postDTO.getConteudo()).isBlank().as("CONTEUDO");
    	assertThat(postDTO.getArquivos().size()).isEqualTo(0).as("ARQUIVOS SIZE");
    	assertThat(postDTO.getLinksExternos().size()).isEqualTo(0).as("LINKS");
    }

}
