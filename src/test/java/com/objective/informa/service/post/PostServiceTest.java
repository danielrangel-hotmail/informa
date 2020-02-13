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
import com.objective.informa.service.dto.PostDTO;
import com.objective.informa.service.mapper.PostMapperImpl;
import com.objective.informa.service.mapper.PostReacaoMapper;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration
public class PostServiceTest {

	private static final String CONTEUDO1 = "CONTEUDO 1";
	
	
    @Mock private PostRepository postRepository;
    @Mock private GrupoRepository grupoRepository;
    @Mock private UserRepository userRepository;
    @Mock private PostPublisher postPublisher;
    @Mock private MensagemRepository mensagemRepository;
    @Mock private SecurityFacade securityFacade;
    @Mock private PostReacaoMapper postReacaoMapper;
    private Grupo grupo;
    private User user;

    @InjectMocks PostMapperImpl postMapper;
    PostService postService;
    
    @Test
    @WithMockUser
	public void testCriacao() {
    	grupo = new Grupo();
    	grupo.setId(1L);
    	user = new User();
    	user.setFirstName("user");
    	MockitoAnnotations.initMocks(this);
    	doReturn(Optional.of("user")).when(securityFacade).getCurrentUserLogin();
        doReturn(Optional.of(grupo)).when(grupoRepository).findById(1L);
        doReturn(Optional.of(user)).when(userRepository).findOneByLogin("user");
        doReturn(0L).when(mensagemRepository).countByPostId(2L);
        when(postRepository.save(any(Post.class))).thenAnswer(i -> {
        	Post post = (Post)i.getArgument(0);
        	post.setId(2L);
        	return post;
        });
    	this.postService = new PostService(postRepository, postMapper, userRepository, postPublisher, securityFacade);
		PostDTO postDTO = new PostDTO();
		postDTO.setConteudo(CONTEUDO1);
		postDTO.setGrupoId(1L);
		PostDTO postRetorno = this.postService.create(postDTO);
		assertThat(postRetorno.getConteudo()).isEqualTo(CONTEUDO1);
		assertThat(postRetorno.getGrupoId()).isEqualTo(1L);
		assertThat(postRetorno.getAutorNome()).isEqualTo("user");
		
	}
}
