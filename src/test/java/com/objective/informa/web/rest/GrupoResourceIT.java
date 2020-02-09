package com.objective.informa.web.rest;

import com.objective.informa.InformaApp;
import com.objective.informa.domain.Grupo;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.security.AuthoritiesConstants;
import com.objective.informa.service.GrupoService;
import com.objective.informa.service.dto.GrupoDTO;
import com.objective.informa.service.environments.GruposEUsuariosEnvironment;
import com.objective.informa.service.environments.ZeradoEnvironment;
import com.objective.informa.service.mapper.GrupoMapper;
import com.objective.informa.web.rest.errors.ExceptionTranslator;
import com.objective.insistence.layer.environment.InsistenceEnvironmentService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GrupoResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class GrupoResourceIT {

    private static final Long DEFAULT_VERSAO = 0L;
    private static final Long UPDATED_VERSAO = 1L;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FORMAL = false;
    private static final Boolean UPDATED_FORMAL = true;

    private static final Boolean DEFAULT_OPCIONAL = false;
    private static final Boolean UPDATED_OPCIONAL = true;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoMapper grupoMapper;

    @Autowired
    private GrupoService grupoService;

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
    
    @Autowired
    private InsistenceEnvironmentService insistenceEnvironmentService; 

    private MockMvc restGrupoMockMvc;

    private Grupo grupo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrupoResource grupoResource = new GrupoResource(grupoService);
        this.restGrupoMockMvc = MockMvcBuilders.standaloneSetup(grupoResource)
    		.addFilter(new SecurityContextPersistenceFilter())
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
    public static Grupo createEntity(EntityManager em) {
        Grupo grupo = new Grupo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .formal(DEFAULT_FORMAL)
            .opcional(DEFAULT_OPCIONAL);
        return grupo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grupo createUpdatedEntity(EntityManager em) {
        Grupo grupo = new Grupo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .formal(UPDATED_FORMAL)
            .opcional(UPDATED_OPCIONAL);
        return grupo;
    }

    @BeforeEach
    public void initTest() {

    }

    @AfterEach
    public void finishTest() {
    	insistenceEnvironmentService.resetEnvironments();
    }
    
    
    @Test
    @Transactional
    public void createGrupo() throws Exception {
    	insistenceEnvironmentService.executeEnvironment(ZeradoEnvironment.ZERADO);
        // Create the Grupo
    	GrupoDTO grupoDTO = new GrupoDTO();
    	grupoDTO.setNome("Novo Grupo");
    	grupoDTO.setDescricao("Qualquer coisa");
    	grupoDTO.setFormal(false);
    	grupoDTO.setOpcional(true);
        restGrupoMockMvc.perform(post("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoDTO)))
            .andExpect(status().isCreated());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(1);
        Grupo testGrupo = grupoList.get(0);
        assertThat(testGrupo.getCriacao()).isNotNull();
        assertThat(testGrupo.getUltimaEdicao()).isEqualTo(testGrupo.getCriacao());
        assertThat(testGrupo.getNome()).isEqualTo("Novo Grupo");
        assertThat(testGrupo.getDescricao()).isEqualTo("Qualquer coisa");
        assertThat(testGrupo.isFormal()).isEqualTo(false);
        assertThat(testGrupo.isOpcional()).isEqualTo(true);
    }

    @Test
    @Transactional
    public void moderadorAlteraGrupo() throws Exception {
    	insistenceEnvironmentService.executeEnvironment(GruposEUsuariosEnvironment.GRUPOS_E_USUARIOS);
		GrupoDTO grupoPapoLivre = grupoDtoByName("papo-livre");
    	grupoPapoLivre.setDescricao("Outra Descricao");		
    	restGrupoMockMvc.perform(put("/api/grupos")
			.with(user("moderador"))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoPapoLivre)))
            .andExpect(status().isOk());

        // Validate the Grupo in the database
		GrupoDTO grupoPapoLivreSalvo = grupoDtoByName("papo-livre");
        assertThat(grupoPapoLivreSalvo.getDescricao()).isEqualTo("Outra Descricao");
    }

    @Test
    @Transactional
    public void naoModeradorNaoConsegueAlterarGrupo() throws Exception {
    	insistenceEnvironmentService.executeEnvironment(GruposEUsuariosEnvironment.GRUPOS_E_USUARIOS);
		GrupoDTO grupoPapoLivre = grupoDtoByName("papo-livre");
    	grupoPapoLivre.setDescricao("Outra Descricao");		
    	restGrupoMockMvc.perform(put("/api/grupos")
			.with(user("normal"))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoPapoLivre)))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void adminAlteraGrupo() throws Exception {
    	insistenceEnvironmentService.executeEnvironment(GruposEUsuariosEnvironment.GRUPOS_E_USUARIOS);
		GrupoDTO grupoPapoLivre = grupoDtoByName("papo-livre");
    	grupoPapoLivre.setDescricao("Outra Descricao");		
    	restGrupoMockMvc.perform(put("/api/grupos")
			.with(user("admin").roles("ADMIN", "USER"))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoPapoLivre)))
            .andExpect(status().isOk());

        // Validate the Grupo in the database
		GrupoDTO grupoPapoLivreSalvo = grupoDtoByName("papo-livre");
        assertThat(grupoPapoLivreSalvo.getDescricao()).isEqualTo("Outra Descricao");
    }

    
	private GrupoDTO grupoDtoByName(String nomeGrupo) {
		GrupoDTO grupoPapoLivre = grupoService.findAll()
    			.stream()
    			.filter(grupo -> grupo.getNome().equalsIgnoreCase(nomeGrupo))
    			.findFirst()
    			.get();
		return grupoPapoLivre;
	}

//    @Test
//    @Transactional
//    public void createGrupoWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = grupoRepository.findAll().size();
//
//        // Create the Grupo with an existing ID
//        grupo.setId(1L);
//        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restGrupoMockMvc.perform(post("/api/grupos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(grupoDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Grupo in the database
//        List<Grupo> grupoList = grupoRepository.findAll();
//        assertThat(grupoList).hasSize(databaseSizeBeforeCreate);
//    }


//    @Test
//    @Transactional
//    public void getAllGrupos() throws Exception {
//        // Initialize the database
//        grupoRepository.saveAndFlush(grupo);
//
//        // Get all the grupoList
//        restGrupoMockMvc.perform(get("/api/grupos?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(grupo.getId().intValue())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
////            .andExpect(jsonPath("$.[*].criacao").value(hasItem(sameInstant(DEFAULT_CRIACAO))))
////            .andExpect(jsonPath("$.[*].ultimaEdicao").value(hasItem(sameInstant(DEFAULT_ULTIMA_EDICAO))))
//            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
//            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
//            .andExpect(jsonPath("$.[*].formal").value(hasItem(DEFAULT_FORMAL.booleanValue())))
//            .andExpect(jsonPath("$.[*].opcional").value(hasItem(DEFAULT_OPCIONAL.booleanValue())));
//    }

//    @Test
//    @Transactional
//    public void getGrupo() throws Exception {
//        // Initialize the database
//        grupoRepository.saveAndFlush(grupo);
//
//        // Get the grupo
//        restGrupoMockMvc.perform(get("/api/grupos/{id}", grupo.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(grupo.getId().intValue()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
////            .andExpect(jsonPath("$.criacao").value(grupo.getCriacao().))
////            .andExpect(jsonPath("$.ultimaEdicao").exists())
//            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
//            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
//            .andExpect(jsonPath("$.formal").value(DEFAULT_FORMAL.booleanValue()))
//            .andExpect(jsonPath("$.opcional").value(DEFAULT_OPCIONAL.booleanValue()));
//    }

//    @Test
//    @Transactional
//    public void getNonExistingGrupo() throws Exception {
//        // Get the grupo
//        restGrupoMockMvc.perform(get("/api/grupos/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateGrupo() throws Exception {
//        // Initialize the database
//        grupoRepository.saveAndFlush(grupo);
//
//        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();
//
//        // Update the grupo
//        Grupo updatedGrupo = grupoRepository.findById(grupo.getId()).get();
//        // Disconnect from session so that the updates on updatedGrupo are not directly saved in db
//        em.detach(updatedGrupo);
//        updatedGrupo
//            .nome(UPDATED_NOME)
//            .descricao(UPDATED_DESCRICAO)
//            .formal(UPDATED_FORMAL)
//            .opcional(UPDATED_OPCIONAL);
//        GrupoDTO grupoDTO = grupoMapper.toDto(updatedGrupo);
//
//        restGrupoMockMvc.perform(put("/api/grupos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(grupoDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Grupo in the database
//        List<Grupo> grupoList = grupoRepository.findAll();
//        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
//        Grupo testGrupo = grupoList.get(grupoList.size() - 1);
//        assertThat(testGrupo.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testGrupo.getCriacao()).isNotNull();
//        assertThat(testGrupo.getUltimaEdicao()).isNotNull();
//        assertThat(testGrupo.getNome()).isEqualTo(UPDATED_NOME);
//        assertThat(testGrupo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
//        assertThat(testGrupo.isFormal()).isEqualTo(UPDATED_FORMAL);
//        assertThat(testGrupo.isOpcional()).isEqualTo(UPDATED_OPCIONAL);
//    }

//    @Test
//    @Transactional
//    public void updateNonExistingGrupo() throws Exception {
//        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();
//
//        // Create the Grupo
//        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restGrupoMockMvc.perform(put("/api/grupos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(grupoDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Grupo in the database
//        List<Grupo> grupoList = grupoRepository.findAll();
//        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
//    }

//    @Test
//    @Transactional
//    public void deleteGrupo() throws Exception {
//        // Initialize the database
//        grupoRepository.saveAndFlush(grupo);
//
//        int databaseSizeBeforeDelete = grupoRepository.findAll().size();
//
//        // Delete the grupo
//        restGrupoMockMvc.perform(delete("/api/grupos/{id}", grupo.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Grupo> grupoList = grupoRepository.findAll();
//        assertThat(grupoList).hasSize(databaseSizeBeforeDelete - 1);
//    }
}
