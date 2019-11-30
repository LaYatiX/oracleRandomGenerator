package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.GodzinyOtwarcia;
import pl.grzegorz.repository.GodzinyOtwarciaRepository;
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

/**
 * Integration tests for the {@link GodzinyOtwarciaResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class GodzinyOtwarciaResourceIT {

    private static final String DEFAULT_PONIEDZIALEK = "AAAAAAAAAA";
    private static final String UPDATED_PONIEDZIALEK = "BBBBBBBBBB";

    private static final String DEFAULT_WTOREK = "AAAAAAAAAA";
    private static final String UPDATED_WTOREK = "BBBBBBBBBB";

    private static final String DEFAULT_SRODA = "AAAAAAAAAA";
    private static final String UPDATED_SRODA = "BBBBBBBBBB";

    private static final String DEFAULT_CZWARTEK = "AAAAAAAAAA";
    private static final String UPDATED_CZWARTEK = "BBBBBBBBBB";

    private static final String DEFAULT_PIATEK = "AAAAAAAAAA";
    private static final String UPDATED_PIATEK = "BBBBBBBBBB";

    private static final String DEFAULT_SOBOTA = "AAAAAAAAAA";
    private static final String UPDATED_SOBOTA = "BBBBBBBBBB";

    private static final String DEFAULT_NIEDZIELA = "AAAAAAAAAA";
    private static final String UPDATED_NIEDZIELA = "BBBBBBBBBB";

    @Autowired
    private GodzinyOtwarciaRepository godzinyOtwarciaRepository;

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

    private MockMvc restGodzinyOtwarciaMockMvc;

    private GodzinyOtwarcia godzinyOtwarcia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GodzinyOtwarciaResource godzinyOtwarciaResource = new GodzinyOtwarciaResource(godzinyOtwarciaRepository);
        this.restGodzinyOtwarciaMockMvc = MockMvcBuilders.standaloneSetup(godzinyOtwarciaResource)
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
    public static GodzinyOtwarcia createEntity(EntityManager em) {
        GodzinyOtwarcia godzinyOtwarcia = new GodzinyOtwarcia()
            .poniedzialek(DEFAULT_PONIEDZIALEK)
            .wtorek(DEFAULT_WTOREK)
            .sroda(DEFAULT_SRODA)
            .czwartek(DEFAULT_CZWARTEK)
            .piatek(DEFAULT_PIATEK)
            .sobota(DEFAULT_SOBOTA)
            .niedziela(DEFAULT_NIEDZIELA);
        return godzinyOtwarcia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GodzinyOtwarcia createUpdatedEntity(EntityManager em) {
        GodzinyOtwarcia godzinyOtwarcia = new GodzinyOtwarcia()
            .poniedzialek(UPDATED_PONIEDZIALEK)
            .wtorek(UPDATED_WTOREK)
            .sroda(UPDATED_SRODA)
            .czwartek(UPDATED_CZWARTEK)
            .piatek(UPDATED_PIATEK)
            .sobota(UPDATED_SOBOTA)
            .niedziela(UPDATED_NIEDZIELA);
        return godzinyOtwarcia;
    }

    @BeforeEach
    public void initTest() {
        godzinyOtwarcia = createEntity(em);
    }

    @Test
    @Transactional
    public void createGodzinyOtwarcia() throws Exception {
        int databaseSizeBeforeCreate = godzinyOtwarciaRepository.findAll().size();

        // Create the GodzinyOtwarcia
        restGodzinyOtwarciaMockMvc.perform(post("/api/godziny-otwarcias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godzinyOtwarcia)))
            .andExpect(status().isCreated());

        // Validate the GodzinyOtwarcia in the database
        List<GodzinyOtwarcia> godzinyOtwarciaList = godzinyOtwarciaRepository.findAll();
        assertThat(godzinyOtwarciaList).hasSize(databaseSizeBeforeCreate + 1);
        GodzinyOtwarcia testGodzinyOtwarcia = godzinyOtwarciaList.get(godzinyOtwarciaList.size() - 1);
        assertThat(testGodzinyOtwarcia.getPoniedzialek()).isEqualTo(DEFAULT_PONIEDZIALEK);
        assertThat(testGodzinyOtwarcia.getWtorek()).isEqualTo(DEFAULT_WTOREK);
        assertThat(testGodzinyOtwarcia.getSroda()).isEqualTo(DEFAULT_SRODA);
        assertThat(testGodzinyOtwarcia.getCzwartek()).isEqualTo(DEFAULT_CZWARTEK);
        assertThat(testGodzinyOtwarcia.getPiatek()).isEqualTo(DEFAULT_PIATEK);
        assertThat(testGodzinyOtwarcia.getSobota()).isEqualTo(DEFAULT_SOBOTA);
        assertThat(testGodzinyOtwarcia.getNiedziela()).isEqualTo(DEFAULT_NIEDZIELA);
    }

    @Test
    @Transactional
    public void createGodzinyOtwarciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = godzinyOtwarciaRepository.findAll().size();

        // Create the GodzinyOtwarcia with an existing ID
        godzinyOtwarcia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGodzinyOtwarciaMockMvc.perform(post("/api/godziny-otwarcias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godzinyOtwarcia)))
            .andExpect(status().isBadRequest());

        // Validate the GodzinyOtwarcia in the database
        List<GodzinyOtwarcia> godzinyOtwarciaList = godzinyOtwarciaRepository.findAll();
        assertThat(godzinyOtwarciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGodzinyOtwarcias() throws Exception {
        // Initialize the database
        godzinyOtwarciaRepository.saveAndFlush(godzinyOtwarcia);

        // Get all the godzinyOtwarciaList
        restGodzinyOtwarciaMockMvc.perform(get("/api/godziny-otwarcias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(godzinyOtwarcia.getId().intValue())))
            .andExpect(jsonPath("$.[*].poniedzialek").value(hasItem(DEFAULT_PONIEDZIALEK)))
            .andExpect(jsonPath("$.[*].wtorek").value(hasItem(DEFAULT_WTOREK)))
            .andExpect(jsonPath("$.[*].sroda").value(hasItem(DEFAULT_SRODA)))
            .andExpect(jsonPath("$.[*].czwartek").value(hasItem(DEFAULT_CZWARTEK)))
            .andExpect(jsonPath("$.[*].piatek").value(hasItem(DEFAULT_PIATEK)))
            .andExpect(jsonPath("$.[*].sobota").value(hasItem(DEFAULT_SOBOTA)))
            .andExpect(jsonPath("$.[*].niedziela").value(hasItem(DEFAULT_NIEDZIELA)));
    }
    
    @Test
    @Transactional
    public void getGodzinyOtwarcia() throws Exception {
        // Initialize the database
        godzinyOtwarciaRepository.saveAndFlush(godzinyOtwarcia);

        // Get the godzinyOtwarcia
        restGodzinyOtwarciaMockMvc.perform(get("/api/godziny-otwarcias/{id}", godzinyOtwarcia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(godzinyOtwarcia.getId().intValue()))
            .andExpect(jsonPath("$.poniedzialek").value(DEFAULT_PONIEDZIALEK))
            .andExpect(jsonPath("$.wtorek").value(DEFAULT_WTOREK))
            .andExpect(jsonPath("$.sroda").value(DEFAULT_SRODA))
            .andExpect(jsonPath("$.czwartek").value(DEFAULT_CZWARTEK))
            .andExpect(jsonPath("$.piatek").value(DEFAULT_PIATEK))
            .andExpect(jsonPath("$.sobota").value(DEFAULT_SOBOTA))
            .andExpect(jsonPath("$.niedziela").value(DEFAULT_NIEDZIELA));
    }

    @Test
    @Transactional
    public void getNonExistingGodzinyOtwarcia() throws Exception {
        // Get the godzinyOtwarcia
        restGodzinyOtwarciaMockMvc.perform(get("/api/godziny-otwarcias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGodzinyOtwarcia() throws Exception {
        // Initialize the database
        godzinyOtwarciaRepository.saveAndFlush(godzinyOtwarcia);

        int databaseSizeBeforeUpdate = godzinyOtwarciaRepository.findAll().size();

        // Update the godzinyOtwarcia
        GodzinyOtwarcia updatedGodzinyOtwarcia = godzinyOtwarciaRepository.findById(godzinyOtwarcia.getId()).get();
        // Disconnect from session so that the updates on updatedGodzinyOtwarcia are not directly saved in db
        em.detach(updatedGodzinyOtwarcia);
        updatedGodzinyOtwarcia
            .poniedzialek(UPDATED_PONIEDZIALEK)
            .wtorek(UPDATED_WTOREK)
            .sroda(UPDATED_SRODA)
            .czwartek(UPDATED_CZWARTEK)
            .piatek(UPDATED_PIATEK)
            .sobota(UPDATED_SOBOTA)
            .niedziela(UPDATED_NIEDZIELA);

        restGodzinyOtwarciaMockMvc.perform(put("/api/godziny-otwarcias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGodzinyOtwarcia)))
            .andExpect(status().isOk());

        // Validate the GodzinyOtwarcia in the database
        List<GodzinyOtwarcia> godzinyOtwarciaList = godzinyOtwarciaRepository.findAll();
        assertThat(godzinyOtwarciaList).hasSize(databaseSizeBeforeUpdate);
        GodzinyOtwarcia testGodzinyOtwarcia = godzinyOtwarciaList.get(godzinyOtwarciaList.size() - 1);
        assertThat(testGodzinyOtwarcia.getPoniedzialek()).isEqualTo(UPDATED_PONIEDZIALEK);
        assertThat(testGodzinyOtwarcia.getWtorek()).isEqualTo(UPDATED_WTOREK);
        assertThat(testGodzinyOtwarcia.getSroda()).isEqualTo(UPDATED_SRODA);
        assertThat(testGodzinyOtwarcia.getCzwartek()).isEqualTo(UPDATED_CZWARTEK);
        assertThat(testGodzinyOtwarcia.getPiatek()).isEqualTo(UPDATED_PIATEK);
        assertThat(testGodzinyOtwarcia.getSobota()).isEqualTo(UPDATED_SOBOTA);
        assertThat(testGodzinyOtwarcia.getNiedziela()).isEqualTo(UPDATED_NIEDZIELA);
    }

    @Test
    @Transactional
    public void updateNonExistingGodzinyOtwarcia() throws Exception {
        int databaseSizeBeforeUpdate = godzinyOtwarciaRepository.findAll().size();

        // Create the GodzinyOtwarcia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGodzinyOtwarciaMockMvc.perform(put("/api/godziny-otwarcias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godzinyOtwarcia)))
            .andExpect(status().isBadRequest());

        // Validate the GodzinyOtwarcia in the database
        List<GodzinyOtwarcia> godzinyOtwarciaList = godzinyOtwarciaRepository.findAll();
        assertThat(godzinyOtwarciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGodzinyOtwarcia() throws Exception {
        // Initialize the database
        godzinyOtwarciaRepository.saveAndFlush(godzinyOtwarcia);

        int databaseSizeBeforeDelete = godzinyOtwarciaRepository.findAll().size();

        // Delete the godzinyOtwarcia
        restGodzinyOtwarciaMockMvc.perform(delete("/api/godziny-otwarcias/{id}", godzinyOtwarcia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GodzinyOtwarcia> godzinyOtwarciaList = godzinyOtwarciaRepository.findAll();
        assertThat(godzinyOtwarciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
