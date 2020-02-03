package com.objective.informa.web.rest;

import com.objective.informa.InformaApp;
import com.objective.informa.domain.Topico;
import com.objective.informa.domain.User;
import com.objective.informa.repository.TopicoRepository;
import com.objective.informa.service.TopicoService;
import com.objective.informa.service.dto.TopicoDTO;
import com.objective.informa.service.mapper.TopicoMapper;
import com.objective.informa.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.objective.informa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TopicoResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class TopicoResourceIT {

    private static final Long DEFAULT_VERSAO = 1L;
    private static final Long UPDATED_VERSAO = 2L;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoMapper topicoMapper;

    @Autowired
    private TopicoService topicoService;

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

    private MockMvc restTopicoMockMvc;

    private Topico topico;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TopicoResource topicoResource = new TopicoResource(topicoService);
        this.restTopicoMockMvc = MockMvcBuilders.standaloneSetup(topicoResource)
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
    public static Topico createEntity(EntityManager em) {
        Topico topico = new Topico()
            .versao(DEFAULT_VERSAO)
            .nome(DEFAULT_NOME);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        topico.setAutor(user);
        return topico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topico createUpdatedEntity(EntityManager em) {
        Topico topico = new Topico()
            .versao(UPDATED_VERSAO)
            .nome(UPDATED_NOME);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        topico.setAutor(user);
        return topico;
    }

    @BeforeEach
    public void initTest() {
        topico = createEntity(em);
    }

    @Test
    @Transactional
    @WithMockUser()
    public void createTopico() throws Exception {
        int databaseSizeBeforeCreate = topicoRepository.findAll().size();

        // Create the Topico
        TopicoDTO topicoDTO = topicoMapper.toDto(topico);
        restTopicoMockMvc.perform(post("/api/topicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Topico in the database
        List<Topico> topicoList = topicoRepository.findAll();
        assertThat(topicoList).hasSize(databaseSizeBeforeCreate + 1);
        Topico testTopico = topicoList.get(topicoList.size() - 1);
        assertThat(testTopico.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testTopico.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTopico.getAutor().getLogin()).isEqualTo("user");

    }

    @Test
    @Transactional
    public void createTopicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicoRepository.findAll().size();

        // Create the Topico with an existing ID
        topico.setId(1L);
        TopicoDTO topicoDTO = topicoMapper.toDto(topico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicoMockMvc.perform(post("/api/topicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Topico in the database
        List<Topico> topicoList = topicoRepository.findAll();
        assertThat(topicoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTopicos() throws Exception {
        // Initialize the database
        topicoRepository.saveAndFlush(topico);

        // Get all the topicoList
        restTopicoMockMvc.perform(get("/api/topicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topico.getId().intValue())))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTopico() throws Exception {
        // Initialize the database
        topicoRepository.saveAndFlush(topico);

        // Get the topico
        restTopicoMockMvc.perform(get("/api/topicos/{id}", topico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(topico.getId().intValue()))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingTopico() throws Exception {
        // Get the topico
        restTopicoMockMvc.perform(get("/api/topicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithMockUser()
    public void updateTopico() throws Exception {
        // Initialize the database
        topicoRepository.saveAndFlush(topico);

        int databaseSizeBeforeUpdate = topicoRepository.findAll().size();

        // Update the topico
        Topico updatedTopico = topicoRepository.findById(topico.getId()).get();
        // Disconnect from session so that the updates on updatedTopico are not directly saved in db
        em.detach(updatedTopico);
        updatedTopico
            .versao(UPDATED_VERSAO)
            .nome(UPDATED_NOME);
        TopicoDTO topicoDTO = topicoMapper.toDto(updatedTopico);

        restTopicoMockMvc.perform(put("/api/topicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicoDTO)))
            .andExpect(status().isOk());

        // Validate the Topico in the database
        List<Topico> topicoList = topicoRepository.findAll();
        assertThat(topicoList).hasSize(databaseSizeBeforeUpdate);
        Topico testTopico = topicoList.get(topicoList.size() - 1);
        assertThat(testTopico.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testTopico.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTopico() throws Exception {
        int databaseSizeBeforeUpdate = topicoRepository.findAll().size();

        // Create the Topico
        TopicoDTO topicoDTO = topicoMapper.toDto(topico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicoMockMvc.perform(put("/api/topicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Topico in the database
        List<Topico> topicoList = topicoRepository.findAll();
        assertThat(topicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopico() throws Exception {
        // Initialize the database
        topicoRepository.saveAndFlush(topico);

        int databaseSizeBeforeDelete = topicoRepository.findAll().size();

        // Delete the topico
        restTopicoMockMvc.perform(delete("/api/topicos/{id}", topico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Topico> topicoList = topicoRepository.findAll();
        assertThat(topicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
