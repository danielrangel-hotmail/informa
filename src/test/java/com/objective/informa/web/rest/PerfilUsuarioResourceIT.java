package com.objective.informa.web.rest;

import com.objective.informa.InformaApp;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.User;
import com.objective.informa.repository.PerfilUsuarioRepository;
import com.objective.informa.service.PerfilUsuarioService;
import com.objective.informa.service.dto.PerfilUsuarioDTO;
import com.objective.informa.service.mapper.PerfilUsuarioMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
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
 * Integration tests for the {@link PerfilUsuarioResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class PerfilUsuarioResourceIT {

    private static final ZonedDateTime DEFAULT_CRIACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRIACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ULTIMA_EDICAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ULTIMA_EDICAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_VERSAO = 1L;
    private static final Long UPDATED_VERSAO = 2L;

    private static final LocalDate DEFAULT_ENTRADA_NA_EMPRESA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENTRADA_NA_EMPRESA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SAIDA_DA_EMPRESA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SAIDA_DA_EMPRESA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SKYPE = "AAAAAAAAAA";
    private static final String UPDATED_SKYPE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_AVATAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AVATAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_CONTENT_TYPE = "image/png";

    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    private PerfilUsuarioMapper perfilUsuarioMapper;

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

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

    private MockMvc restPerfilUsuarioMockMvc;

    private PerfilUsuario perfilUsuario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerfilUsuarioResource perfilUsuarioResource = new PerfilUsuarioResource(perfilUsuarioService);
        this.restPerfilUsuarioMockMvc = MockMvcBuilders.standaloneSetup(perfilUsuarioResource)
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
    public static PerfilUsuario createEntity(EntityManager em) {
        PerfilUsuario perfilUsuario = new PerfilUsuario()
            .criacao(DEFAULT_CRIACAO)
            .ultimaEdicao(DEFAULT_ULTIMA_EDICAO)
            .versao(DEFAULT_VERSAO)
            .entradaNaEmpresa(DEFAULT_ENTRADA_NA_EMPRESA)
            .saidaDaEmpresa(DEFAULT_SAIDA_DA_EMPRESA)
            .nascimento(DEFAULT_NASCIMENTO)
            .skype(DEFAULT_SKYPE)
            .avatar(DEFAULT_AVATAR)
            .avatarContentType(DEFAULT_AVATAR_CONTENT_TYPE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        perfilUsuario.setUsuario(user);
        return perfilUsuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilUsuario createUpdatedEntity(EntityManager em) {
        PerfilUsuario perfilUsuario = new PerfilUsuario()
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .versao(UPDATED_VERSAO)
            .entradaNaEmpresa(UPDATED_ENTRADA_NA_EMPRESA)
            .saidaDaEmpresa(UPDATED_SAIDA_DA_EMPRESA)
            .nascimento(UPDATED_NASCIMENTO)
            .skype(UPDATED_SKYPE)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        perfilUsuario.setUsuario(user);
        return perfilUsuario;
    }

    @BeforeEach
    public void initTest() {
        perfilUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfilUsuario() throws Exception {
        int databaseSizeBeforeCreate = perfilUsuarioRepository.findAll().size();

        // Create the PerfilUsuario
        PerfilUsuarioDTO perfilUsuarioDTO = perfilUsuarioMapper.toDto(perfilUsuario);
        restPerfilUsuarioMockMvc.perform(post("/api/perfil-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilUsuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the PerfilUsuario in the database
        List<PerfilUsuario> perfilUsuarioList = perfilUsuarioRepository.findAll();
        assertThat(perfilUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        PerfilUsuario testPerfilUsuario = perfilUsuarioList.get(perfilUsuarioList.size() - 1);
        assertThat(testPerfilUsuario.getCriacao()).isEqualTo(DEFAULT_CRIACAO);
        assertThat(testPerfilUsuario.getUltimaEdicao()).isEqualTo(DEFAULT_ULTIMA_EDICAO);
        assertThat(testPerfilUsuario.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testPerfilUsuario.getEntradaNaEmpresa()).isEqualTo(DEFAULT_ENTRADA_NA_EMPRESA);
        assertThat(testPerfilUsuario.getSaidaDaEmpresa()).isEqualTo(DEFAULT_SAIDA_DA_EMPRESA);
        assertThat(testPerfilUsuario.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testPerfilUsuario.getSkype()).isEqualTo(DEFAULT_SKYPE);
        assertThat(testPerfilUsuario.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testPerfilUsuario.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPerfilUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfilUsuarioRepository.findAll().size();

        // Create the PerfilUsuario with an existing ID
        perfilUsuario.setId(1L);
        PerfilUsuarioDTO perfilUsuarioDTO = perfilUsuarioMapper.toDto(perfilUsuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilUsuarioMockMvc.perform(post("/api/perfil-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilUsuario in the database
        List<PerfilUsuario> perfilUsuarioList = perfilUsuarioRepository.findAll();
        assertThat(perfilUsuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerfilUsuarios() throws Exception {
        // Initialize the database
        perfilUsuarioRepository.saveAndFlush(perfilUsuario);

        // Get all the perfilUsuarioList
        restPerfilUsuarioMockMvc.perform(get("/api/perfil-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(sameInstant(DEFAULT_CRIACAO))))
            .andExpect(jsonPath("$.[*].ultimaEdicao").value(hasItem(sameInstant(DEFAULT_ULTIMA_EDICAO))))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
            .andExpect(jsonPath("$.[*].entradaNaEmpresa").value(hasItem(DEFAULT_ENTRADA_NA_EMPRESA.toString())))
            .andExpect(jsonPath("$.[*].saidaDaEmpresa").value(hasItem(DEFAULT_SAIDA_DA_EMPRESA.toString())))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].skype").value(hasItem(DEFAULT_SKYPE)))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))));
    }
    
    @Test
    @Transactional
    public void getPerfilUsuario() throws Exception {
        // Initialize the database
        perfilUsuarioRepository.saveAndFlush(perfilUsuario);

        // Get the perfilUsuario
        restPerfilUsuarioMockMvc.perform(get("/api/perfil-usuarios/{id}", perfilUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perfilUsuario.getId().intValue()))
            .andExpect(jsonPath("$.criacao").value(sameInstant(DEFAULT_CRIACAO)))
            .andExpect(jsonPath("$.ultimaEdicao").value(sameInstant(DEFAULT_ULTIMA_EDICAO)))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
            .andExpect(jsonPath("$.entradaNaEmpresa").value(DEFAULT_ENTRADA_NA_EMPRESA.toString()))
            .andExpect(jsonPath("$.saidaDaEmpresa").value(DEFAULT_SAIDA_DA_EMPRESA.toString()))
            .andExpect(jsonPath("$.nascimento").value(DEFAULT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.skype").value(DEFAULT_SKYPE))
            .andExpect(jsonPath("$.avatarContentType").value(DEFAULT_AVATAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.avatar").value(Base64Utils.encodeToString(DEFAULT_AVATAR)));
    }

    @Test
    @Transactional
    public void getNonExistingPerfilUsuario() throws Exception {
        // Get the perfilUsuario
        restPerfilUsuarioMockMvc.perform(get("/api/perfil-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfilUsuario() throws Exception {
        // Initialize the database
        perfilUsuarioRepository.saveAndFlush(perfilUsuario);

        int databaseSizeBeforeUpdate = perfilUsuarioRepository.findAll().size();

        // Update the perfilUsuario
        PerfilUsuario updatedPerfilUsuario = perfilUsuarioRepository.findById(perfilUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedPerfilUsuario are not directly saved in db
        em.detach(updatedPerfilUsuario);
        updatedPerfilUsuario
            .criacao(UPDATED_CRIACAO)
            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
            .versao(UPDATED_VERSAO)
            .entradaNaEmpresa(UPDATED_ENTRADA_NA_EMPRESA)
            .saidaDaEmpresa(UPDATED_SAIDA_DA_EMPRESA)
            .nascimento(UPDATED_NASCIMENTO)
            .skype(UPDATED_SKYPE)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE);
        PerfilUsuarioDTO perfilUsuarioDTO = perfilUsuarioMapper.toDto(updatedPerfilUsuario);

        restPerfilUsuarioMockMvc.perform(put("/api/perfil-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilUsuarioDTO)))
            .andExpect(status().isOk());

        // Validate the PerfilUsuario in the database
        List<PerfilUsuario> perfilUsuarioList = perfilUsuarioRepository.findAll();
        assertThat(perfilUsuarioList).hasSize(databaseSizeBeforeUpdate);
        PerfilUsuario testPerfilUsuario = perfilUsuarioList.get(perfilUsuarioList.size() - 1);
        assertThat(testPerfilUsuario.getCriacao()).isEqualTo(UPDATED_CRIACAO);
        assertThat(testPerfilUsuario.getUltimaEdicao()).isEqualTo(UPDATED_ULTIMA_EDICAO);
        assertThat(testPerfilUsuario.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testPerfilUsuario.getEntradaNaEmpresa()).isEqualTo(UPDATED_ENTRADA_NA_EMPRESA);
        assertThat(testPerfilUsuario.getSaidaDaEmpresa()).isEqualTo(UPDATED_SAIDA_DA_EMPRESA);
        assertThat(testPerfilUsuario.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testPerfilUsuario.getSkype()).isEqualTo(UPDATED_SKYPE);
        assertThat(testPerfilUsuario.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testPerfilUsuario.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfilUsuario() throws Exception {
        int databaseSizeBeforeUpdate = perfilUsuarioRepository.findAll().size();

        // Create the PerfilUsuario
        PerfilUsuarioDTO perfilUsuarioDTO = perfilUsuarioMapper.toDto(perfilUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilUsuarioMockMvc.perform(put("/api/perfil-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilUsuario in the database
        List<PerfilUsuario> perfilUsuarioList = perfilUsuarioRepository.findAll();
        assertThat(perfilUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfilUsuario() throws Exception {
        // Initialize the database
        perfilUsuarioRepository.saveAndFlush(perfilUsuario);

        int databaseSizeBeforeDelete = perfilUsuarioRepository.findAll().size();

        // Delete the perfilUsuario
        restPerfilUsuarioMockMvc.perform(delete("/api/perfil-usuarios/{id}", perfilUsuario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerfilUsuario> perfilUsuarioList = perfilUsuarioRepository.findAll();
        assertThat(perfilUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
