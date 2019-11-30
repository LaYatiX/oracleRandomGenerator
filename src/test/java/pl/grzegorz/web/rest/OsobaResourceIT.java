package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.Osoba;
import pl.grzegorz.repository.OsobaRepository;
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
 * Integration tests for the {@link OsobaResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class OsobaResourceIT {

    private static final String DEFAULT_IMIE = "AAAAAAAAAA";
    private static final String UPDATED_IMIE = "BBBBBBBBBB";

    private static final String DEFAULT_NAZWISKO = "AAAAAAAAAA";
    private static final String UPDATED_NAZWISKO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_PESEL = "AAAAAAAAAA";
    private static final String UPDATED_PESEL = "BBBBBBBBBB";

    @Autowired
    private OsobaRepository osobaRepository;

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

    private MockMvc restOsobaMockMvc;

    private Osoba osoba;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OsobaResource osobaResource = new OsobaResource(osobaRepository);
        this.restOsobaMockMvc = MockMvcBuilders.standaloneSetup(osobaResource)
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
    public static Osoba createEntity(EntityManager em) {
        Osoba osoba = new Osoba()
            .imie(DEFAULT_IMIE)
            .nazwisko(DEFAULT_NAZWISKO)
            .telefon(DEFAULT_TELEFON)
            .pesel(DEFAULT_PESEL);
        return osoba;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Osoba createUpdatedEntity(EntityManager em) {
        Osoba osoba = new Osoba()
            .imie(UPDATED_IMIE)
            .nazwisko(UPDATED_NAZWISKO)
            .telefon(UPDATED_TELEFON)
            .pesel(UPDATED_PESEL);
        return osoba;
    }

    @BeforeEach
    public void initTest() {
        osoba = createEntity(em);
    }

    @Test
    @Transactional
    public void createOsoba() throws Exception {
        int databaseSizeBeforeCreate = osobaRepository.findAll().size();

        // Create the Osoba
        restOsobaMockMvc.perform(post("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osoba)))
            .andExpect(status().isCreated());

        // Validate the Osoba in the database
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeCreate + 1);
        Osoba testOsoba = osobaList.get(osobaList.size() - 1);
        assertThat(testOsoba.getImie()).isEqualTo(DEFAULT_IMIE);
        assertThat(testOsoba.getNazwisko()).isEqualTo(DEFAULT_NAZWISKO);
        assertThat(testOsoba.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testOsoba.getPesel()).isEqualTo(DEFAULT_PESEL);
    }

    @Test
    @Transactional
    public void createOsobaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = osobaRepository.findAll().size();

        // Create the Osoba with an existing ID
        osoba.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOsobaMockMvc.perform(post("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osoba)))
            .andExpect(status().isBadRequest());

        // Validate the Osoba in the database
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOsobas() throws Exception {
        // Initialize the database
        osobaRepository.saveAndFlush(osoba);

        // Get all the osobaList
        restOsobaMockMvc.perform(get("/api/osobas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(osoba.getId().intValue())))
            .andExpect(jsonPath("$.[*].imie").value(hasItem(DEFAULT_IMIE)))
            .andExpect(jsonPath("$.[*].nazwisko").value(hasItem(DEFAULT_NAZWISKO)))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON)))
            .andExpect(jsonPath("$.[*].pesel").value(hasItem(DEFAULT_PESEL)));
    }
    
    @Test
    @Transactional
    public void getOsoba() throws Exception {
        // Initialize the database
        osobaRepository.saveAndFlush(osoba);

        // Get the osoba
        restOsobaMockMvc.perform(get("/api/osobas/{id}", osoba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(osoba.getId().intValue()))
            .andExpect(jsonPath("$.imie").value(DEFAULT_IMIE))
            .andExpect(jsonPath("$.nazwisko").value(DEFAULT_NAZWISKO))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON))
            .andExpect(jsonPath("$.pesel").value(DEFAULT_PESEL));
    }

    @Test
    @Transactional
    public void getNonExistingOsoba() throws Exception {
        // Get the osoba
        restOsobaMockMvc.perform(get("/api/osobas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOsoba() throws Exception {
        // Initialize the database
        osobaRepository.saveAndFlush(osoba);

        int databaseSizeBeforeUpdate = osobaRepository.findAll().size();

        // Update the osoba
        Osoba updatedOsoba = osobaRepository.findById(osoba.getId()).get();
        // Disconnect from session so that the updates on updatedOsoba are not directly saved in db
        em.detach(updatedOsoba);
        updatedOsoba
            .imie(UPDATED_IMIE)
            .nazwisko(UPDATED_NAZWISKO)
            .telefon(UPDATED_TELEFON)
            .pesel(UPDATED_PESEL);

        restOsobaMockMvc.perform(put("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOsoba)))
            .andExpect(status().isOk());

        // Validate the Osoba in the database
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeUpdate);
        Osoba testOsoba = osobaList.get(osobaList.size() - 1);
        assertThat(testOsoba.getImie()).isEqualTo(UPDATED_IMIE);
        assertThat(testOsoba.getNazwisko()).isEqualTo(UPDATED_NAZWISKO);
        assertThat(testOsoba.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testOsoba.getPesel()).isEqualTo(UPDATED_PESEL);
    }

    @Test
    @Transactional
    public void updateNonExistingOsoba() throws Exception {
        int databaseSizeBeforeUpdate = osobaRepository.findAll().size();

        // Create the Osoba

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsobaMockMvc.perform(put("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osoba)))
            .andExpect(status().isBadRequest());

        // Validate the Osoba in the database
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOsoba() throws Exception {
        // Initialize the database
        osobaRepository.saveAndFlush(osoba);

        int databaseSizeBeforeDelete = osobaRepository.findAll().size();

        // Delete the osoba
        restOsobaMockMvc.perform(delete("/api/osobas/{id}", osoba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
