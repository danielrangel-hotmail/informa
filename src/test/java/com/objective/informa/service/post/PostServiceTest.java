package com.objective.informa.service.post;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.service.dto.PostDTO;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration
public class PostServiceTest {

	private static final String CONTEUDO1 = "CONTEUDO 1";
	private static final String CONTEUDO2 = "CONTEUDO 2";
	
	
    private PostServiceTestData data = new PostServiceTestData();


	@Before
    public void setUp() {
    	this.data.setup();
        this.data.postService = new PostService(data.postRepository, data.postMapper, data.postPublisher, data.securityFacade, data.linkExternoRepository, data.arquivoRepository);
    }
    
    @Test
    @WithMockUser
	public void testCriacao() {
    	prepareCriacaoDePost("user");
		PostDTO postDTO = new PostDTO();
		postDTO.setConteudo(CONTEUDO1);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(false);
		PostDTO postRetorno = this.data.postService.create(postDTO);
		assertPostCriado(postRetorno, "user");
	}

	private void assertPostCriado(PostDTO postRetorno, String user) {
		assertThat(postRetorno.getConteudo()).isEqualTo(CONTEUDO1);
		assertThat(postRetorno.getGrupoId()).isEqualTo(1L);
		assertThat(postRetorno.getAutorNome()).isEqualTo(user + " null");
	}

	private void prepareCriacaoDePost(String username) {
		data.grupo = new Grupo();
    	data.grupo.setId(1L);
    	data.user = new User();
    	data.user.setFirstName(username);
    	MockitoAnnotations.initMocks(this);
        doReturn(Optional.of(data.grupo)).when(data.grupoRepository).findById(1L);
        doReturn(Optional.of(data.user)).when(data.userRepository).findOneByLogin(username);
        doReturn(0L).when(data.mensagemRepository).countByPostId(2L);
        when(data.postRepository.save(any(Post.class))).thenAnswer(i -> {
        	Post post = (Post)i.getArgument(0);
        	post.setId(2L);
        	return post;
        });
	}
    
    @Test(expected = AccessDeniedException.class)
    @WithMockUser
	public void testCriacaoOficialComUser() {
    	prepareCriacaoDePost("user");
		PostDTO postDTO = new PostDTO();
		postDTO.setConteudo(CONTEUDO1);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(true);
		this.data.postService.create(postDTO);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN", "USER"})
	public void testCriacaoOficialComAdmins() {
    	prepareCriacaoDePost("admin");
		PostDTO postDTO = new PostDTO();
		postDTO.setConteudo(CONTEUDO1);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(true);
		PostDTO postRetorno = this.data.postService.create(postDTO);
		assertPostCriado(postRetorno, "admin");
    }

    @Test
    @WithMockUser(username="gestor", roles={"GESTOR", "USER"})
	public void testCriacaoOficialComGestor() {
    	prepareCriacaoDePost("gestor");
		PostDTO postDTO = new PostDTO();
		postDTO.setConteudo(CONTEUDO1);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(true);
		PostDTO postRetorno = this.data.postService.create(postDTO);
		assertPostCriado(postRetorno, "gestor");
		
    }

	private void assertPostAlterado(PostDTO postRetorno) {
		assertThat(data.post.getConteudo()).isEqualTo(CONTEUDO2);
	}

	private void prepareUpdateDePost(String username) {
		data.setupPostNovo(username);
        doReturn(0L).when(data.mensagemRepository).countByPostId(2L);
        when(data.postRepository.save(any(Post.class))).thenAnswer(i -> {
        	Post post = (Post)i.getArgument(0);
        	post.setId(2L);
        	return post;
        });
	}

    @Test
    @WithMockUser
	public void testUpdate() throws PostUpdateNullException, PostNonAuthorizedException {
    	prepareUpdateDePost("user");
		PostDTO postDTO = new PostDTO();
		postDTO.setId(1L);
		postDTO.setVersao(0L);
		postDTO.setConteudo(CONTEUDO2);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(false);
		PostDTO postRetorno = this.data.postService.update(postDTO);
		assertPostAlterado(postRetorno);
	}

    @Test(expected = AccessDeniedException.class)
    @WithMockUser
	public void testUpdateOficialComUser() throws PostUpdateNullException, PostNonAuthorizedException {
    	testeUpdateOficial("user");
	}

    @Test
    @WithMockUser(username="admin", roles={"ADMIN", "USER"})
	public void testUpdateOficialComAdmin() throws PostUpdateNullException, PostNonAuthorizedException {
    	testeUpdateOficial("admin");
	}

    @Test
    @WithMockUser(username="gestor", roles={"GESTOR", "USER"})
	public void testUpdateOficialComGestor() throws PostUpdateNullException, PostNonAuthorizedException {
    	testeUpdateOficial("gestor");
	}

	private void testeUpdateOficial(String username) throws PostUpdateNullException, PostNonAuthorizedException {
		prepareUpdateDePost(username);
		PostDTO postDTO = new PostDTO();
		postDTO.setId(1L);
		postDTO.setVersao(0L);
		postDTO.setConteudo(CONTEUDO2);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(true);
		PostDTO postRetorno = this.data.postService.update(postDTO);
		assertPostAlterado(postRetorno);
	}

	
	private void prepareExclusaoDePost(String username) {
		data.setupPostNovo(username);
	}

    @Test
    @WithMockUser
	public void testExclusao() {
    	prepareExclusaoDePost("user");
    	this.data.postService.delete(1L);
    	verify(data.postRepository).deleteById(1L);
    	
    }

    @Test
    @WithMockUser
	public void testExclusaoComArquivoELinkExterno() {
    	prepareExclusaoDePost("user");
    	this.data.adicionaArquivoELink();
    	this.data.postService.delete(1L);
    	verify(data.postRepository).deleteById(1L);
    	verify(data.arquivoRepository).deleteById(2L);
    	verify(data.linkExternoRepository).deleteById(3L);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "normal")
	public void testExclusaoOutroUser() {
    	prepareExclusaoDePost("user");
    	this.data.postService.delete(1L);
    	verify(data.postRepository).deleteById(1L);
    	
    }

    @Test
    @WithMockUser
	public void testExclusaoAposPublicacao() {
    	prepareExclusaoDePost("user");
    	data.post.setPublicacao(ZonedDateTime.now());
    	this.data.postService.delete(1L);
    	verify(data.postRepository, never()).deleteById(1L);
    	assertThat(data.post.getRemovido()).isTrue().as("post precisa estar removido");
    	assertThat(data.post.getMomentoRemocao()).isNotNull().as("post precisa ter momento de remocao");
    	assertThat(data.post.getRemovedor()).isEqualTo(data.user).as("post precisa ter removedor");
    }

}
