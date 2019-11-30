package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.Transakcja;
import pl.grzegorz.repository.TransakcjaRepository;
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
 * Integration tests for the {@link TransakcjaResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class TransakcjaResourceIT {

    private static final Double DEFAULT_NETTOO = 1D;
    private static final Double UPDATED_NETTOO = 2D;

    private static final Double DEFAULT_BRUTTON = 1D;
    private static final Double UPDATED_BRUTTON = 2D;

    private static final Integer DEFAULT_VAT = 1;
    private static final Integer UPDATED_VAT = 2;

    @Autowired
    private TransakcjaRepository transakcjaRepository;

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

    private MockMvc restTransakcjaMockMvc;

    private Transakcja transakcja;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransakcjaResource transakcjaResource = new TransakcjaResource(transakcjaRepository);
        this.restTransakcjaMockMvc = MockMvcBuilders.standaloneSetup(transakcjaResource)
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
    public static Transakcja createEntity(EntityManager em) {
        Transakcja transakcja = new Transakcja()
            .nettoo(DEFAULT_NETTOO)
            .brutton(DEFAULT_BRUTTON)
            .vat(DEFAULT_VAT);
        return transakcja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transakcja createUpdatedEntity(EntityManager em) {
        Transakcja transakcja = new Transakcja()
            .nettoo(UPDATED_NETTOO)
            .brutton(UPDATED_BRUTTON)
            .vat(UPDATED_VAT);
        return transakcja;
    }

    @BeforeEach
    public void initTest() {
        transakcja = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransakcja() throws Exception {
        int databaseSizeBeforeCreate = transakcjaRepository.findAll().size();

        // Create the Transakcja
        restTransakcjaMockMvc.perform(post("/api/transakcjas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transakcja)))
            .andExpect(status().isCreated());

        // Validate the Transakcja in the database
        List<Transakcja> transakcjaList = transakcjaRepository.findAll();
        assertThat(transakcjaList).hasSize(databaseSizeBeforeCreate + 1);
        Transakcja testTransakcja = transakcjaList.get(transakcjaList.size() - 1);
        assertThat(testTransakcja.getNettoo()).isEqualTo(DEFAULT_NETTOO);
        assertThat(testTransakcja.getBrutton()).isEqualTo(DEFAULT_BRUTTON);
        assertThat(testTransakcja.getVat()).isEqualTo(DEFAULT_VAT);
    }

    @Test
    @Transactional
    public void createTransakcjaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transakcjaRepository.findAll().size();

        // Create the Transakcja with an existing ID
        transakcja.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransakcjaMockMvc.perform(post("/api/transakcjas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transakcja)))
            .andExpect(status().isBadRequest());

        // Validate the Transakcja in the database
        List<Transakcja> transakcjaList = transakcjaRepository.findAll();
        assertThat(transakcjaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransakcjas() throws Exception {
        // Initialize the database
        transakcjaRepository.saveAndFlush(transakcja);

        // Get all the transakcjaList
        restTransakcjaMockMvc.perform(get("/api/transakcjas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transakcja.getId().intValue())))
            .andExpect(jsonPath("$.[*].nettoo").value(hasItem(DEFAULT_NETTOO.doubleValue())))
            .andExpect(jsonPath("$.[*].brutton").value(hasItem(DEFAULT_BRUTTON.doubleValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT)));
    }
    
    @Test
    @Transactional
    public void getTransakcja() throws Exception {
        // Initialize the database
        transakcjaRepository.saveAndFlush(transakcja);

        // Get the transakcja
        restTransakcjaMockMvc.perform(get("/api/transakcjas/{id}", transakcja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transakcja.getId().intValue()))
            .andExpect(jsonPath("$.nettoo").value(DEFAULT_NETTOO.doubleValue()))
            .andExpect(jsonPath("$.brutton").value(DEFAULT_BRUTTON.doubleValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT));
    }

    @Test
    @Transactional
    public void getNonExistingTransakcja() throws Exception {
        // Get the transakcja
        restTransakcjaMockMvc.perform(get("/api/transakcjas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransakcja() throws Exception {
        // Initialize the database
        transakcjaRepository.saveAndFlush(transakcja);

        int databaseSizeBeforeUpdate = transakcjaRepository.findAll().size();

        // Update the transakcja
        Transakcja updatedTransakcja = transakcjaRepository.findById(transakcja.getId()).get();
        // Disconnect from session so that the updates on updatedTransakcja are not directly saved in db
        em.detach(updatedTransakcja);
        updatedTransakcja
            .nettoo(UPDATED_NETTOO)
            .brutton(UPDATED_BRUTTON)
            .vat(UPDATED_VAT);

        restTransakcjaMockMvc.perform(put("/api/transakcjas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransakcja)))
            .andExpect(status().isOk());

        // Validate the Transakcja in the database
        List<Transakcja> transakcjaList = transakcjaRepository.findAll();
        assertThat(transakcjaList).hasSize(databaseSizeBeforeUpdate);
        Transakcja testTransakcja = transakcjaList.get(transakcjaList.size() - 1);
        assertThat(testTransakcja.getNettoo()).isEqualTo(UPDATED_NETTOO);
        assertThat(testTransakcja.getBrutton()).isEqualTo(UPDATED_BRUTTON);
        assertThat(testTransakcja.getVat()).isEqualTo(UPDATED_VAT);
    }

    @Test
    @Transactional
    public void updateNonExistingTransakcja() throws Exception {
        int databaseSizeBeforeUpdate = transakcjaRepository.findAll().size();

        // Create the Transakcja

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransakcjaMockMvc.perform(put("/api/transakcjas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transakcja)))
            .andExpect(status().isBadRequest());

        // Validate the Transakcja in the database
        List<Transakcja> transakcjaList = transakcjaRepository.findAll();
        assertThat(transakcjaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransakcja() throws Exception {
        // Initialize the database
        transakcjaRepository.saveAndFlush(transakcja);

        int databaseSizeBeforeDelete = transakcjaRepository.findAll().size();

        // Delete the transakcja
        restTransakcjaMockMvc.perform(delete("/api/transakcjas/{id}", transakcja.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transakcja> transakcjaList = transakcjaRepository.findAll();
        assertThat(transakcjaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
