package manager.com.chitieu.web.rest;

import manager.com.chitieu.QuanlychitieuApp;

import manager.com.chitieu.domain.NhomChiTieu;
import manager.com.chitieu.repository.NhomChiTieuRepository;
import manager.com.chitieu.service.NhomChiTieuService;
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
 * Test class for the NhomChiTieuResource REST controller.
 *
 * @see NhomChiTieuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuanlychitieuApp.class)
public class NhomChiTieuResourceIntTest {

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
    private NhomChiTieuRepository nhomChiTieuRepository;

    @Autowired
    private NhomChiTieuService nhomChiTieuService;

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

    private MockMvc restNhomChiTieuMockMvc;

    private NhomChiTieu nhomChiTieu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhomChiTieuResource nhomChiTieuResource = new NhomChiTieuResource(nhomChiTieuService);
        this.restNhomChiTieuMockMvc = MockMvcBuilders.standaloneSetup(nhomChiTieuResource)
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
    public static NhomChiTieu createEntity(EntityManager em) {
        NhomChiTieu nhomChiTieu = new NhomChiTieu()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .userName(DEFAULT_USER_NAME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .updateProgram(DEFAULT_UPDATE_PROGRAM);
        return nhomChiTieu;
    }

    @Before
    public void initTest() {
        nhomChiTieu = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhomChiTieu() throws Exception {
        int databaseSizeBeforeCreate = nhomChiTieuRepository.findAll().size();

        // Create the NhomChiTieu
        restNhomChiTieuMockMvc.perform(post("/api/nhom-chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomChiTieu)))
            .andExpect(status().isCreated());

        // Validate the NhomChiTieu in the database
        List<NhomChiTieu> nhomChiTieuList = nhomChiTieuRepository.findAll();
        assertThat(nhomChiTieuList).hasSize(databaseSizeBeforeCreate + 1);
        NhomChiTieu testNhomChiTieu = nhomChiTieuList.get(nhomChiTieuList.size() - 1);
        assertThat(testNhomChiTieu.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNhomChiTieu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNhomChiTieu.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNhomChiTieu.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testNhomChiTieu.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testNhomChiTieu.getUpdateProgram()).isEqualTo(DEFAULT_UPDATE_PROGRAM);
    }

    @Test
    @Transactional
    public void createNhomChiTieuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhomChiTieuRepository.findAll().size();

        // Create the NhomChiTieu with an existing ID
        nhomChiTieu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhomChiTieuMockMvc.perform(post("/api/nhom-chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomChiTieu)))
            .andExpect(status().isBadRequest());

        // Validate the NhomChiTieu in the database
        List<NhomChiTieu> nhomChiTieuList = nhomChiTieuRepository.findAll();
        assertThat(nhomChiTieuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhomChiTieuRepository.findAll().size();
        // set the field null
        nhomChiTieu.setCode(null);

        // Create the NhomChiTieu, which fails.

        restNhomChiTieuMockMvc.perform(post("/api/nhom-chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomChiTieu)))
            .andExpect(status().isBadRequest());

        List<NhomChiTieu> nhomChiTieuList = nhomChiTieuRepository.findAll();
        assertThat(nhomChiTieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhomChiTieuRepository.findAll().size();
        // set the field null
        nhomChiTieu.setName(null);

        // Create the NhomChiTieu, which fails.

        restNhomChiTieuMockMvc.perform(post("/api/nhom-chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomChiTieu)))
            .andExpect(status().isBadRequest());

        List<NhomChiTieu> nhomChiTieuList = nhomChiTieuRepository.findAll();
        assertThat(nhomChiTieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhomChiTieus() throws Exception {
        // Initialize the database
        nhomChiTieuRepository.saveAndFlush(nhomChiTieu);

        // Get all the nhomChiTieuList
        restNhomChiTieuMockMvc.perform(get("/api/nhom-chi-tieus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhomChiTieu.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateProgram").value(hasItem(DEFAULT_UPDATE_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getNhomChiTieu() throws Exception {
        // Initialize the database
        nhomChiTieuRepository.saveAndFlush(nhomChiTieu);

        // Get the nhomChiTieu
        restNhomChiTieuMockMvc.perform(get("/api/nhom-chi-tieus/{id}", nhomChiTieu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhomChiTieu.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.updateProgram").value(DEFAULT_UPDATE_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhomChiTieu() throws Exception {
        // Get the nhomChiTieu
        restNhomChiTieuMockMvc.perform(get("/api/nhom-chi-tieus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhomChiTieu() throws Exception {
        // Initialize the database
        nhomChiTieuService.save(nhomChiTieu);

        int databaseSizeBeforeUpdate = nhomChiTieuRepository.findAll().size();

        // Update the nhomChiTieu
        NhomChiTieu updatedNhomChiTieu = nhomChiTieuRepository.findById(nhomChiTieu.getId()).get();
        // Disconnect from session so that the updates on updatedNhomChiTieu are not directly saved in db
        em.detach(updatedNhomChiTieu);
        updatedNhomChiTieu
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .userName(UPDATED_USER_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .updateProgram(UPDATED_UPDATE_PROGRAM);

        restNhomChiTieuMockMvc.perform(put("/api/nhom-chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhomChiTieu)))
            .andExpect(status().isOk());

        // Validate the NhomChiTieu in the database
        List<NhomChiTieu> nhomChiTieuList = nhomChiTieuRepository.findAll();
        assertThat(nhomChiTieuList).hasSize(databaseSizeBeforeUpdate);
        NhomChiTieu testNhomChiTieu = nhomChiTieuList.get(nhomChiTieuList.size() - 1);
        assertThat(testNhomChiTieu.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNhomChiTieu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNhomChiTieu.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNhomChiTieu.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testNhomChiTieu.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testNhomChiTieu.getUpdateProgram()).isEqualTo(UPDATED_UPDATE_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingNhomChiTieu() throws Exception {
        int databaseSizeBeforeUpdate = nhomChiTieuRepository.findAll().size();

        // Create the NhomChiTieu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhomChiTieuMockMvc.perform(put("/api/nhom-chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomChiTieu)))
            .andExpect(status().isBadRequest());

        // Validate the NhomChiTieu in the database
        List<NhomChiTieu> nhomChiTieuList = nhomChiTieuRepository.findAll();
        assertThat(nhomChiTieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhomChiTieu() throws Exception {
        // Initialize the database
        nhomChiTieuService.save(nhomChiTieu);

        int databaseSizeBeforeDelete = nhomChiTieuRepository.findAll().size();

        // Delete the nhomChiTieu
        restNhomChiTieuMockMvc.perform(delete("/api/nhom-chi-tieus/{id}", nhomChiTieu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhomChiTieu> nhomChiTieuList = nhomChiTieuRepository.findAll();
        assertThat(nhomChiTieuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhomChiTieu.class);
        NhomChiTieu nhomChiTieu1 = new NhomChiTieu();
        nhomChiTieu1.setId(1L);
        NhomChiTieu nhomChiTieu2 = new NhomChiTieu();
        nhomChiTieu2.setId(nhomChiTieu1.getId());
        assertThat(nhomChiTieu1).isEqualTo(nhomChiTieu2);
        nhomChiTieu2.setId(2L);
        assertThat(nhomChiTieu1).isNotEqualTo(nhomChiTieu2);
        nhomChiTieu1.setId(null);
        assertThat(nhomChiTieu1).isNotEqualTo(nhomChiTieu2);
    }
}
