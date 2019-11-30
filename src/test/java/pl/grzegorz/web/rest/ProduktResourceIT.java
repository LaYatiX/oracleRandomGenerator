package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.Produkt;
import pl.grzegorz.repository.ProduktRepository;
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
 * Integration tests for the {@link ProduktResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class ProduktResourceIT {

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final Double DEFAULT_WARTOSC = 1D;
    private static final Double UPDATED_WARTOSC = 2D;

    private static final Integer DEFAULT_VAT = 1;
    private static final Integer UPDATED_VAT = 2;

    @Autowired
    private ProduktRepository produktRepository;

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

    private MockMvc restProduktMockMvc;

    private Produkt produkt;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduktResource produktResource = new ProduktResource(produktRepository);
        this.restProduktMockMvc = MockMvcBuilders.standaloneSetup(produktResource)
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
    public static Produkt createEntity(EntityManager em) {
        Produkt produkt = new Produkt()
            .nazwa(DEFAULT_NAZWA)
            .wartosc(DEFAULT_WARTOSC)
            .vat(DEFAULT_VAT);
        return produkt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produkt createUpdatedEntity(EntityManager em) {
        Produkt produkt = new Produkt()
            .nazwa(UPDATED_NAZWA)
            .wartosc(UPDATED_WARTOSC)
            .vat(UPDATED_VAT);
        return produkt;
    }

    @BeforeEach
    public void initTest() {
        produkt = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdukt() throws Exception {
        int databaseSizeBeforeCreate = produktRepository.findAll().size();

        // Create the Produkt
        restProduktMockMvc.perform(post("/api/produkts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produkt)))
            .andExpect(status().isCreated());

        // Validate the Produkt in the database
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeCreate + 1);
        Produkt testProdukt = produktList.get(produktList.size() - 1);
        assertThat(testProdukt.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testProdukt.getWartosc()).isEqualTo(DEFAULT_WARTOSC);
        assertThat(testProdukt.getVat()).isEqualTo(DEFAULT_VAT);
    }

    @Test
    @Transactional
    public void createProduktWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produktRepository.findAll().size();

        // Create the Produkt with an existing ID
        produkt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduktMockMvc.perform(post("/api/produkts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produkt)))
            .andExpect(status().isBadRequest());

        // Validate the Produkt in the database
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProdukts() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

        // Get all the produktList
        restProduktMockMvc.perform(get("/api/produkts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produkt.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)))
            .andExpect(jsonPath("$.[*].wartosc").value(hasItem(DEFAULT_WARTOSC.doubleValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT)));
    }
    
    @Test
    @Transactional
    public void getProdukt() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

        // Get the produkt
        restProduktMockMvc.perform(get("/api/produkts/{id}", produkt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produkt.getId().intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA))
            .andExpect(jsonPath("$.wartosc").value(DEFAULT_WARTOSC.doubleValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT));
    }

    @Test
    @Transactional
    public void getNonExistingProdukt() throws Exception {
        // Get the produkt
        restProduktMockMvc.perform(get("/api/produkts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdukt() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

        int databaseSizeBeforeUpdate = produktRepository.findAll().size();

        // Update the produkt
        Produkt updatedProdukt = produktRepository.findById(produkt.getId()).get();
        // Disconnect from session so that the updates on updatedProdukt are not directly saved in db
        em.detach(updatedProdukt);
        updatedProdukt
            .nazwa(UPDATED_NAZWA)
            .wartosc(UPDATED_WARTOSC)
            .vat(UPDATED_VAT);

        restProduktMockMvc.perform(put("/api/produkts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdukt)))
            .andExpect(status().isOk());

        // Validate the Produkt in the database
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeUpdate);
        Produkt testProdukt = produktList.get(produktList.size() - 1);
        assertThat(testProdukt.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testProdukt.getWartosc()).isEqualTo(UPDATED_WARTOSC);
        assertThat(testProdukt.getVat()).isEqualTo(UPDATED_VAT);
    }

    @Test
    @Transactional
    public void updateNonExistingProdukt() throws Exception {
        int databaseSizeBeforeUpdate = produktRepository.findAll().size();

        // Create the Produkt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduktMockMvc.perform(put("/api/produkts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produkt)))
            .andExpect(status().isBadRequest());

        // Validate the Produkt in the database
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProdukt() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

        int databaseSizeBeforeDelete = produktRepository.findAll().size();

        // Delete the produkt
        restProduktMockMvc.perform(delete("/api/produkts/{id}", produkt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
