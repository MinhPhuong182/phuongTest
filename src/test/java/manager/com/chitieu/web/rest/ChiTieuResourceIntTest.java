package manager.com.chitieu.web.rest;

import manager.com.chitieu.QuanlychitieuApp;

import manager.com.chitieu.domain.ChiTieu;
import manager.com.chitieu.repository.ChiTieuRepository;
import manager.com.chitieu.service.ChiTieuService;
import manager.com.chitieu.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static manager.com.chitieu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChiTieuResource REST controller.
 *
 * @see ChiTieuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuanlychitieuApp.class)
public class ChiTieuResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_PROGRAM = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_PROGRAM = "BBBBBBBBBB";

    @Autowired
    private ChiTieuRepository chiTieuRepository;

    @Autowired
    private ChiTieuService chiTieuService;

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

    private MockMvc restChiTieuMockMvc;

    private ChiTieu chiTieu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChiTieuResource chiTieuResource = new ChiTieuResource(chiTieuService);
        this.restChiTieuMockMvc = MockMvcBuilders.standaloneSetup(chiTieuResource)
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
    public static ChiTieu createEntity(EntityManager em) {
        ChiTieu chiTieu = new ChiTieu()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .userName(DEFAULT_USER_NAME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .updateProgram(DEFAULT_UPDATE_PROGRAM);
        return chiTieu;
    }

    @Before
    public void initTest() {
        chiTieu = createEntity(em);
    }

    @Test
    @Transactional
    public void createChiTieu() throws Exception {
        int databaseSizeBeforeCreate = chiTieuRepository.findAll().size();

        // Create the ChiTieu
        restChiTieuMockMvc.perform(post("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isCreated());

        // Validate the ChiTieu in the database
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeCreate + 1);
        ChiTieu testChiTieu = chiTieuList.get(chiTieuList.size() - 1);
        assertThat(testChiTieu.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testChiTieu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChiTieu.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testChiTieu.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testChiTieu.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testChiTieu.getUpdateProgram()).isEqualTo(DEFAULT_UPDATE_PROGRAM);
    }

    @Test
    @Transactional
    public void createChiTieuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chiTieuRepository.findAll().size();

        // Create the ChiTieu with an existing ID
        chiTieu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChiTieuMockMvc.perform(post("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isBadRequest());

        // Validate the ChiTieu in the database
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTieuRepository.findAll().size();
        // set the field null
        chiTieu.setCode(null);

        // Create the ChiTieu, which fails.

        restChiTieuMockMvc.perform(post("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isBadRequest());

        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTieuRepository.findAll().size();
        // set the field null
        chiTieu.setName(null);

        // Create the ChiTieu, which fails.

        restChiTieuMockMvc.perform(post("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isBadRequest());

        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChiTieus() throws Exception {
        // Initialize the database
        chiTieuRepository.saveAndFlush(chiTieu);

        // Get all the chiTieuList
        restChiTieuMockMvc.perform(get("/api/chi-tieus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chiTieu.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateProgram").value(hasItem(DEFAULT_UPDATE_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getChiTieu() throws Exception {
        // Initialize the database
        chiTieuRepository.saveAndFlush(chiTieu);

        // Get the chiTieu
        restChiTieuMockMvc.perform(get("/api/chi-tieus/{id}", chiTieu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chiTieu.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.updateProgram").value(DEFAULT_UPDATE_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChiTieu() throws Exception {
        // Get the chiTieu
        restChiTieuMockMvc.perform(get("/api/chi-tieus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChiTieu() throws Exception {
        // Initialize the database
        chiTieuService.save(chiTieu);

        int databaseSizeBeforeUpdate = chiTieuRepository.findAll().size();

        // Update the chiTieu
        ChiTieu updatedChiTieu = chiTieuRepository.findById(chiTieu.getId()).get();
        // Disconnect from session so that the updates on updatedChiTieu are not directly saved in db
        em.detach(updatedChiTieu);
        updatedChiTieu
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .userName(UPDATED_USER_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .updateProgram(UPDATED_UPDATE_PROGRAM);

        restChiTieuMockMvc.perform(put("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChiTieu)))
            .andExpect(status().isOk());

        // Validate the ChiTieu in the database
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeUpdate);
        ChiTieu testChiTieu = chiTieuList.get(chiTieuList.size() - 1);
        assertThat(testChiTieu.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testChiTieu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChiTieu.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChiTieu.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testChiTieu.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testChiTieu.getUpdateProgram()).isEqualTo(UPDATED_UPDATE_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingChiTieu() throws Exception {
        int databaseSizeBeforeUpdate = chiTieuRepository.findAll().size();

        // Create the ChiTieu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTieuMockMvc.perform(put("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isBadRequest());

        // Validate the ChiTieu in the database
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChiTieu() throws Exception {
        // Initialize the database
        chiTieuService.save(chiTieu);

        int databaseSizeBeforeDelete = chiTieuRepository.findAll().size();

        // Delete the chiTieu
        restChiTieuMockMvc.perform(delete("/api/chi-tieus/{id}", chiTieu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTieu.class);
        ChiTieu chiTieu1 = new ChiTieu();
        chiTieu1.setId(1L);
        ChiTieu chiTieu2 = new ChiTieu();
        chiTieu2.setId(chiTieu1.getId());
        assertThat(chiTieu1).isEqualTo(chiTieu2);
        chiTieu2.setId(2L);
        assertThat(chiTieu1).isNotEqualTo(chiTieu2);
        chiTieu1.setId(null);
        assertThat(chiTieu1).isNotEqualTo(chiTieu2);
    }
}
