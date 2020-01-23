package com.objective.informa.web.rest;

import com.objective.informa.InformaApp;
import com.objective.informa.domain.PushSubscription;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.repository.PushSubscriptionRepository;
import com.objective.informa.service.PushSubscriptionService;
import com.objective.informa.service.dto.PushSubscriptionDTO;
import com.objective.informa.service.mapper.PushSubscriptionMapper;
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
 * Integration tests for the {@link PushSubscriptionResource} REST controller.
 */
@SpringBootTest(classes = InformaApp.class)
public class PushSubscriptionResourceIT {

    private static final Long DEFAULT_VERSAO = 1L;
    private static final Long UPDATED_VERSAO = 2L;

    private static final ZonedDateTime DEFAULT_CRIACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRIACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH = "AAAAAAAAAA";
    private static final String UPDATED_AUTH = "BBBBBBBBBB";

    @Autowired
    private PushSubscriptionRepository pushSubscriptionRepository;

    @Autowired
    private PushSubscriptionMapper pushSubscriptionMapper;

    @Autowired
    private PushSubscriptionService pushSubscriptionService;

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

    private MockMvc restPushSubscriptionMockMvc;

    private PushSubscription pushSubscription;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PushSubscriptionResource pushSubscriptionResource = new PushSubscriptionResource(pushSubscriptionService);
        this.restPushSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(pushSubscriptionResource)
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
    public static PushSubscription createEntity(EntityManager em) {
        PushSubscription pushSubscription = new PushSubscription()
            .versao(DEFAULT_VERSAO)
            .criacao(DEFAULT_CRIACAO)
            .endpoint(DEFAULT_ENDPOINT)
            .key(DEFAULT_KEY)
            .auth(DEFAULT_AUTH);
        // Add required entity
        PerfilUsuario perfilUsuario;
        if (TestUtil.findAll(em, PerfilUsuario.class).isEmpty()) {
            perfilUsuario = PerfilUsuarioResourceIT.createEntity(em);
            em.persist(perfilUsuario);
            em.flush();
        } else {
            perfilUsuario = TestUtil.findAll(em, PerfilUsuario.class).get(0);
        }
        pushSubscription.setPerfil(perfilUsuario);
        return pushSubscription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PushSubscription createUpdatedEntity(EntityManager em) {
        PushSubscription pushSubscription = new PushSubscription()
            .versao(UPDATED_VERSAO)
            .criacao(UPDATED_CRIACAO)
            .endpoint(UPDATED_ENDPOINT)
            .key(UPDATED_KEY)
            .auth(UPDATED_AUTH);
        // Add required entity
        PerfilUsuario perfilUsuario;
        if (TestUtil.findAll(em, PerfilUsuario.class).isEmpty()) {
            perfilUsuario = PerfilUsuarioResourceIT.createUpdatedEntity(em);
            em.persist(perfilUsuario);
            em.flush();
        } else {
            perfilUsuario = TestUtil.findAll(em, PerfilUsuario.class).get(0);
        }
        pushSubscription.setPerfil(perfilUsuario);
        return pushSubscription;
    }

    @BeforeEach
    public void initTest() {
        pushSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createPushSubscription() throws Exception {
        int databaseSizeBeforeCreate = pushSubscriptionRepository.findAll().size();

        // Create the PushSubscription
        PushSubscriptionDTO pushSubscriptionDTO = pushSubscriptionMapper.toDto(pushSubscription);
        restPushSubscriptionMockMvc.perform(post("/api/push-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the PushSubscription in the database
        List<PushSubscription> pushSubscriptionList = pushSubscriptionRepository.findAll();
        assertThat(pushSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        PushSubscription testPushSubscription = pushSubscriptionList.get(pushSubscriptionList.size() - 1);
        assertThat(testPushSubscription.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testPushSubscription.getCriacao()).isEqualTo(DEFAULT_CRIACAO);
        assertThat(testPushSubscription.getEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
        assertThat(testPushSubscription.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testPushSubscription.getAuth()).isEqualTo(DEFAULT_AUTH);
    }

    @Test
    @Transactional
    public void createPushSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pushSubscriptionRepository.findAll().size();

        // Create the PushSubscription with an existing ID
        pushSubscription.setId(1L);
        PushSubscriptionDTO pushSubscriptionDTO = pushSubscriptionMapper.toDto(pushSubscription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPushSubscriptionMockMvc.perform(post("/api/push-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PushSubscription in the database
        List<PushSubscription> pushSubscriptionList = pushSubscriptionRepository.findAll();
        assertThat(pushSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPushSubscriptions() throws Exception {
        // Initialize the database
        pushSubscriptionRepository.saveAndFlush(pushSubscription);

        // Get all the pushSubscriptionList
        restPushSubscriptionMockMvc.perform(get("/api/push-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pushSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(sameInstant(DEFAULT_CRIACAO))))
            .andExpect(jsonPath("$.[*].endpoint").value(hasItem(DEFAULT_ENDPOINT)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].auth").value(hasItem(DEFAULT_AUTH)));
    }
    
    @Test
    @Transactional
    public void getPushSubscription() throws Exception {
        // Initialize the database
        pushSubscriptionRepository.saveAndFlush(pushSubscription);

        // Get the pushSubscription
        restPushSubscriptionMockMvc.perform(get("/api/push-subscriptions/{id}", pushSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pushSubscription.getId().intValue()))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
            .andExpect(jsonPath("$.criacao").value(sameInstant(DEFAULT_CRIACAO)))
            .andExpect(jsonPath("$.endpoint").value(DEFAULT_ENDPOINT))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.auth").value(DEFAULT_AUTH));
    }

    @Test
    @Transactional
    public void getNonExistingPushSubscription() throws Exception {
        // Get the pushSubscription
        restPushSubscriptionMockMvc.perform(get("/api/push-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePushSubscription() throws Exception {
        // Initialize the database
        pushSubscriptionRepository.saveAndFlush(pushSubscription);

        int databaseSizeBeforeUpdate = pushSubscriptionRepository.findAll().size();

        // Update the pushSubscription
        PushSubscription updatedPushSubscription = pushSubscriptionRepository.findById(pushSubscription.getId()).get();
        // Disconnect from session so that the updates on updatedPushSubscription are not directly saved in db
        em.detach(updatedPushSubscription);
        updatedPushSubscription
            .versao(UPDATED_VERSAO)
            .criacao(UPDATED_CRIACAO)
            .endpoint(UPDATED_ENDPOINT)
            .key(UPDATED_KEY)
            .auth(UPDATED_AUTH);
        PushSubscriptionDTO pushSubscriptionDTO = pushSubscriptionMapper.toDto(updatedPushSubscription);

        restPushSubscriptionMockMvc.perform(put("/api/push-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionDTO)))
            .andExpect(status().isOk());

        // Validate the PushSubscription in the database
        List<PushSubscription> pushSubscriptionList = pushSubscriptionRepository.findAll();
        assertThat(pushSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        PushSubscription testPushSubscription = pushSubscriptionList.get(pushSubscriptionList.size() - 1);
        assertThat(testPushSubscription.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testPushSubscription.getCriacao()).isEqualTo(UPDATED_CRIACAO);
        assertThat(testPushSubscription.getEndpoint()).isEqualTo(UPDATED_ENDPOINT);
        assertThat(testPushSubscription.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testPushSubscription.getAuth()).isEqualTo(UPDATED_AUTH);
    }

    @Test
    @Transactional
    public void updateNonExistingPushSubscription() throws Exception {
        int databaseSizeBeforeUpdate = pushSubscriptionRepository.findAll().size();

        // Create the PushSubscription
        PushSubscriptionDTO pushSubscriptionDTO = pushSubscriptionMapper.toDto(pushSubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPushSubscriptionMockMvc.perform(put("/api/push-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PushSubscription in the database
        List<PushSubscription> pushSubscriptionList = pushSubscriptionRepository.findAll();
        assertThat(pushSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePushSubscription() throws Exception {
        // Initialize the database
        pushSubscriptionRepository.saveAndFlush(pushSubscription);

        int databaseSizeBeforeDelete = pushSubscriptionRepository.findAll().size();

        // Delete the pushSubscription
        restPushSubscriptionMockMvc.perform(delete("/api/push-subscriptions/{id}", pushSubscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PushSubscription> pushSubscriptionList = pushSubscriptionRepository.findAll();
        assertThat(pushSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
