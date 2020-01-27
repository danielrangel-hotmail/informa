package com.objective.informa.web.rest;

import com.objective.informa.InformaApp;
import com.objective.informa.domain.PerfilGrupo;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.Grupo;
import com.objective.informa.repository.PerfilGrupoRepository;
import com.objective.informa.service.PerfilGrupoService;
import com.objective.informa.service.dto.PerfilGrupoDTO;
import com.objective.informa.service.mapper.PerfilGrupoMapper;
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
 * Integration tests for the {@link PerfilGrupoResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class PerfilGrupoResourceIT {

    private static final ZonedDateTime DEFAULT_CRIACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRIACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ULTIMA_EDICAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ULTIMA_EDICAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_VERSAO = 1L;
    private static final Long UPDATED_VERSAO = 2L;

    private static final Boolean DEFAULT_FAVORITO = false;
    private static final Boolean UPDATED_FAVORITO = true;

    private static final Boolean DEFAULT_NOTIFICA = false;
    private static final Boolean UPDATED_NOTIFICA = true;

    @Autowired
    private PerfilGrupoRepository perfilGrupoRepository;

    @Autowired
    private PerfilGrupoMapper perfilGrupoMapper;

    @Autowired
    private PerfilGrupoService perfilGrupoService;

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

    private MockMvc restPerfilGrupoMockMvc;

    private PerfilGrupo perfilGrupo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerfilGrupoResource perfilGrupoResource = new PerfilGrupoResource(perfilGrupoService);
        this.restPerfilGrupoMockMvc = MockMvcBuilders.standaloneSetup(perfilGrupoResource)
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
    public static PerfilGrupo createEntity(EntityManager em) {
        PerfilGrupo perfilGrupo = new PerfilGrupo()
            .criacao(DEFAULT_CRIACAO)
            .ultimaEdicao(DEFAULT_ULTIMA_EDICAO)
            .versao(DEFAULT_VERSAO)
            .favorito(DEFAULT_FAVORITO)
            .notifica(DEFAULT_NOTIFICA);
        // Add required entity
        PerfilUsuario perfilUsuario;
        if (TestUtil.findAll(em, PerfilUsuario.class).isEmpty()) {
            perfilUsuario = PerfilUsuarioResourceIT.createEntity(em);
            em.persist(perfilUsuario);
            em.flush();
        } else {
            perfilUsuario = TestUtil.findAll(em, PerfilUsuario.class).get(0);
        }
        perfilGrupo.setPerfil(perfilUsuario);
        // Add required entity
        Grupo grupo;
        if (TestUtil.findAll(em, Grupo.class).isEmpty()) {
            grupo = GrupoResourceIT.createEntity(em);
            em.persist(grupo);
            em.flush();
        } else {
            grupo = TestUtil.findAll(em, Grupo.class).get(0);
        }
        perfilGrupo.setGrupo(grupo);
        return perfilGrupo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilGrupo createUpdatedEntity(EntityManager em) {
        PerfilGrupo perfilGrupo = new PerfilGrupo()
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .versao(UPDATED_VERSAO)
            .favorito(UPDATED_FAVORITO)
            .notifica(UPDATED_NOTIFICA);
        // Add required entity
        PerfilUsuario perfilUsuario;
        if (TestUtil.findAll(em, PerfilUsuario.class).isEmpty()) {
            perfilUsuario = PerfilUsuarioResourceIT.createUpdatedEntity(em);
            em.persist(perfilUsuario);
            em.flush();
        } else {
            perfilUsuario = TestUtil.findAll(em, PerfilUsuario.class).get(0);
        }
        perfilGrupo.setPerfil(perfilUsuario);
        // Add required entity
        Grupo grupo;
        if (TestUtil.findAll(em, Grupo.class).isEmpty()) {
            grupo = GrupoResourceIT.createUpdatedEntity(em);
            em.persist(grupo);
            em.flush();
        } else {
            grupo = TestUtil.findAll(em, Grupo.class).get(0);
        }
        perfilGrupo.setGrupo(grupo);
        return perfilGrupo;
    }

    @BeforeEach
    public void initTest() {
        perfilGrupo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfilGrupo() throws Exception {
        int databaseSizeBeforeCreate = perfilGrupoRepository.findAll().size();

        // Create the PerfilGrupo
        PerfilGrupoDTO perfilGrupoDTO = perfilGrupoMapper.toDto(perfilGrupo);
        restPerfilGrupoMockMvc.perform(post("/api/perfil-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilGrupoDTO)))
            .andExpect(status().isCreated());

        // Validate the PerfilGrupo in the database
        List<PerfilGrupo> perfilGrupoList = perfilGrupoRepository.findAll();
        assertThat(perfilGrupoList).hasSize(databaseSizeBeforeCreate + 1);
        PerfilGrupo testPerfilGrupo = perfilGrupoList.get(perfilGrupoList.size() - 1);
        assertThat(testPerfilGrupo.getCriacao()).isEqualTo(DEFAULT_CRIACAO);
        assertThat(testPerfilGrupo.getUltimaEdicao()).isEqualTo(DEFAULT_ULTIMA_EDICAO);
        assertThat(testPerfilGrupo.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testPerfilGrupo.isFavorito()).isEqualTo(DEFAULT_FAVORITO);
        assertThat(testPerfilGrupo.isNotifica()).isEqualTo(DEFAULT_NOTIFICA);
    }

    @Test
    @Transactional
    public void createPerfilGrupoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfilGrupoRepository.findAll().size();

        // Create the PerfilGrupo with an existing ID
        perfilGrupo.setId(1L);
        PerfilGrupoDTO perfilGrupoDTO = perfilGrupoMapper.toDto(perfilGrupo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilGrupoMockMvc.perform(post("/api/perfil-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilGrupoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilGrupo in the database
        List<PerfilGrupo> perfilGrupoList = perfilGrupoRepository.findAll();
        assertThat(perfilGrupoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerfilGrupos() throws Exception {
        // Initialize the database
        perfilGrupoRepository.saveAndFlush(perfilGrupo);

        // Get all the perfilGrupoList
        restPerfilGrupoMockMvc.perform(get("/api/perfil-grupos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilGrupo.getId().intValue())))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(sameInstant(DEFAULT_CRIACAO))))
            .andExpect(jsonPath("$.[*].ultimaEdicao").value(hasItem(sameInstant(DEFAULT_ULTIMA_EDICAO))))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
            .andExpect(jsonPath("$.[*].favorito").value(hasItem(DEFAULT_FAVORITO.booleanValue())))
            .andExpect(jsonPath("$.[*].notifica").value(hasItem(DEFAULT_NOTIFICA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPerfilGrupo() throws Exception {
        // Initialize the database
        perfilGrupoRepository.saveAndFlush(perfilGrupo);

        // Get the perfilGrupo
        restPerfilGrupoMockMvc.perform(get("/api/perfil-grupos/{id}", perfilGrupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perfilGrupo.getId().intValue()))
            .andExpect(jsonPath("$.criacao").value(sameInstant(DEFAULT_CRIACAO)))
            .andExpect(jsonPath("$.ultimaEdicao").value(sameInstant(DEFAULT_ULTIMA_EDICAO)))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
            .andExpect(jsonPath("$.favorito").value(DEFAULT_FAVORITO.booleanValue()))
            .andExpect(jsonPath("$.notifica").value(DEFAULT_NOTIFICA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPerfilGrupo() throws Exception {
        // Get the perfilGrupo
        restPerfilGrupoMockMvc.perform(get("/api/perfil-grupos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfilGrupo() throws Exception {
        // Initialize the database
        perfilGrupoRepository.saveAndFlush(perfilGrupo);

        int databaseSizeBeforeUpdate = perfilGrupoRepository.findAll().size();

        // Update the perfilGrupo
        PerfilGrupo updatedPerfilGrupo = perfilGrupoRepository.findById(perfilGrupo.getId()).get();
        // Disconnect from session so that the updates on updatedPerfilGrupo are not directly saved in db
        em.detach(updatedPerfilGrupo);
        updatedPerfilGrupo
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .versao(UPDATED_VERSAO)
            .favorito(UPDATED_FAVORITO)
            .notifica(UPDATED_NOTIFICA);
        PerfilGrupoDTO perfilGrupoDTO = perfilGrupoMapper.toDto(updatedPerfilGrupo);

        restPerfilGrupoMockMvc.perform(put("/api/perfil-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilGrupoDTO)))
            .andExpect(status().isOk());

        // Validate the PerfilGrupo in the database
        List<PerfilGrupo> perfilGrupoList = perfilGrupoRepository.findAll();
        assertThat(perfilGrupoList).hasSize(databaseSizeBeforeUpdate);
        PerfilGrupo testPerfilGrupo = perfilGrupoList.get(perfilGrupoList.size() - 1);
        assertThat(testPerfilGrupo.getCriacao()).isEqualTo(UPDATED_CRIACAO);
        assertThat(testPerfilGrupo.getUltimaEdicao()).isEqualTo(UPDATED_ULTIMA_EDICAO);
        assertThat(testPerfilGrupo.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testPerfilGrupo.isFavorito()).isEqualTo(UPDATED_FAVORITO);
        assertThat(testPerfilGrupo.isNotifica()).isEqualTo(UPDATED_NOTIFICA);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfilGrupo() throws Exception {
        int databaseSizeBeforeUpdate = perfilGrupoRepository.findAll().size();

        // Create the PerfilGrupo
        PerfilGrupoDTO perfilGrupoDTO = perfilGrupoMapper.toDto(perfilGrupo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilGrupoMockMvc.perform(put("/api/perfil-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilGrupoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilGrupo in the database
        List<PerfilGrupo> perfilGrupoList = perfilGrupoRepository.findAll();
        assertThat(perfilGrupoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfilGrupo() throws Exception {
        // Initialize the database
        perfilGrupoRepository.saveAndFlush(perfilGrupo);

        int databaseSizeBeforeDelete = perfilGrupoRepository.findAll().size();

        // Delete the perfilGrupo
        restPerfilGrupoMockMvc.perform(delete("/api/perfil-grupos/{id}", perfilGrupo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerfilGrupo> perfilGrupoList = perfilGrupoRepository.findAll();
        assertThat(perfilGrupoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
