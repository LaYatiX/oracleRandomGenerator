package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.Sklep;
import pl.grzegorz.repository.SklepRepository;
import pl.grzegorz.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static pl.grzegorz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pl.grzegorz.domain.enumeration.TypSklepu;
/**
 * Integration tests for the {@link SklepResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class SklepResourceIT {

    private static final TypSklepu DEFAULT_TYP = TypSklepu.SPOZYWCZY;
    private static final TypSklepu UPDATED_TYP = TypSklepu.PRZEMYSLOWY;

    @Autowired
    private SklepRepository sklepRepository;

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

    private MockMvc restSklepMockMvc;

    private Sklep sklep;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SklepResource sklepResource = new SklepResource(sklepRepository);
        this.restSklepMockMvc = MockMvcBuilders.standaloneSetup(sklepResource)
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
    public static Sklep createEntity(EntityManager em) {
        Sklep sklep = new Sklep()
            .typ(DEFAULT_TYP);
        return sklep;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sklep createUpdatedEntity(EntityManager em) {
        Sklep sklep = new Sklep()
            .typ(UPDATED_TYP);
        return sklep;
    }

    @BeforeEach
    public void initTest() {
        sklep = createEntity(em);
    }

    @Test
    @Transactional
    public void createSklep() throws Exception {
        int databaseSizeBeforeCreate = sklepRepository.findAll().size();

        // Create the Sklep
        restSklepMockMvc.perform(post("/api/skleps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sklep)))
            .andExpect(status().isCreated());

        // Validate the Sklep in the database
        List<Sklep> sklepList = sklepRepository.findAll();
        assertThat(sklepList).hasSize(databaseSizeBeforeCreate + 1);
        Sklep testSklep = sklepList.get(sklepList.size() - 1);
        assertThat(testSklep.getTyp()).isEqualTo(DEFAULT_TYP);
    }

    @Test
    @Transactional
    public void createSklepWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sklepRepository.findAll().size();

        // Create the Sklep with an existing ID
        sklep.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSklepMockMvc.perform(post("/api/skleps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sklep)))
            .andExpect(status().isBadRequest());

        // Validate the Sklep in the database
        List<Sklep> sklepList = sklepRepository.findAll();
        assertThat(sklepList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkleps() throws Exception {
        // Initialize the database
        sklepRepository.saveAndFlush(sklep);

        // Get all the sklepList
        restSklepMockMvc.perform(get("/api/skleps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sklep.getId().intValue())))
            .andExpect(jsonPath("$.[*].typ").value(hasItem(DEFAULT_TYP.toString())));
    }
    
    @Test
    @Transactional
    public void getSklep() throws Exception {
        // Initialize the database
        sklepRepository.saveAndFlush(sklep);

        // Get the sklep
        restSklepMockMvc.perform(get("/api/skleps/{id}", sklep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sklep.getId().intValue()))
            .andExpect(jsonPath("$.typ").value(DEFAULT_TYP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSklep() throws Exception {
        // Get the sklep
        restSklepMockMvc.perform(get("/api/skleps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSklep() throws Exception {
        // Initialize the database
        sklepRepository.saveAndFlush(sklep);

        int databaseSizeBeforeUpdate = sklepRepository.findAll().size();

        // Update the sklep
        Sklep updatedSklep = sklepRepository.findById(sklep.getId()).get();
        // Disconnect from session so that the updates on updatedSklep are not directly saved in db
        em.detach(updatedSklep);
        updatedSklep
            .typ(UPDATED_TYP);

        restSklepMockMvc.perform(put("/api/skleps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSklep)))
            .andExpect(status().isOk());

        // Validate the Sklep in the database
        List<Sklep> sklepList = sklepRepository.findAll();
        assertThat(sklepList).hasSize(databaseSizeBeforeUpdate);
        Sklep testSklep = sklepList.get(sklepList.size() - 1);
        assertThat(testSklep.getTyp()).isEqualTo(UPDATED_TYP);
    }

    @Test
    @Transactional
    public void updateNonExistingSklep() throws Exception {
        int databaseSizeBeforeUpdate = sklepRepository.findAll().size();

        // Create the Sklep

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSklepMockMvc.perform(put("/api/skleps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sklep)))
            .andExpect(status().isBadRequest());

        // Validate the Sklep in the database
        List<Sklep> sklepList = sklepRepository.findAll();
        assertThat(sklepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSklep() throws Exception {
        // Initialize the database
        sklepRepository.saveAndFlush(sklep);

        int databaseSizeBeforeDelete = sklepRepository.findAll().size();

        // Delete the sklep
        restSklepMockMvc.perform(delete("/api/skleps/{id}", sklep.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sklep> sklepList = sklepRepository.findAll();
        assertThat(sklepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
