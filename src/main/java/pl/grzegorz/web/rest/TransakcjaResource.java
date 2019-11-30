package pl.grzegorz.web.rest;

import pl.grzegorz.domain.Transakcja;
import pl.grzegorz.repository.TransakcjaRepository;
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
 * REST controller for managing {@link pl.grzegorz.domain.Transakcja}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransakcjaResource {

    private final Logger log = LoggerFactory.getLogger(TransakcjaResource.class);

    private static final String ENTITY_NAME = "transakcja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransakcjaRepository transakcjaRepository;

    public TransakcjaResource(TransakcjaRepository transakcjaRepository) {
        this.transakcjaRepository = transakcjaRepository;
    }

    /**
     * {@code POST  /transakcjas} : Create a new transakcja.
     *
     * @param transakcja the transakcja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transakcja, or with status {@code 400 (Bad Request)} if the transakcja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transakcjas")
    public ResponseEntity<Transakcja> createTransakcja(@RequestBody Transakcja transakcja) throws URISyntaxException {
        log.debug("REST request to save Transakcja : {}", transakcja);
        if (transakcja.getId() != null) {
            throw new BadRequestAlertException("A new transakcja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transakcja result = transakcjaRepository.save(transakcja);
        return ResponseEntity.created(new URI("/api/transakcjas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transakcjas} : Updates an existing transakcja.
     *
     * @param transakcja the transakcja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transakcja,
     * or with status {@code 400 (Bad Request)} if the transakcja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transakcja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transakcjas")
    public ResponseEntity<Transakcja> updateTransakcja(@RequestBody Transakcja transakcja) throws URISyntaxException {
        log.debug("REST request to update Transakcja : {}", transakcja);
        if (transakcja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transakcja result = transakcjaRepository.save(transakcja);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transakcja.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transakcjas} : get all the transakcjas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transakcjas in body.
     */
    @GetMapping("/transakcjas")
    public List<Transakcja> getAllTransakcjas() {
        log.debug("REST request to get all Transakcjas");
        return transakcjaRepository.findAll();
    }

    /**
     * {@code GET  /transakcjas/:id} : get the "id" transakcja.
     *
     * @param id the id of the transakcja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transakcja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transakcjas/{id}")
    public ResponseEntity<Transakcja> getTransakcja(@PathVariable Long id) {
        log.debug("REST request to get Transakcja : {}", id);
        Optional<Transakcja> transakcja = transakcjaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transakcja);
    }

    /**
     * {@code DELETE  /transakcjas/:id} : delete the "id" transakcja.
     *
     * @param id the id of the transakcja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transakcjas/{id}")
    public ResponseEntity<Void> deleteTransakcja(@PathVariable Long id) {
        log.debug("REST request to delete Transakcja : {}", id);
        transakcjaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
