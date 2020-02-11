package com.objective.informa.web.rest;

import com.objective.informa.InformaApp;
import com.objective.informa.domain.PostReacao;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.Post;
import com.objective.informa.repository.PostReacaoRepository;
import com.objective.informa.service.PostReacaoService;
import com.objective.informa.service.dto.PostReacaoDTO;
import com.objective.informa.service.mapper.PostReacaoMapper;
import com.objective.informa.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.objective.informa.web.rest.TestUtil.sameInstant;
import static com.objective.informa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PostReacaoResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class PostReacaoResourceIT {

    private static final ZonedDateTime DEFAULT_CRIACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRIACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ULTIMA_EDICAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ULTIMA_EDICAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_VERSAO = 1L;
    private static final Long UPDATED_VERSAO = 2L;

    private static final String DEFAULT_REACAO = "AAAAAAAAAA";
    private static final String UPDATED_REACAO = "BBBBBBBBBB";

    @Autowired
    private PostReacaoRepository postReacaoRepository;

    @Autowired
    private PostReacaoMapper postReacaoMapper;

    @Autowired
    private PostReacaoService postReacaoService;

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

    private MockMvc restPostReacaoMockMvc;

    private PostReacao postReacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PostReacaoResource postReacaoResource = new PostReacaoResource(postReacaoService);
        this.restPostReacaoMockMvc = MockMvcBuilders.standaloneSetup(postReacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostReacao createEntity(EntityManager em) {
        PostReacao postReacao = new PostReacao()
            .criacao(DEFAULT_CRIACAO)
            .ultimaEdicao(DEFAULT_ULTIMA_EDICAO)
            .versao(DEFAULT_VERSAO)
            .reacao(DEFAULT_REACAO);
        // Add required entity
        PerfilUsuario perfilUsuario;
        if (TestUtil.findAll(em, PerfilUsuario.class).isEmpty()) {
            perfilUsuario = PerfilUsuarioResourceIT.createEntity(em);
            em.persist(perfilUsuario);
            em.flush();
        } else {
            perfilUsuario = TestUtil.findAll(em, PerfilUsuario.class).get(0);
        }
        postReacao.setPerfil(perfilUsuario);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
        	post = null;
//            post = PostResourceIT.createEntity(em);
//            em.persist(post);
//            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        postReacao.setPost(post);
        return postReacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostReacao createUpdatedEntity(EntityManager em) {
        PostReacao postReacao = new PostReacao()
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .versao(UPDATED_VERSAO)
            .reacao(UPDATED_REACAO);
        // Add required entity
        PerfilUsuario perfilUsuario;
        if (TestUtil.findAll(em, PerfilUsuario.class).isEmpty()) {
            perfilUsuario = PerfilUsuarioResourceIT.createUpdatedEntity(em);
            em.persist(perfilUsuario);
            em.flush();
        } else {
            perfilUsuario = TestUtil.findAll(em, PerfilUsuario.class).get(0);
        }
        postReacao.setPerfil(perfilUsuario);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = null;
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        postReacao.setPost(post);
        return postReacao;
    }

    @BeforeEach
    public void initTest() {
        postReacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostReacao() throws Exception {
        int databaseSizeBeforeCreate = postReacaoRepository.findAll().size();

        // Create the PostReacao
        PostReacaoDTO postReacaoDTO = postReacaoMapper.toDto(postReacao);
        restPostReacaoMockMvc.perform(post("/api/post-reacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postReacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the PostReacao in the database
        List<PostReacao> postReacaoList = postReacaoRepository.findAll();
        assertThat(postReacaoList).hasSize(databaseSizeBeforeCreate + 1);
        PostReacao testPostReacao = postReacaoList.get(postReacaoList.size() - 1);
        assertThat(testPostReacao.getCriacao()).isEqualTo(DEFAULT_CRIACAO);
        assertThat(testPostReacao.getUltimaEdicao()).isEqualTo(DEFAULT_ULTIMA_EDICAO);
        assertThat(testPostReacao.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testPostReacao.getReacao()).isEqualTo(DEFAULT_REACAO);
    }

    @Test
    @Transactional
    public void createPostReacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postReacaoRepository.findAll().size();

        // Create the PostReacao with an existing ID
        postReacao.setId(1L);
        PostReacaoDTO postReacaoDTO = postReacaoMapper.toDto(postReacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostReacaoMockMvc.perform(post("/api/post-reacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postReacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostReacao in the database
        List<PostReacao> postReacaoList = postReacaoRepository.findAll();
        assertThat(postReacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPostReacaos() throws Exception {
        // Initialize the database
        postReacaoRepository.saveAndFlush(postReacao);

        // Get all the postReacaoList
        restPostReacaoMockMvc.perform(get("/api/post-reacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postReacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(sameInstant(DEFAULT_CRIACAO))))
            .andExpect(jsonPath("$.[*].ultimaEdicao").value(hasItem(sameInstant(DEFAULT_ULTIMA_EDICAO))))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
            .andExpect(jsonPath("$.[*].reacao").value(hasItem(DEFAULT_REACAO)));
    }
    
    @Test
    @Transactional
    public void getPostReacao() throws Exception {
        // Initialize the database
        postReacaoRepository.saveAndFlush(postReacao);

        // Get the postReacao
        restPostReacaoMockMvc.perform(get("/api/post-reacaos/{id}", postReacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(postReacao.getId().intValue()))
            .andExpect(jsonPath("$.criacao").value(sameInstant(DEFAULT_CRIACAO)))
            .andExpect(jsonPath("$.ultimaEdicao").value(sameInstant(DEFAULT_ULTIMA_EDICAO)))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
            .andExpect(jsonPath("$.reacao").value(DEFAULT_REACAO));
    }

    @Test
    @Transactional
    public void getNonExistingPostReacao() throws Exception {
        // Get the postReacao
        restPostReacaoMockMvc.perform(get("/api/post-reacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostReacao() throws Exception {
        // Initialize the database
        postReacaoRepository.saveAndFlush(postReacao);

        int databaseSizeBeforeUpdate = postReacaoRepository.findAll().size();

        // Update the postReacao
        PostReacao updatedPostReacao = postReacaoRepository.findById(postReacao.getId()).get();
        // Disconnect from session so that the updates on updatedPostReacao are not directly saved in db
        em.detach(updatedPostReacao);
        updatedPostReacao
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .versao(UPDATED_VERSAO)
            .reacao(UPDATED_REACAO);
        PostReacaoDTO postReacaoDTO = postReacaoMapper.toDto(updatedPostReacao);

        restPostReacaoMockMvc.perform(put("/api/post-reacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postReacaoDTO)))
            .andExpect(status().isOk());

        // Validate the PostReacao in the database
        List<PostReacao> postReacaoList = postReacaoRepository.findAll();
        assertThat(postReacaoList).hasSize(databaseSizeBeforeUpdate);
        PostReacao testPostReacao = postReacaoList.get(postReacaoList.size() - 1);
        assertThat(testPostReacao.getCriacao()).isEqualTo(UPDATED_CRIACAO);
        assertThat(testPostReacao.getUltimaEdicao()).isEqualTo(UPDATED_ULTIMA_EDICAO);
        assertThat(testPostReacao.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testPostReacao.getReacao()).isEqualTo(UPDATED_REACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingPostReacao() throws Exception {
        int databaseSizeBeforeUpdate = postReacaoRepository.findAll().size();

        // Create the PostReacao
        PostReacaoDTO postReacaoDTO = postReacaoMapper.toDto(postReacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostReacaoMockMvc.perform(put("/api/post-reacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postReacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostReacao in the database
        List<PostReacao> postReacaoList = postReacaoRepository.findAll();
        assertThat(postReacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostReacao() throws Exception {
        // Initialize the database
        postReacaoRepository.saveAndFlush(postReacao);

        int databaseSizeBeforeDelete = postReacaoRepository.findAll().size();

        // Delete the postReacao
        restPostReacaoMockMvc.perform(delete("/api/post-reacaos/{id}", postReacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostReacao> postReacaoList = postReacaoRepository.findAll();
        assertThat(postReacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
