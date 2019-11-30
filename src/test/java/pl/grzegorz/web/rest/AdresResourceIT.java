package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.Adres;
import pl.grzegorz.repository.AdresRepository;
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
 * Integration tests for the {@link AdresResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class AdresResourceIT {

    private static final String DEFAULT_MIASTO = "AAAAAAAAAA";
    private static final String UPDATED_MIASTO = "BBBBBBBBBB";

    private static final String DEFAULT_ULICA = "AAAAAAAAAA";
    private static final String UPDATED_ULICA = "BBBBBBBBBB";

    private static final String DEFAULT_NR_DOMU = "AAAAAAAAAA";
    private static final String UPDATED_NR_DOMU = "BBBBBBBBBB";

    private static final String DEFAULT_NR_LOKALU = "AAAAAAAAAA";
    private static final String UPDATED_NR_LOKALU = "BBBBBBBBBB";

    private static final String DEFAULT_WOJEWODZWTWO = "AAAAAAAAAA";
    private static final String UPDATED_WOJEWODZWTWO = "BBBBBBBBBB";

    private static final String DEFAULT_POWIAT = "AAAAAAAAAA";
    private static final String UPDATED_POWIAT = "BBBBBBBBBB";

    private static final String DEFAULT_GMINA = "AAAAAAAAAA";
    private static final String UPDATED_GMINA = "BBBBBBBBBB";

    private static final String DEFAULT_KOD_POCZTOWY = "AAAAAAAAAA";
    private static final String UPDATED_KOD_POCZTOWY = "BBBBBBBBBB";

    private static final String DEFAULT_KRAJ = "AAAAAAAAAA";
    private static final String UPDATED_KRAJ = "BBBBBBBBBB";

    @Autowired
    private AdresRepository adresRepository;

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

    private MockMvc restAdresMockMvc;

    private Adres adres;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdresResource adresResource = new AdresResource(adresRepository);
        this.restAdresMockMvc = MockMvcBuilders.standaloneSetup(adresResource)
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
    public static Adres createEntity(EntityManager em) {
        Adres adres = new Adres()
            .miasto(DEFAULT_MIASTO)
            .ulica(DEFAULT_ULICA)
            .nrDomu(DEFAULT_NR_DOMU)
            .nrLokalu(DEFAULT_NR_LOKALU)
            .wojewodzwtwo(DEFAULT_WOJEWODZWTWO)
            .powiat(DEFAULT_POWIAT)
            .gmina(DEFAULT_GMINA)
            .kodPocztowy(DEFAULT_KOD_POCZTOWY)
            .kraj(DEFAULT_KRAJ);
        return adres;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adres createUpdatedEntity(EntityManager em) {
        Adres adres = new Adres()
            .miasto(UPDATED_MIASTO)
            .ulica(UPDATED_ULICA)
            .nrDomu(UPDATED_NR_DOMU)
            .nrLokalu(UPDATED_NR_LOKALU)
            .wojewodzwtwo(UPDATED_WOJEWODZWTWO)
            .powiat(UPDATED_POWIAT)
            .gmina(UPDATED_GMINA)
            .kodPocztowy(UPDATED_KOD_POCZTOWY)
            .kraj(UPDATED_KRAJ);
        return adres;
    }

    @BeforeEach
    public void initTest() {
        adres = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdres() throws Exception {
        int databaseSizeBeforeCreate = adresRepository.findAll().size();

        // Create the Adres
        restAdresMockMvc.perform(post("/api/adres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adres)))
            .andExpect(status().isCreated());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeCreate + 1);
        Adres testAdres = adresList.get(adresList.size() - 1);
        assertThat(testAdres.getMiasto()).isEqualTo(DEFAULT_MIASTO);
        assertThat(testAdres.getUlica()).isEqualTo(DEFAULT_ULICA);
        assertThat(testAdres.getNrDomu()).isEqualTo(DEFAULT_NR_DOMU);
        assertThat(testAdres.getNrLokalu()).isEqualTo(DEFAULT_NR_LOKALU);
        assertThat(testAdres.getWojewodzwtwo()).isEqualTo(DEFAULT_WOJEWODZWTWO);
        assertThat(testAdres.getPowiat()).isEqualTo(DEFAULT_POWIAT);
        assertThat(testAdres.getGmina()).isEqualTo(DEFAULT_GMINA);
        assertThat(testAdres.getKodPocztowy()).isEqualTo(DEFAULT_KOD_POCZTOWY);
        assertThat(testAdres.getKraj()).isEqualTo(DEFAULT_KRAJ);
    }

    @Test
    @Transactional
    public void createAdresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adresRepository.findAll().size();

        // Create the Adres with an existing ID
        adres.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresMockMvc.perform(post("/api/adres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adres)))
            .andExpect(status().isBadRequest());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        // Get all the adresList
        restAdresMockMvc.perform(get("/api/adres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adres.getId().intValue())))
            .andExpect(jsonPath("$.[*].miasto").value(hasItem(DEFAULT_MIASTO)))
            .andExpect(jsonPath("$.[*].ulica").value(hasItem(DEFAULT_ULICA)))
            .andExpect(jsonPath("$.[*].nrDomu").value(hasItem(DEFAULT_NR_DOMU)))
            .andExpect(jsonPath("$.[*].nrLokalu").value(hasItem(DEFAULT_NR_LOKALU)))
            .andExpect(jsonPath("$.[*].wojewodzwtwo").value(hasItem(DEFAULT_WOJEWODZWTWO)))
            .andExpect(jsonPath("$.[*].powiat").value(hasItem(DEFAULT_POWIAT)))
            .andExpect(jsonPath("$.[*].gmina").value(hasItem(DEFAULT_GMINA)))
            .andExpect(jsonPath("$.[*].kodPocztowy").value(hasItem(DEFAULT_KOD_POCZTOWY)))
            .andExpect(jsonPath("$.[*].kraj").value(hasItem(DEFAULT_KRAJ)));
    }
    
    @Test
    @Transactional
    public void getAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        // Get the adres
        restAdresMockMvc.perform(get("/api/adres/{id}", adres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adres.getId().intValue()))
            .andExpect(jsonPath("$.miasto").value(DEFAULT_MIASTO))
            .andExpect(jsonPath("$.ulica").value(DEFAULT_ULICA))
            .andExpect(jsonPath("$.nrDomu").value(DEFAULT_NR_DOMU))
            .andExpect(jsonPath("$.nrLokalu").value(DEFAULT_NR_LOKALU))
            .andExpect(jsonPath("$.wojewodzwtwo").value(DEFAULT_WOJEWODZWTWO))
            .andExpect(jsonPath("$.powiat").value(DEFAULT_POWIAT))
            .andExpect(jsonPath("$.gmina").value(DEFAULT_GMINA))
            .andExpect(jsonPath("$.kodPocztowy").value(DEFAULT_KOD_POCZTOWY))
            .andExpect(jsonPath("$.kraj").value(DEFAULT_KRAJ));
    }

    @Test
    @Transactional
    public void getNonExistingAdres() throws Exception {
        // Get the adres
        restAdresMockMvc.perform(get("/api/adres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        int databaseSizeBeforeUpdate = adresRepository.findAll().size();

        // Update the adres
        Adres updatedAdres = adresRepository.findById(adres.getId()).get();
        // Disconnect from session so that the updates on updatedAdres are not directly saved in db
        em.detach(updatedAdres);
        updatedAdres
            .miasto(UPDATED_MIASTO)
            .ulica(UPDATED_ULICA)
            .nrDomu(UPDATED_NR_DOMU)
            .nrLokalu(UPDATED_NR_LOKALU)
            .wojewodzwtwo(UPDATED_WOJEWODZWTWO)
            .powiat(UPDATED_POWIAT)
            .gmina(UPDATED_GMINA)
            .kodPocztowy(UPDATED_KOD_POCZTOWY)
            .kraj(UPDATED_KRAJ);

        restAdresMockMvc.perform(put("/api/adres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdres)))
            .andExpect(status().isOk());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
        Adres testAdres = adresList.get(adresList.size() - 1);
        assertThat(testAdres.getMiasto()).isEqualTo(UPDATED_MIASTO);
        assertThat(testAdres.getUlica()).isEqualTo(UPDATED_ULICA);
        assertThat(testAdres.getNrDomu()).isEqualTo(UPDATED_NR_DOMU);
        assertThat(testAdres.getNrLokalu()).isEqualTo(UPDATED_NR_LOKALU);
        assertThat(testAdres.getWojewodzwtwo()).isEqualTo(UPDATED_WOJEWODZWTWO);
        assertThat(testAdres.getPowiat()).isEqualTo(UPDATED_POWIAT);
        assertThat(testAdres.getGmina()).isEqualTo(UPDATED_GMINA);
        assertThat(testAdres.getKodPocztowy()).isEqualTo(UPDATED_KOD_POCZTOWY);
        assertThat(testAdres.getKraj()).isEqualTo(UPDATED_KRAJ);
    }

    @Test
    @Transactional
    public void updateNonExistingAdres() throws Exception {
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();

        // Create the Adres

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresMockMvc.perform(put("/api/adres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adres)))
            .andExpect(status().isBadRequest());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        int databaseSizeBeforeDelete = adresRepository.findAll().size();

        // Delete the adres
        restAdresMockMvc.perform(delete("/api/adres/{id}", adres.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
