package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.Lokalizacja;
import pl.grzegorz.repository.LokalizacjaRepository;
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
 * Integration tests for the {@link LokalizacjaResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class LokalizacjaResourceIT {

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;

    @Autowired
    private LokalizacjaRepository lokalizacjaRepository;

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

    private MockMvc restLokalizacjaMockMvc;

    private Lokalizacja lokalizacja;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LokalizacjaResource lokalizacjaResource = new LokalizacjaResource(lokalizacjaRepository);
        this.restLokalizacjaMockMvc = MockMvcBuilders.standaloneSetup(lokalizacjaResource)
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
    public static Lokalizacja createEntity(EntityManager em) {
        Lokalizacja lokalizacja = new Lokalizacja()
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG);
        return lokalizacja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lokalizacja createUpdatedEntity(EntityManager em) {
        Lokalizacja lokalizacja = new Lokalizacja()
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);
        return lokalizacja;
    }

    @BeforeEach
    public void initTest() {
        lokalizacja = createEntity(em);
    }

    @Test
    @Transactional
    public void createLokalizacja() throws Exception {
        int databaseSizeBeforeCreate = lokalizacjaRepository.findAll().size();

        // Create the Lokalizacja
        restLokalizacjaMockMvc.perform(post("/api/lokalizacjas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lokalizacja)))
            .andExpect(status().isCreated());

        // Validate the Lokalizacja in the database
        List<Lokalizacja> lokalizacjaList = lokalizacjaRepository.findAll();
        assertThat(lokalizacjaList).hasSize(databaseSizeBeforeCreate + 1);
        Lokalizacja testLokalizacja = lokalizacjaList.get(lokalizacjaList.size() - 1);
        assertThat(testLokalizacja.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testLokalizacja.getLng()).isEqualTo(DEFAULT_LNG);
    }

    @Test
    @Transactional
    public void createLokalizacjaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lokalizacjaRepository.findAll().size();

        // Create the Lokalizacja with an existing ID
        lokalizacja.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLokalizacjaMockMvc.perform(post("/api/lokalizacjas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lokalizacja)))
            .andExpect(status().isBadRequest());

        // Validate the Lokalizacja in the database
        List<Lokalizacja> lokalizacjaList = lokalizacjaRepository.findAll();
        assertThat(lokalizacjaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLokalizacjas() throws Exception {
        // Initialize the database
        lokalizacjaRepository.saveAndFlush(lokalizacja);

        // Get all the lokalizacjaList
        restLokalizacjaMockMvc.perform(get("/api/lokalizacjas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lokalizacja.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLokalizacja() throws Exception {
        // Initialize the database
        lokalizacjaRepository.saveAndFlush(lokalizacja);

        // Get the lokalizacja
        restLokalizacjaMockMvc.perform(get("/api/lokalizacjas/{id}", lokalizacja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lokalizacja.getId().intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLokalizacja() throws Exception {
        // Get the lokalizacja
        restLokalizacjaMockMvc.perform(get("/api/lokalizacjas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLokalizacja() throws Exception {
        // Initialize the database
        lokalizacjaRepository.saveAndFlush(lokalizacja);

        int databaseSizeBeforeUpdate = lokalizacjaRepository.findAll().size();

        // Update the lokalizacja
        Lokalizacja updatedLokalizacja = lokalizacjaRepository.findById(lokalizacja.getId()).get();
        // Disconnect from session so that the updates on updatedLokalizacja are not directly saved in db
        em.detach(updatedLokalizacja);
        updatedLokalizacja
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);

        restLokalizacjaMockMvc.perform(put("/api/lokalizacjas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLokalizacja)))
            .andExpect(status().isOk());

        // Validate the Lokalizacja in the database
        List<Lokalizacja> lokalizacjaList = lokalizacjaRepository.findAll();
        assertThat(lokalizacjaList).hasSize(databaseSizeBeforeUpdate);
        Lokalizacja testLokalizacja = lokalizacjaList.get(lokalizacjaList.size() - 1);
        assertThat(testLokalizacja.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testLokalizacja.getLng()).isEqualTo(UPDATED_LNG);
    }

    @Test
    @Transactional
    public void updateNonExistingLokalizacja() throws Exception {
        int databaseSizeBeforeUpdate = lokalizacjaRepository.findAll().size();

        // Create the Lokalizacja

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLokalizacjaMockMvc.perform(put("/api/lokalizacjas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lokalizacja)))
            .andExpect(status().isBadRequest());

        // Validate the Lokalizacja in the database
        List<Lokalizacja> lokalizacjaList = lokalizacjaRepository.findAll();
        assertThat(lokalizacjaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLokalizacja() throws Exception {
        // Initialize the database
        lokalizacjaRepository.saveAndFlush(lokalizacja);

        int databaseSizeBeforeDelete = lokalizacjaRepository.findAll().size();

        // Delete the lokalizacja
        restLokalizacjaMockMvc.perform(delete("/api/lokalizacjas/{id}", lokalizacja.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lokalizacja> lokalizacjaList = lokalizacjaRepository.findAll();
        assertThat(lokalizacjaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
