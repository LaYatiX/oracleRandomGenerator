package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.Mieszkanie;
import pl.grzegorz.repository.MieszkanieRepository;
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
 * Integration tests for the {@link MieszkanieResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class MieszkanieResourceIT {

    private static final Integer DEFAULT_METRAZ = 1;
    private static final Integer UPDATED_METRAZ = 2;

    private static final Boolean DEFAULT_CZY_LAZIENKA = false;
    private static final Boolean UPDATED_CZY_LAZIENKA = true;

    private static final Boolean DEFAULT_CZY_KUCHNIA = false;
    private static final Boolean UPDATED_CZY_KUCHNIA = true;

    private static final Boolean DEFAULT_CZY_WYPOSAZONE = false;
    private static final Boolean UPDATED_CZY_WYPOSAZONE = true;

    @Autowired
    private MieszkanieRepository mieszkanieRepository;

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

    private MockMvc restMieszkanieMockMvc;

    private Mieszkanie mieszkanie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MieszkanieResource mieszkanieResource = new MieszkanieResource(mieszkanieRepository);
        this.restMieszkanieMockMvc = MockMvcBuilders.standaloneSetup(mieszkanieResource)
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
    public static Mieszkanie createEntity(EntityManager em) {
        Mieszkanie mieszkanie = new Mieszkanie()
            .metraz(DEFAULT_METRAZ)
            .czyLazienka(DEFAULT_CZY_LAZIENKA)
            .czyKuchnia(DEFAULT_CZY_KUCHNIA)
            .czyWyposazone(DEFAULT_CZY_WYPOSAZONE);
        return mieszkanie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mieszkanie createUpdatedEntity(EntityManager em) {
        Mieszkanie mieszkanie = new Mieszkanie()
            .metraz(UPDATED_METRAZ)
            .czyLazienka(UPDATED_CZY_LAZIENKA)
            .czyKuchnia(UPDATED_CZY_KUCHNIA)
            .czyWyposazone(UPDATED_CZY_WYPOSAZONE);
        return mieszkanie;
    }

    @BeforeEach
    public void initTest() {
        mieszkanie = createEntity(em);
    }

    @Test
    @Transactional
    public void createMieszkanie() throws Exception {
        int databaseSizeBeforeCreate = mieszkanieRepository.findAll().size();

        // Create the Mieszkanie
        restMieszkanieMockMvc.perform(post("/api/mieszkanies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mieszkanie)))
            .andExpect(status().isCreated());

        // Validate the Mieszkanie in the database
        List<Mieszkanie> mieszkanieList = mieszkanieRepository.findAll();
        assertThat(mieszkanieList).hasSize(databaseSizeBeforeCreate + 1);
        Mieszkanie testMieszkanie = mieszkanieList.get(mieszkanieList.size() - 1);
        assertThat(testMieszkanie.getMetraz()).isEqualTo(DEFAULT_METRAZ);
        assertThat(testMieszkanie.isCzyLazienka()).isEqualTo(DEFAULT_CZY_LAZIENKA);
        assertThat(testMieszkanie.isCzyKuchnia()).isEqualTo(DEFAULT_CZY_KUCHNIA);
        assertThat(testMieszkanie.isCzyWyposazone()).isEqualTo(DEFAULT_CZY_WYPOSAZONE);
    }

    @Test
    @Transactional
    public void createMieszkanieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mieszkanieRepository.findAll().size();

        // Create the Mieszkanie with an existing ID
        mieszkanie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMieszkanieMockMvc.perform(post("/api/mieszkanies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mieszkanie)))
            .andExpect(status().isBadRequest());

        // Validate the Mieszkanie in the database
        List<Mieszkanie> mieszkanieList = mieszkanieRepository.findAll();
        assertThat(mieszkanieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMieszkanies() throws Exception {
        // Initialize the database
        mieszkanieRepository.saveAndFlush(mieszkanie);

        // Get all the mieszkanieList
        restMieszkanieMockMvc.perform(get("/api/mieszkanies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mieszkanie.getId().intValue())))
            .andExpect(jsonPath("$.[*].metraz").value(hasItem(DEFAULT_METRAZ)))
            .andExpect(jsonPath("$.[*].czyLazienka").value(hasItem(DEFAULT_CZY_LAZIENKA.booleanValue())))
            .andExpect(jsonPath("$.[*].czyKuchnia").value(hasItem(DEFAULT_CZY_KUCHNIA.booleanValue())))
            .andExpect(jsonPath("$.[*].czyWyposazone").value(hasItem(DEFAULT_CZY_WYPOSAZONE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMieszkanie() throws Exception {
        // Initialize the database
        mieszkanieRepository.saveAndFlush(mieszkanie);

        // Get the mieszkanie
        restMieszkanieMockMvc.perform(get("/api/mieszkanies/{id}", mieszkanie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mieszkanie.getId().intValue()))
            .andExpect(jsonPath("$.metraz").value(DEFAULT_METRAZ))
            .andExpect(jsonPath("$.czyLazienka").value(DEFAULT_CZY_LAZIENKA.booleanValue()))
            .andExpect(jsonPath("$.czyKuchnia").value(DEFAULT_CZY_KUCHNIA.booleanValue()))
            .andExpect(jsonPath("$.czyWyposazone").value(DEFAULT_CZY_WYPOSAZONE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMieszkanie() throws Exception {
        // Get the mieszkanie
        restMieszkanieMockMvc.perform(get("/api/mieszkanies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMieszkanie() throws Exception {
        // Initialize the database
        mieszkanieRepository.saveAndFlush(mieszkanie);

        int databaseSizeBeforeUpdate = mieszkanieRepository.findAll().size();

        // Update the mieszkanie
        Mieszkanie updatedMieszkanie = mieszkanieRepository.findById(mieszkanie.getId()).get();
        // Disconnect from session so that the updates on updatedMieszkanie are not directly saved in db
        em.detach(updatedMieszkanie);
        updatedMieszkanie
            .metraz(UPDATED_METRAZ)
            .czyLazienka(UPDATED_CZY_LAZIENKA)
            .czyKuchnia(UPDATED_CZY_KUCHNIA)
            .czyWyposazone(UPDATED_CZY_WYPOSAZONE);

        restMieszkanieMockMvc.perform(put("/api/mieszkanies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMieszkanie)))
            .andExpect(status().isOk());

        // Validate the Mieszkanie in the database
        List<Mieszkanie> mieszkanieList = mieszkanieRepository.findAll();
        assertThat(mieszkanieList).hasSize(databaseSizeBeforeUpdate);
        Mieszkanie testMieszkanie = mieszkanieList.get(mieszkanieList.size() - 1);
        assertThat(testMieszkanie.getMetraz()).isEqualTo(UPDATED_METRAZ);
        assertThat(testMieszkanie.isCzyLazienka()).isEqualTo(UPDATED_CZY_LAZIENKA);
        assertThat(testMieszkanie.isCzyKuchnia()).isEqualTo(UPDATED_CZY_KUCHNIA);
        assertThat(testMieszkanie.isCzyWyposazone()).isEqualTo(UPDATED_CZY_WYPOSAZONE);
    }

    @Test
    @Transactional
    public void updateNonExistingMieszkanie() throws Exception {
        int databaseSizeBeforeUpdate = mieszkanieRepository.findAll().size();

        // Create the Mieszkanie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMieszkanieMockMvc.perform(put("/api/mieszkanies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mieszkanie)))
            .andExpect(status().isBadRequest());

        // Validate the Mieszkanie in the database
        List<Mieszkanie> mieszkanieList = mieszkanieRepository.findAll();
        assertThat(mieszkanieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMieszkanie() throws Exception {
        // Initialize the database
        mieszkanieRepository.saveAndFlush(mieszkanie);

        int databaseSizeBeforeDelete = mieszkanieRepository.findAll().size();

        // Delete the mieszkanie
        restMieszkanieMockMvc.perform(delete("/api/mieszkanies/{id}", mieszkanie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mieszkanie> mieszkanieList = mieszkanieRepository.findAll();
        assertThat(mieszkanieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
