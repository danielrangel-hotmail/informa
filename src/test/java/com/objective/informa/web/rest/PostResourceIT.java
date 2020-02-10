package com.objective.informa.web.rest;

import static com.objective.informa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

/* TODO Precisa acertar os outros testes, e entender porque diabos a versão se comportou no método getAllPosts()
 */

import com.objective.informa.InformaApp;
import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.dto.PostDTO;
import com.objective.informa.service.dto.SimplePostDTO;
import com.objective.informa.service.post.PostService;
import com.objective.informa.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link PostResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class PostResourceIT {

    private static final Long DEFAULT_VERSAO = 0L;

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPostMockMvc;

    private PostDTO postDTO;

    private User user;

    private Grupo grupo;

    private ZonedDateTime timeBeforeTest;

    @BeforeEach
    public void setup() {
        timeBeforeTest = ZonedDateTime.now();
        MockitoAnnotations.initMocks(this);
        final PostResource postResource = new PostResource(postService);
        this.restPostMockMvc = MockMvcBuilders.standaloneSetup(postResource)
        	.addFilter(new SecurityContextPersistenceFilter())
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
        this.criaGrupoEUser();
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public void criaGrupoEUser() {
        this.user = this.userRepository.findOneByLogin("user").get();
        if (TestUtil.findAll(em, Grupo.class).isEmpty()) {
            this.grupo = GrupoResourceIT.createUpdatedEntity(em);
            em.persist(grupo);
            em.flush();
        } else {
            this.grupo = TestUtil.findAll(em, Grupo.class).get(0);
        }
    }


    @BeforeEach
    public void initTest() {
        this.postDTO = new PostDTO();
        this.postDTO.setConteudo(DEFAULT_CONTEUDO);
        this.postDTO.setAutorId(user.getId());
        this.postDTO.setGrupoId(grupo.getId());
    }

    @Test
    @Transactional
    public void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();
        restPostMockMvc.perform(post("/api/posts")
    		.with(user("user"))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testPost.getCriacao()).isBetween(timeBeforeTest, ZonedDateTime.now());
        assertThat(testPost.getUltimaEdicao()).isBetween(timeBeforeTest, ZonedDateTime.now());
        assertThat(testPost.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testPost.getPublicacao()).isNull();
        assertThat(testPost.getAutor().getLogin()).isEqualTo("user");
    }

    @Test
    @Transactional
    public void createPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post with an existing ID
        this.postDTO.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    @WithMockUser()
    public void getAllPosts() throws Exception {
        // Initialize the database
        final PostDTO postDTOCriado = postService.create(postService.create(this.postDTO));

        // Get all the postList
        restPostMockMvc.perform(get("/api/posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("[]"));

        PostDTO postDTOPublicado = postService.publica(new SimplePostDTO(postDTOCriado.getId(), postDTOCriado.getVersao()));
        restPostMockMvc.perform(get("/api/posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postDTOPublicado.getId().intValue())))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue()+2)))
            .andExpect(jsonPath("$.[*].criacao").value(notNullValue()))
            .andExpect(jsonPath("$.[*].ultimaEdicao").value(notNullValue()))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO)))
            .andExpect(jsonPath("$.[*].publicacao").value(notNullValue()));

    }
}
