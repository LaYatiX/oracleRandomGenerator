package pl.grzegorz.web.rest;

import pl.grzegorz.domain.Osoba;
import pl.grzegorz.repository.OsobaRepository;
import pl.grzegorz.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.grzegorz.domain.Osoba}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OsobaResource {

    private final Logger log = LoggerFactory.getLogger(OsobaResource.class);

    private static final String ENTITY_NAME = "osoba";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OsobaRepository osobaRepository;

    public OsobaResource(OsobaRepository osobaRepository) {
        this.osobaRepository = osobaRepository;
    }

    /**
     * {@code POST  /osobas} : Create a new osoba.
     *
     * @param osoba the osoba to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new osoba, or with status {@code 400 (Bad Request)} if the osoba has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/osobas")
    public ResponseEntity<Osoba> createOsoba(@RequestBody Osoba osoba) throws URISyntaxException {
        log.debug("REST request to save Osoba : {}", osoba);
        if (osoba.getId() != null) {
            throw new BadRequestAlertException("A new osoba cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Osoba result = osobaRepository.save(osoba);
        return ResponseEntity.created(new URI("/api/osobas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /osobas} : Updates an existing osoba.
     *
     * @param osoba the osoba to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osoba,
     * or with status {@code 400 (Bad Request)} if the osoba is not valid,
     * or with status {@code 500 (Internal Server Error)} if the osoba couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/osobas")
    public ResponseEntity<Osoba> updateOsoba(@RequestBody Osoba osoba) throws URISyntaxException {
        log.debug("REST request to update Osoba : {}", osoba);
        if (osoba.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Osoba result = osobaRepository.save(osoba);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, osoba.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /osobas} : get all the osobas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of osobas in body.
     */
    @GetMapping("/osobas")
    public List<Osoba> getAllOsobas() {
        log.debug("REST request to get all Osobas");
        return osobaRepository.findAll();
    }

    /**
     * {@code GET  /osobas/:id} : get the "id" osoba.
     *
     * @param id the id of the osoba to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the osoba, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/osobas/{id}")
    public ResponseEntity<Osoba> getOsoba(@PathVariable Long id) {
        log.debug("REST request to get Osoba : {}", id);
        Optional<Osoba> osoba = osobaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(osoba);
    }

    /**
     * {@code DELETE  /osobas/:id} : delete the "id" osoba.
     *
     * @param id the id of the osoba to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/osobas/{id}")
    public ResponseEntity<Void> deleteOsoba(@PathVariable Long id) {
        log.debug("REST request to delete Osoba : {}", id);
        osobaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
