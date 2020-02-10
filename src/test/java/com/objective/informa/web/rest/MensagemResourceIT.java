package com.objective.informa.web.rest;

//import javax.persistence.EntityManager;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.validation.Validator;
//
//import com.objective.informa.InformaApp;
//import com.objective.informa.domain.Mensagem;
//import com.objective.informa.repository.MensagemRepository;
//import com.objective.informa.service.MensagemService;
//import com.objective.informa.service.mapper.MensagemMapper;
//import com.objective.informa.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link MensagemResource} REST controller.
 * TODO Tem que rever tudo. Inclusive faltou o autor pelo jeito :D
 */
//@SpringBootTest(classes = InformaApp.class)
public class MensagemResourceIT {

//    private static final Long DEFAULT_VERSAO = 0L;
//
//    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
//    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";
//
//    @Autowired
//    private MensagemRepository mensagemRepository;
//
//    @Autowired
//    private MensagemMapper mensagemMapper;
//
//    @Autowired
//    private MensagemService mensagemService;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private Validator validator;
//
//    private MockMvc restMensagemMockMvc;
//
//    private Mensagem mensagem;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final MensagemResource mensagemResource = new MensagemResource(mensagemService);
//        this.restMensagemMockMvc = MockMvcBuilders.standaloneSetup(mensagemResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter)
//            .setValidator(validator).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Mensagem createEntity(EntityManager em) {
//        Mensagem mensagem = new Mensagem()
//            .conteudo(DEFAULT_CONTEUDO);
//        // Add required entity
//        User user = UserResourceIT.createEntity(em);
//        em.persist(user);
//        em.flush();
//        mensagem.setAutor(user);
//        return mensagem;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Mensagem createUpdatedEntity(EntityManager em) {
//        Mensagem mensagem = new Mensagem()
//            .conteudo(UPDATED_CONTEUDO);
//        // Add required entity
//        User user = UserResourceIT.createEntity(em);
//        em.persist(user);
//        em.flush();
//        mensagem.setAutor(user);
//        return mensagem;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        mensagem = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    @WithMockUser
//    public void createMensagem() throws Exception {
//        int databaseSizeBeforeCreate = mensagemRepository.findAll().size();
//
//        // Create the Mensagem
//        MensagemDTO mensagemDTO = mensagemMapper.toDto(mensagem);
//        restMensagemMockMvc.perform(post("/api/mensagems")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(mensagemDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Mensagem in the database
//        List<Mensagem> mensagemList = mensagemRepository.findAll();
//        assertThat(mensagemList).hasSize(databaseSizeBeforeCreate + 1);
//        Mensagem testMensagem = mensagemList.get(mensagemList.size() - 1);
//        assertThat(testMensagem.getVersao()).isEqualTo(0L);
//        assertThat(testMensagem.getCriacao()).isNotNull();
//        assertThat(testMensagem.getUltimaEdicao()).isNotNull();
//        assertThat(testMensagem.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
//        assertThat(testMensagem.isTemConversa()).isEqualTo(false);
//    }
//
//    @Test
//    @Transactional
//    public void createMensagemWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = mensagemRepository.findAll().size();
//
//        // Create the Mensagem with an existing ID
//        mensagem.setId(1L);
//        MensagemDTO mensagemDTO = mensagemMapper.toDto(mensagem);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restMensagemMockMvc.perform(post("/api/mensagems")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(mensagemDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Mensagem in the database
//        List<Mensagem> mensagemList = mensagemRepository.findAll();
//        assertThat(mensagemList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMensagems() throws Exception {
//        // Initialize the database
//        mensagemRepository.saveAndFlush(mensagem);
//
//        // Get all the mensagemList
//        restMensagemMockMvc.perform(get("/api/mensagems?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(mensagem.getId().intValue())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].criacao").value(hasItem(notNullValue())))
//            .andExpect(jsonPath("$.[*].ultimaEdicao").value(hasItem(notNullValue())))
//            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO)))
//            .andExpect(jsonPath("$.[*].temConversa").value(hasItem(false)));
//    }

//    @Test
//    @Transactional
//    public void getMensagem() throws Exception {
//        // Initialize the database
//        mensagemRepository.saveAndFlush(mensagem);
//
//        // Get the mensagem
//        restMensagemMockMvc.perform(get("/api/mensagems/{id}", mensagem.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(mensagem.getId().intValue()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.criacao").value(sameInstant(DEFAULT_CRIACAO)))
//            .andExpect(jsonPath("$.ultimaEdicao").value(sameInstant(DEFAULT_ULTIMA_EDICAO)))
//            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO))
//            .andExpect(jsonPath("$.temConversa").value(DEFAULT_TEM_CONVERSA.booleanValue()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingMensagem() throws Exception {
//        // Get the mensagem
//        restMensagemMockMvc.perform(get("/api/mensagems/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateMensagem() throws Exception {
//        // Initialize the database
//        mensagemRepository.saveAndFlush(mensagem);
//
//        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();
//
//        // Update the mensagem
//        Mensagem updatedMensagem = mensagemRepository.findById(mensagem.getId()).get();
//        // Disconnect from session so that the updates on updatedMensagem are not directly saved in db
//        em.detach(updatedMensagem);
//        updatedMensagem
//            .versao(UPDATED_VERSAO)
//            .criacao(UPDATED_CRIACAO)
//            .ultimaEdicao(UPDATED_ULTIMA_EDICAO)
//            .conteudo(UPDATED_CONTEUDO)
//            .temConversa(UPDATED_TEM_CONVERSA);
//        MensagemDTO mensagemDTO = mensagemMapper.toDto(updatedMensagem);
//
//        restMensagemMockMvc.perform(put("/api/mensagems")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(mensagemDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Mensagem in the database
//        List<Mensagem> mensagemList = mensagemRepository.findAll();
//        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
//        Mensagem testMensagem = mensagemList.get(mensagemList.size() - 1);
//        assertThat(testMensagem.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testMensagem.getCriacao()).isEqualTo(UPDATED_CRIACAO);
//        assertThat(testMensagem.getUltimaEdicao()).isEqualTo(UPDATED_ULTIMA_EDICAO);
//        assertThat(testMensagem.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
//        assertThat(testMensagem.isTemConversa()).isEqualTo(UPDATED_TEM_CONVERSA);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingMensagem() throws Exception {
//        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();
//
//        // Create the Mensagem
//        MensagemDTO mensagemDTO = mensagemMapper.toDto(mensagem);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restMensagemMockMvc.perform(put("/api/mensagems")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(mensagemDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Mensagem in the database
//        List<Mensagem> mensagemList = mensagemRepository.findAll();
//        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteMensagem() throws Exception {
//        // Initialize the database
//        mensagemRepository.saveAndFlush(mensagem);
//
//        int databaseSizeBeforeDelete = mensagemRepository.findAll().size();
//
//        // Delete the mensagem
//        restMensagemMockMvc.perform(delete("/api/mensagems/{id}", mensagem.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Mensagem> mensagemList = mensagemRepository.findAll();
//        assertThat(mensagemList).hasSize(databaseSizeBeforeDelete - 1);
//    }
}
