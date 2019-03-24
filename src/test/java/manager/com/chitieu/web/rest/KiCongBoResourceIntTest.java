package manager.com.chitieu.web.rest;

import manager.com.chitieu.QuanlychitieuApp;

import manager.com.chitieu.domain.KiCongBo;
import manager.com.chitieu.repository.KiCongBoRepository;
import manager.com.chitieu.service.KiCongBoService;
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
 * Test class for the KiCongBoResource REST controller.
 *
 * @see KiCongBoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuanlychitieuApp.class)
public class KiCongBoResourceIntTest {

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
    private KiCongBoRepository kiCongBoRepository;

    @Autowired
    private KiCongBoService kiCongBoService;

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

    private MockMvc restKiCongBoMockMvc;

    private KiCongBo kiCongBo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KiCongBoResource kiCongBoResource = new KiCongBoResource(kiCongBoService);
        this.restKiCongBoMockMvc = MockMvcBuilders.standaloneSetup(kiCongBoResource)
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
    public static KiCongBo createEntity(EntityManager em) {
        KiCongBo kiCongBo = new KiCongBo()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .userName(DEFAULT_USER_NAME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .updateProgram(DEFAULT_UPDATE_PROGRAM);
        return kiCongBo;
    }

    @Before
    public void initTest() {
        kiCongBo = createEntity(em);
    }

    @Test
    @Transactional
    public void createKiCongBo() throws Exception {
        int databaseSizeBeforeCreate = kiCongBoRepository.findAll().size();

        // Create the KiCongBo
        restKiCongBoMockMvc.perform(post("/api/ki-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kiCongBo)))
            .andExpect(status().isCreated());

        // Validate the KiCongBo in the database
        List<KiCongBo> kiCongBoList = kiCongBoRepository.findAll();
        assertThat(kiCongBoList).hasSize(databaseSizeBeforeCreate + 1);
        KiCongBo testKiCongBo = kiCongBoList.get(kiCongBoList.size() - 1);
        assertThat(testKiCongBo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testKiCongBo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKiCongBo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testKiCongBo.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testKiCongBo.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testKiCongBo.getUpdateProgram()).isEqualTo(DEFAULT_UPDATE_PROGRAM);
    }

    @Test
    @Transactional
    public void createKiCongBoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kiCongBoRepository.findAll().size();

        // Create the KiCongBo with an existing ID
        kiCongBo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKiCongBoMockMvc.perform(post("/api/ki-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kiCongBo)))
            .andExpect(status().isBadRequest());

        // Validate the KiCongBo in the database
        List<KiCongBo> kiCongBoList = kiCongBoRepository.findAll();
        assertThat(kiCongBoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiCongBoRepository.findAll().size();
        // set the field null
        kiCongBo.setCode(null);

        // Create the KiCongBo, which fails.

        restKiCongBoMockMvc.perform(post("/api/ki-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kiCongBo)))
            .andExpect(status().isBadRequest());

        List<KiCongBo> kiCongBoList = kiCongBoRepository.findAll();
        assertThat(kiCongBoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiCongBoRepository.findAll().size();
        // set the field null
        kiCongBo.setName(null);

        // Create the KiCongBo, which fails.

        restKiCongBoMockMvc.perform(post("/api/ki-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kiCongBo)))
            .andExpect(status().isBadRequest());

        List<KiCongBo> kiCongBoList = kiCongBoRepository.findAll();
        assertThat(kiCongBoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKiCongBos() throws Exception {
        // Initialize the database
        kiCongBoRepository.saveAndFlush(kiCongBo);

        // Get all the kiCongBoList
        restKiCongBoMockMvc.perform(get("/api/ki-cong-bos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kiCongBo.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateProgram").value(hasItem(DEFAULT_UPDATE_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getKiCongBo() throws Exception {
        // Initialize the database
        kiCongBoRepository.saveAndFlush(kiCongBo);

        // Get the kiCongBo
        restKiCongBoMockMvc.perform(get("/api/ki-cong-bos/{id}", kiCongBo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kiCongBo.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.updateProgram").value(DEFAULT_UPDATE_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKiCongBo() throws Exception {
        // Get the kiCongBo
        restKiCongBoMockMvc.perform(get("/api/ki-cong-bos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKiCongBo() throws Exception {
        // Initialize the database
        kiCongBoService.save(kiCongBo);

        int databaseSizeBeforeUpdate = kiCongBoRepository.findAll().size();

        // Update the kiCongBo
        KiCongBo updatedKiCongBo = kiCongBoRepository.findById(kiCongBo.getId()).get();
        // Disconnect from session so that the updates on updatedKiCongBo are not directly saved in db
        em.detach(updatedKiCongBo);
        updatedKiCongBo
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .userName(UPDATED_USER_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .updateProgram(UPDATED_UPDATE_PROGRAM);

        restKiCongBoMockMvc.perform(put("/api/ki-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKiCongBo)))
            .andExpect(status().isOk());

        // Validate the KiCongBo in the database
        List<KiCongBo> kiCongBoList = kiCongBoRepository.findAll();
        assertThat(kiCongBoList).hasSize(databaseSizeBeforeUpdate);
        KiCongBo testKiCongBo = kiCongBoList.get(kiCongBoList.size() - 1);
        assertThat(testKiCongBo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testKiCongBo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKiCongBo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testKiCongBo.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testKiCongBo.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testKiCongBo.getUpdateProgram()).isEqualTo(UPDATED_UPDATE_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingKiCongBo() throws Exception {
        int databaseSizeBeforeUpdate = kiCongBoRepository.findAll().size();

        // Create the KiCongBo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKiCongBoMockMvc.perform(put("/api/ki-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kiCongBo)))
            .andExpect(status().isBadRequest());

        // Validate the KiCongBo in the database
        List<KiCongBo> kiCongBoList = kiCongBoRepository.findAll();
        assertThat(kiCongBoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKiCongBo() throws Exception {
        // Initialize the database
        kiCongBoService.save(kiCongBo);

        int databaseSizeBeforeDelete = kiCongBoRepository.findAll().size();

        // Delete the kiCongBo
        restKiCongBoMockMvc.perform(delete("/api/ki-cong-bos/{id}", kiCongBo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KiCongBo> kiCongBoList = kiCongBoRepository.findAll();
        assertThat(kiCongBoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KiCongBo.class);
        KiCongBo kiCongBo1 = new KiCongBo();
        kiCongBo1.setId(1L);
        KiCongBo kiCongBo2 = new KiCongBo();
        kiCongBo2.setId(kiCongBo1.getId());
        assertThat(kiCongBo1).isEqualTo(kiCongBo2);
        kiCongBo2.setId(2L);
        assertThat(kiCongBo1).isNotEqualTo(kiCongBo2);
        kiCongBo1.setId(null);
        assertThat(kiCongBo1).isNotEqualTo(kiCongBo2);
    }
}
