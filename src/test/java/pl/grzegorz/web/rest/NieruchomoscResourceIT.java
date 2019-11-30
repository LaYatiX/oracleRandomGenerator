package pl.grzegorz.web.rest;

import pl.grzegorz.OracleApp;
import pl.grzegorz.domain.Nieruchomosc;
import pl.grzegorz.repository.NieruchomoscRepository;
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

import pl.grzegorz.domain.enumeration.TypNieruchomosci;
/**
 * Integration tests for the {@link NieruchomoscResource} REST controller.
 */
@SpringBootTest(classes = OracleApp.class)
public class NieruchomoscResourceIT {

    private static final TypNieruchomosci DEFAULT_TYP = TypNieruchomosci.DOM;
    private static final TypNieruchomosci UPDATED_TYP = TypNieruchomosci.BLOK;

    private static final Integer DEFAULT_ILOSC_MIESZKAN = 1;
    private static final Integer UPDATED_ILOSC_MIESZKAN = 2;

    private static final Integer DEFAULT_ILOSC_MIESZKANCOW = 1;
    private static final Integer UPDATED_ILOSC_MIESZKANCOW = 2;

    @Autowired
    private NieruchomoscRepository nieruchomoscRepository;

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

    private MockMvc restNieruchomoscMockMvc;

    private Nieruchomosc nieruchomosc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NieruchomoscResource nieruchomoscResource = new NieruchomoscResource(nieruchomoscRepository);
        this.restNieruchomoscMockMvc = MockMvcBuilders.standaloneSetup(nieruchomoscResource)
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
    public static Nieruchomosc createEntity(EntityManager em) {
        Nieruchomosc nieruchomosc = new Nieruchomosc()
            .typ(DEFAULT_TYP)
            .iloscMieszkan(DEFAULT_ILOSC_MIESZKAN)
            .iloscMieszkancow(DEFAULT_ILOSC_MIESZKANCOW);
        return nieruchomosc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nieruchomosc createUpdatedEntity(EntityManager em) {
        Nieruchomosc nieruchomosc = new Nieruchomosc()
            .typ(UPDATED_TYP)
            .iloscMieszkan(UPDATED_ILOSC_MIESZKAN)
            .iloscMieszkancow(UPDATED_ILOSC_MIESZKANCOW);
        return nieruchomosc;
    }

    @BeforeEach
    public void initTest() {
        nieruchomosc = createEntity(em);
    }

    @Test
    @Transactional
    public void createNieruchomosc() throws Exception {
        int databaseSizeBeforeCreate = nieruchomoscRepository.findAll().size();

        // Create the Nieruchomosc
        restNieruchomoscMockMvc.perform(post("/api/nieruchomoscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nieruchomosc)))
            .andExpect(status().isCreated());

        // Validate the Nieruchomosc in the database
        List<Nieruchomosc> nieruchomoscList = nieruchomoscRepository.findAll();
        assertThat(nieruchomoscList).hasSize(databaseSizeBeforeCreate + 1);
        Nieruchomosc testNieruchomosc = nieruchomoscList.get(nieruchomoscList.size() - 1);
        assertThat(testNieruchomosc.getTyp()).isEqualTo(DEFAULT_TYP);
        assertThat(testNieruchomosc.getIloscMieszkan()).isEqualTo(DEFAULT_ILOSC_MIESZKAN);
        assertThat(testNieruchomosc.getIloscMieszkancow()).isEqualTo(DEFAULT_ILOSC_MIESZKANCOW);
    }

    @Test
    @Transactional
    public void createNieruchomoscWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nieruchomoscRepository.findAll().size();

        // Create the Nieruchomosc with an existing ID
        nieruchomosc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNieruchomoscMockMvc.perform(post("/api/nieruchomoscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nieruchomosc)))
            .andExpect(status().isBadRequest());

        // Validate the Nieruchomosc in the database
        List<Nieruchomosc> nieruchomoscList = nieruchomoscRepository.findAll();
        assertThat(nieruchomoscList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNieruchomoscs() throws Exception {
        // Initialize the database
        nieruchomoscRepository.saveAndFlush(nieruchomosc);

        // Get all the nieruchomoscList
        restNieruchomoscMockMvc.perform(get("/api/nieruchomoscs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nieruchomosc.getId().intValue())))
            .andExpect(jsonPath("$.[*].typ").value(hasItem(DEFAULT_TYP.toString())))
            .andExpect(jsonPath("$.[*].iloscMieszkan").value(hasItem(DEFAULT_ILOSC_MIESZKAN)))
            .andExpect(jsonPath("$.[*].iloscMieszkancow").value(hasItem(DEFAULT_ILOSC_MIESZKANCOW)));
    }
    
    @Test
    @Transactional
    public void getNieruchomosc() throws Exception {
        // Initialize the database
        nieruchomoscRepository.saveAndFlush(nieruchomosc);

        // Get the nieruchomosc
        restNieruchomoscMockMvc.perform(get("/api/nieruchomoscs/{id}", nieruchomosc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nieruchomosc.getId().intValue()))
            .andExpect(jsonPath("$.typ").value(DEFAULT_TYP.toString()))
            .andExpect(jsonPath("$.iloscMieszkan").value(DEFAULT_ILOSC_MIESZKAN))
            .andExpect(jsonPath("$.iloscMieszkancow").value(DEFAULT_ILOSC_MIESZKANCOW));
    }

    @Test
    @Transactional
    public void getNonExistingNieruchomosc() throws Exception {
        // Get the nieruchomosc
        restNieruchomoscMockMvc.perform(get("/api/nieruchomoscs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNieruchomosc() throws Exception {
        // Initialize the database
        nieruchomoscRepository.saveAndFlush(nieruchomosc);

        int databaseSizeBeforeUpdate = nieruchomoscRepository.findAll().size();

        // Update the nieruchomosc
        Nieruchomosc updatedNieruchomosc = nieruchomoscRepository.findById(nieruchomosc.getId()).get();
        // Disconnect from session so that the updates on updatedNieruchomosc are not directly saved in db
        em.detach(updatedNieruchomosc);
        updatedNieruchomosc
            .typ(UPDATED_TYP)
            .iloscMieszkan(UPDATED_ILOSC_MIESZKAN)
            .iloscMieszkancow(UPDATED_ILOSC_MIESZKANCOW);

        restNieruchomoscMockMvc.perform(put("/api/nieruchomoscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNieruchomosc)))
            .andExpect(status().isOk());

        // Validate the Nieruchomosc in the database
        List<Nieruchomosc> nieruchomoscList = nieruchomoscRepository.findAll();
        assertThat(nieruchomoscList).hasSize(databaseSizeBeforeUpdate);
        Nieruchomosc testNieruchomosc = nieruchomoscList.get(nieruchomoscList.size() - 1);
        assertThat(testNieruchomosc.getTyp()).isEqualTo(UPDATED_TYP);
        assertThat(testNieruchomosc.getIloscMieszkan()).isEqualTo(UPDATED_ILOSC_MIESZKAN);
        assertThat(testNieruchomosc.getIloscMieszkancow()).isEqualTo(UPDATED_ILOSC_MIESZKANCOW);
    }

    @Test
    @Transactional
    public void updateNonExistingNieruchomosc() throws Exception {
        int databaseSizeBeforeUpdate = nieruchomoscRepository.findAll().size();

        // Create the Nieruchomosc

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNieruchomoscMockMvc.perform(put("/api/nieruchomoscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nieruchomosc)))
            .andExpect(status().isBadRequest());

        // Validate the Nieruchomosc in the database
        List<Nieruchomosc> nieruchomoscList = nieruchomoscRepository.findAll();
        assertThat(nieruchomoscList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNieruchomosc() throws Exception {
        // Initialize the database
        nieruchomoscRepository.saveAndFlush(nieruchomosc);

        int databaseSizeBeforeDelete = nieruchomoscRepository.findAll().size();

        // Delete the nieruchomosc
        restNieruchomoscMockMvc.perform(delete("/api/nieruchomoscs/{id}", nieruchomosc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nieruchomosc> nieruchomoscList = nieruchomoscRepository.findAll();
        assertThat(nieruchomoscList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
