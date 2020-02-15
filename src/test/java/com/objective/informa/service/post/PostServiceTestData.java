package com.objective.informa.service.post;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.objective.informa.domain.Arquivo;
import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.LinkExterno;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.repository.ArquivoRepository;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.repository.LinkExternoRepository;
import com.objective.informa.repository.MensagemRepository;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.security.SecurityFacade;
import com.objective.informa.service.PostReacaoService;
import com.objective.informa.service.mapper.ArquivoMapperImpl;
import com.objective.informa.service.mapper.LinkExternoMapperImpl;
import com.objective.informa.service.mapper.PostMapperImpl;

public class PostServiceTestData {
	
	public static final String CONTEUDO1 = "CONTEUDO 1";
	
	public PostRepository postRepository;
	public GrupoRepository grupoRepository;
	public UserRepository userRepository;
	public MensagemRepository mensagemRepository;
	public ArquivoRepository arquivoRepository;
	public LinkExternoRepository linkExternoRepository;
	public PostPublisher postPublisher;

	public PostReacaoService postReacaoService;
	public PostMapperImpl postMapper;
	public ArquivoMapperImpl arquivoMapperImpl;
	public LinkExternoMapperImpl linkExternoMapperImpl;
	public SecurityFacade securityFacade;
	public PostService postService;
	public Grupo grupo;
	public User user;
	public Post post;

	public PostServiceTestData() {
	}
	
	public void setup() {
    	this.postRepository = Mockito.mock(PostRepository.class);
    	this.grupoRepository = Mockito.mock(GrupoRepository.class);
    	this.userRepository = Mockito.mock(UserRepository.class);
    	this.postPublisher = Mockito.mock(PostPublisher.class);
    	this.mensagemRepository = Mockito.mock(MensagemRepository.class);
    	this.postReacaoService = Mockito.mock(PostReacaoService.class);
    	this.arquivoRepository = Mockito.mock(ArquivoRepository.class);
    	this.linkExternoRepository = Mockito.mock(LinkExternoRepository.class);
    	this.securityFacade = new SecurityFacade(userRepository);
    	this.postMapper= new PostMapperImpl();
    	this.linkExternoMapperImpl = new LinkExternoMapperImpl();
    	this.arquivoMapperImpl = new ArquivoMapperImpl();
    	ReflectionTestUtils.setField(this.postMapper, "grupoRepository", grupoRepository);
    	ReflectionTestUtils.setField(this.postMapper, "userRepository", userRepository);
    	ReflectionTestUtils.setField(this.postMapper, "mensagemRepository", mensagemRepository);
    	ReflectionTestUtils.setField(this.postMapper, "postReacaoService", postReacaoService);
    	ReflectionTestUtils.setField(this.postMapper, "securityFacade", securityFacade);
    	ReflectionTestUtils.setField(this.postMapper, "arquivoMapper", arquivoMapperImpl);
    	ReflectionTestUtils.setField(this.postMapper, "linkExternoMapper", linkExternoMapperImpl);

	}
	
	public void setupPostNovo(String username) {
		grupo = new Grupo();
    	grupo.setId(1L);
    	user = new User();
    	user.setFirstName(username);
    	user.setLogin(username);
    	post = new Post();
    	post.setOficial(false);
    	post.setRemovido(false);
    	post.setId(1L);
    	post.setGrupo(grupo);
    	post.setAutor(user);
    	post.setConteudo(CONTEUDO1);
    	post.setVersao(0L);
    	doReturn(Optional.of(post)).when(postRepository).findById(1L);
    	doReturn(Optional.of(user)).when(userRepository).findOneByLogin(username);
    	doReturn(Optional.of(grupo)).when(grupoRepository).findById(1L);

	}

	public void setupPostNovoComArquivosELinks(String username) {
		this.setupPostNovo(username);
		adicionaArquivoELink();
	}

	public void adicionaArquivoELink() {
		Arquivo arquivo = new Arquivo();
		arquivo.setId(2L);
		post.addArquivos(arquivo);
		LinkExterno link = new LinkExterno();
		link.setId(3L);
		post.addLinksExternos(link);
	}
}