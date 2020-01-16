package com.objective.informa.web.rest;

import com.objective.informa.InformaApp;
import com.objective.informa.domain.LinkExterno;
import com.objective.informa.domain.User;
import com.objective.informa.repository.LinkExternoRepository;
import com.objective.informa.service.LinkExternoService;
import com.objective.informa.service.dto.LinkExternoDTO;
import com.objective.informa.service.mapper.LinkExternoMapper;
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

import com.objective.informa.domain.enumeration.LinkTipo;
/**
 * Integration tests for the {@link LinkExternoResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class LinkExternoResourceIT {

    private static final Long DEFAULT_VERSAO = 1L;
    private static final Long UPDATED_VERSAO = 2L;

    private static final ZonedDateTime DEFAULT_CRIACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRIACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ULTIMA_EDICAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ULTIMA_EDICAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LinkTipo DEFAULT_TIPO = LinkTipo.VIDEO;
    private static final LinkTipo UPDATED_TIPO = LinkTipo.EXTERNO;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    @Autowired
    private LinkExternoRepository linkExternoRepository;

    @Autowired
    private LinkExternoMapper linkExternoMapper;

    @Autowired
    private LinkExternoService linkExternoService;

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

    private MockMvc restLinkExternoMockMvc;

    private LinkExterno linkExterno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LinkExternoResource linkExternoResource = new LinkExternoResource(linkExternoService);
        this.restLinkExternoMockMvc = MockMvcBuilders.standaloneSetup(linkExternoResource)
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
    public static LinkExterno createEntity(EntityManager em) {
        LinkExterno linkExterno = new LinkExterno()
            .versao(DEFAULT_VERSAO)
            .criacao(DEFAULT_CRIACAO)
            .ultimaEdicao(DEFAULT_ULTIMA_EDICAO)
            .tipo(DEFAULT_TIPO)
            .link(DEFAULT_LINK);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        linkExterno.setUsuario(user);
        return linkExterno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkExterno createUpdatedEntity(EntityManager em) {
        LinkExterno linkExterno = new LinkExterno()
            .versao(UPDATED_VERSAO)
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .tipo(UPDATED_TIPO)
            .link(UPDATED_LINK);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        linkExterno.setUsuario(user);
        return linkExterno;
    }

    @BeforeEach
    public void initTest() {
        linkExterno = createEntity(em);
    }

    @Test
    @Transactional
    public void createLinkExterno() throws Exception {
        int databaseSizeBeforeCreate = linkExternoRepository.findAll().size();

        // Create the LinkExterno
        LinkExternoDTO linkExternoDTO = linkExternoMapper.toDto(linkExterno);
        restLinkExternoMockMvc.perform(post("/api/link-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linkExternoDTO)))
            .andExpect(status().isCreated());

        // Validate the LinkExterno in the database
        List<LinkExterno> linkExternoList = linkExternoRepository.findAll();
        assertThat(linkExternoList).hasSize(databaseSizeBeforeCreate + 1);
        LinkExterno testLinkExterno = linkExternoList.get(linkExternoList.size() - 1);
        assertThat(testLinkExterno.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testLinkExterno.getCriacao()).isEqualTo(DEFAULT_CRIACAO);
        assertThat(testLinkExterno.getUltimaEdicao()).isEqualTo(DEFAULT_ULTIMA_EDICAO);
        assertThat(testLinkExterno.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testLinkExterno.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    @Transactional
    public void createLinkExternoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = linkExternoRepository.findAll().size();

        // Create the LinkExterno with an existing ID
        linkExterno.setId(1L);
        LinkExternoDTO linkExternoDTO = linkExternoMapper.toDto(linkExterno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkExternoMockMvc.perform(post("/api/link-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linkExternoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LinkExterno in the database
        List<LinkExterno> linkExternoList = linkExternoRepository.findAll();
        assertThat(linkExternoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLinkExternos() throws Exception {
        // Initialize the database
        linkExternoRepository.saveAndFlush(linkExterno);

        // Get all the linkExternoList
        restLinkExternoMockMvc.perform(get("/api/link-externos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linkExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(sameInstant(DEFAULT_CRIACAO))))
            .andExpect(jsonPath("$.[*].ultimaEdicao").value(hasItem(sameInstant(DEFAULT_ULTIMA_EDICAO))))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)));
    }
    
    @Test
    @Transactional
    public void getLinkExterno() throws Exception {
        // Initialize the database
        linkExternoRepository.saveAndFlush(linkExterno);

        // Get the linkExterno
        restLinkExternoMockMvc.perform(get("/api/link-externos/{id}", linkExterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(linkExterno.getId().intValue()))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
            .andExpect(jsonPath("$.criacao").value(sameInstant(DEFAULT_CRIACAO)))
            .andExpect(jsonPath("$.ultimaEdicao").value(sameInstant(DEFAULT_ULTIMA_EDICAO)))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK));
    }

    @Test
    @Transactional
    public void getNonExistingLinkExterno() throws Exception {
        // Get the linkExterno
        restLinkExternoMockMvc.perform(get("/api/link-externos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLinkExterno() throws Exception {
        // Initialize the database
        linkExternoRepository.saveAndFlush(linkExterno);

        int databaseSizeBeforeUpdate = linkExternoRepository.findAll().size();

        // Update the linkExterno
        LinkExterno updatedLinkExterno = linkExternoRepository.findById(linkExterno.getId()).get();
        // Disconnect from session so that the updates on updatedLinkExterno are not directly saved in db
        em.detach(updatedLinkExterno);
        updatedLinkExterno
            .versao(UPDATED_VERSAO)
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .tipo(UPDATED_TIPO)
            .link(UPDATED_LINK);
        LinkExternoDTO linkExternoDTO = linkExternoMapper.toDto(updatedLinkExterno);

        restLinkExternoMockMvc.perform(put("/api/link-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linkExternoDTO)))
            .andExpect(status().isOk());

        // Validate the LinkExterno in the database
        List<LinkExterno> linkExternoList = linkExternoRepository.findAll();
        assertThat(linkExternoList).hasSize(databaseSizeBeforeUpdate);
        LinkExterno testLinkExterno = linkExternoList.get(linkExternoList.size() - 1);
        assertThat(testLinkExterno.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testLinkExterno.getCriacao()).isEqualTo(UPDATED_CRIACAO);
        assertThat(testLinkExterno.getUltimaEdicao()).isEqualTo(UPDATED_ULTIMA_EDICAO);
        assertThat(testLinkExterno.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLinkExterno.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingLinkExterno() throws Exception {
        int databaseSizeBeforeUpdate = linkExternoRepository.findAll().size();

        // Create the LinkExterno
        LinkExternoDTO linkExternoDTO = linkExternoMapper.toDto(linkExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkExternoMockMvc.perform(put("/api/link-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linkExternoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LinkExterno in the database
        List<LinkExterno> linkExternoList = linkExternoRepository.findAll();
        assertThat(linkExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLinkExterno() throws Exception {
        // Initialize the database
        linkExternoRepository.saveAndFlush(linkExterno);

        int databaseSizeBeforeDelete = linkExternoRepository.findAll().size();

        // Delete the linkExterno
        restLinkExternoMockMvc.perform(delete("/api/link-externos/{id}", linkExterno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LinkExterno> linkExternoList = linkExternoRepository.findAll();
        assertThat(linkExternoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
