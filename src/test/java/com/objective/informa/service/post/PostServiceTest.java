package com.objective.informa.service.post;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.repository.MensagemRepository;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.security.SecurityFacade;
import com.objective.informa.service.PostReacaoService;
import com.objective.informa.service.dto.PostDTO;
import com.objective.informa.service.mapper.PostMapperImpl;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration
public class PostServiceTest {

	private static final String CONTEUDO1 = "CONTEUDO 1";
	private static final String CONTEUDO2 = "CONTEUDO 2";
	
	
    @Mock private PostRepository postRepository;
    @Mock private GrupoRepository grupoRepository;
    @Mock private UserRepository userRepository;
    @Mock private PostPublisher postPublisher;
    @Mock private MensagemRepository mensagemRepository;
    @Mock private PostReacaoService postReacaoService;
    private Grupo grupo;
    private User user;
    private Post post;

    @Spy
    private SecurityFacade securityFacade;
    
    @InjectMocks PostMapperImpl postMapper;
    PostService postService;
    
    @Test
    @WithMockUser
	public void testCriacao() {
    	prepareCriacaoDePost("user");
		PostDTO postDTO = new PostDTO();
		postDTO.setConteudo(CONTEUDO1);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(false);
		PostDTO postRetorno = this.postService.create(postDTO);
		assertPostCriado(postRetorno, "user");
	}

	private void assertPostCriado(PostDTO postRetorno, String user) {
		assertThat(postRetorno.getConteudo()).isEqualTo(CONTEUDO1);
		assertThat(postRetorno.getGrupoId()).isEqualTo(1L);
		assertThat(postRetorno.getAutorNome()).isEqualTo(user);
	}

	private void prepareCriacaoDePost(String username) {
		grupo = new Grupo();
    	grupo.setId(1L);
    	user = new User();
    	user.setFirstName(username);
    	MockitoAnnotations.initMocks(this);
    	doReturn(Optional.of(username)).when(securityFacade).getCurrentUserLogin();
        doReturn(Optional.of(grupo)).when(grupoRepository).findById(1L);
        doReturn(Optional.of(user)).when(userRepository).findOneByLogin(username);
        doReturn(0L).when(mensagemRepository).countByPostId(2L);
        when(postRepository.save(any(Post.class))).thenAnswer(i -> {
        	Post post = (Post)i.getArgument(0);
        	post.setId(2L);
        	return post;
        });
    	this.postService = new PostService(postRepository, postMapper, userRepository, postPublisher, securityFacade);
	}
    
    @Test(expected = AccessDeniedException.class)
    @WithMockUser
	public void testCriacaoOficialComUser() {
    	prepareCriacaoDePost("user");
		PostDTO postDTO = new PostDTO();
		postDTO.setConteudo(CONTEUDO1);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(true);
		this.postService.create(postDTO);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN", "USER"})
	public void testCriacaoOficialComAdmins() {
    	prepareCriacaoDePost("admin");
		PostDTO postDTO = new PostDTO();
		postDTO.setConteudo(CONTEUDO1);
		postDTO.setGrupoId(1L);
		postDTO.setOficial(true);
		PostDTO postRetorno = this.postService.create(postDTO);
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
		PostDTO postRetorno = this.postService.create(postDTO);
		assertPostCriado(postRetorno, "gestor");
		
    }

	private void assertPostAlterado(PostDTO postRetorno) {
		assertThat(postRetorno.getConteudo()).isEqualTo(CONTEUDO2);
	}

	private void prepareUpdateDePost(String username) {
		grupo = new Grupo();
    	grupo.setId(1L);
    	user = new User();
    	user.setFirstName(username);
    	user.setLogin(username);
    	MockitoAnnotations.initMocks(this);
    	post = new Post();
    	post.setId(1L);
    	post.setGrupo(grupo);
    	post.setAutor(user);
    	post.setConteudo(CONTEUDO1);
    	post.setVersao(0L);
    	doReturn(Optional.of(post)).when(postRepository).findById(1L);
    	doReturn(Optional.of(grupo)).when(grupoRepository).findById(1L);
//    	doReturn(Optional.of(username)).when(securityFacade).getCurrentUserLogin();
//        doReturn(Optional.of(grupo)).when(grupoRepository).findById(1L);
//        doReturn(Optional.of(user)).when(userRepository).findOneByLogin(username);
        doReturn(0L).when(mensagemRepository).countByPostId(2L);
        when(postRepository.save(any(Post.class))).thenAnswer(i -> {
        	Post post = (Post)i.getArgument(0);
        	post.setId(2L);
        	return post;
        });
    	this.postService = new PostService(postRepository, postMapper, userRepository, postPublisher, securityFacade);
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
		PostDTO postRetorno = this.postService.update(postDTO);
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
		PostDTO postRetorno = this.postService.update(postDTO);
		assertPostAlterado(postRetorno);
	}

}
