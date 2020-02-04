package com.objective.informa.web.rest;

import com.objective.informa.InformaApp;
import com.objective.informa.domain.Arquivo;
import com.objective.informa.domain.User;
import com.objective.informa.repository.ArquivoRepository;
import com.objective.informa.service.ArquivoService;
import com.objective.informa.service.dto.ArquivoDTO;
import com.objective.informa.service.mapper.ArquivoMapper;
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
 * Integration tests for the {@link ArquivoResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class ArquivoResourceIT {

    private static final Long DEFAULT_VERSAO = 1L;
    private static final Long UPDATED_VERSAO = 2L;

    private static final ZonedDateTime DEFAULT_CRIACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRIACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ULTIMA_EDICAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ULTIMA_EDICAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COLECAO = "AAAAAAAAAA";
    private static final String UPDATED_COLECAO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_UPLOAD_CONFIRMADO = false;
    private static final Boolean UPDATED_UPLOAD_CONFIRMADO = true;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private ArquivoMapper arquivoMapper;

    @Autowired
    private ArquivoService arquivoService;

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

    private MockMvc restArquivoMockMvc;

    private Arquivo arquivo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArquivoResource arquivoResource = new ArquivoResource(arquivoService, null);
        this.restArquivoMockMvc = MockMvcBuilders.standaloneSetup(arquivoResource)
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
    public static Arquivo createEntity(EntityManager em) {
        Arquivo arquivo = new Arquivo()
            .versao(DEFAULT_VERSAO)
            .criacao(DEFAULT_CRIACAO)
            .ultimaEdicao(DEFAULT_ULTIMA_EDICAO)
            .nome(DEFAULT_NOME)
            .colecao(DEFAULT_COLECAO)
            .tipo(DEFAULT_TIPO)
            .link(DEFAULT_LINK)
            .uploadConfirmado(DEFAULT_UPLOAD_CONFIRMADO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        arquivo.setUsuario(user);
        return arquivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arquivo createUpdatedEntity(EntityManager em) {
        Arquivo arquivo = new Arquivo()
            .versao(UPDATED_VERSAO)
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .nome(UPDATED_NOME)
            .colecao(UPDATED_COLECAO)
            .tipo(UPDATED_TIPO)
            .link(UPDATED_LINK)
            .uploadConfirmado(UPDATED_UPLOAD_CONFIRMADO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        arquivo.setUsuario(user);
        return arquivo;
    }

    @BeforeEach
    public void initTest() {
        arquivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArquivo() throws Exception {
        int databaseSizeBeforeCreate = arquivoRepository.findAll().size();

        // Create the Arquivo
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);
        restArquivoMockMvc.perform(post("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isCreated());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeCreate + 1);
        Arquivo testArquivo = arquivoList.get(arquivoList.size() - 1);
        assertThat(testArquivo.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testArquivo.getCriacao()).isEqualTo(DEFAULT_CRIACAO);
        assertThat(testArquivo.getUltimaEdicao()).isEqualTo(DEFAULT_ULTIMA_EDICAO);
        assertThat(testArquivo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testArquivo.getColecao()).isEqualTo(DEFAULT_COLECAO);
        assertThat(testArquivo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testArquivo.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testArquivo.isUploadConfirmado()).isEqualTo(DEFAULT_UPLOAD_CONFIRMADO);
    }

    @Test
    @Transactional
    public void createArquivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arquivoRepository.findAll().size();

        // Create the Arquivo with an existing ID
        arquivo.setId(1L);
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArquivoMockMvc.perform(post("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArquivos() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList
        restArquivoMockMvc.perform(get("/api/arquivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arquivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(sameInstant(DEFAULT_CRIACAO))))
            .andExpect(jsonPath("$.[*].ultimaEdicao").value(hasItem(sameInstant(DEFAULT_ULTIMA_EDICAO))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].colecao").value(hasItem(DEFAULT_COLECAO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].uploadConfirmado").value(hasItem(DEFAULT_UPLOAD_CONFIRMADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get the arquivo
        restArquivoMockMvc.perform(get("/api/arquivos/{id}", arquivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arquivo.getId().intValue()))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
            .andExpect(jsonPath("$.criacao").value(sameInstant(DEFAULT_CRIACAO)))
            .andExpect(jsonPath("$.ultimaEdicao").value(sameInstant(DEFAULT_ULTIMA_EDICAO)))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.colecao").value(DEFAULT_COLECAO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.uploadConfirmado").value(DEFAULT_UPLOAD_CONFIRMADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingArquivo() throws Exception {
        // Get the arquivo
        restArquivoMockMvc.perform(get("/api/arquivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        int databaseSizeBeforeUpdate = arquivoRepository.findAll().size();

        // Update the arquivo
        Arquivo updatedArquivo = arquivoRepository.findById(arquivo.getId()).get();
        // Disconnect from session so that the updates on updatedArquivo are not directly saved in db
        em.detach(updatedArquivo);
        updatedArquivo
            .versao(UPDATED_VERSAO)
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .nome(UPDATED_NOME)
            .colecao(UPDATED_COLECAO)
            .tipo(UPDATED_TIPO)
            .link(UPDATED_LINK)
            .uploadConfirmado(UPDATED_UPLOAD_CONFIRMADO);
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(updatedArquivo);

        restArquivoMockMvc.perform(put("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isOk());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeUpdate);
        Arquivo testArquivo = arquivoList.get(arquivoList.size() - 1);
        assertThat(testArquivo.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testArquivo.getCriacao()).isEqualTo(UPDATED_CRIACAO);
        assertThat(testArquivo.getUltimaEdicao()).isEqualTo(UPDATED_ULTIMA_EDICAO);
        assertThat(testArquivo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testArquivo.getColecao()).isEqualTo(UPDATED_COLECAO);
        assertThat(testArquivo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testArquivo.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testArquivo.isUploadConfirmado()).isEqualTo(UPDATED_UPLOAD_CONFIRMADO);
    }

    @Test
    @Transactional
    public void updateNonExistingArquivo() throws Exception {
        int databaseSizeBeforeUpdate = arquivoRepository.findAll().size();

        // Create the Arquivo
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArquivoMockMvc.perform(put("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        int databaseSizeBeforeDelete = arquivoRepository.findAll().size();

        // Delete the arquivo
        restArquivoMockMvc.perform(delete("/api/arquivos/{id}", arquivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
