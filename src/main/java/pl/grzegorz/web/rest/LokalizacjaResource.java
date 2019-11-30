package pl.grzegorz.web.rest;

import pl.grzegorz.domain.Lokalizacja;
import pl.grzegorz.repository.LokalizacjaRepository;
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
 * REST controller for managing {@link pl.grzegorz.domain.Lokalizacja}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LokalizacjaResource {

    private final Logger log = LoggerFactory.getLogger(LokalizacjaResource.class);

    private static final String ENTITY_NAME = "lokalizacja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LokalizacjaRepository lokalizacjaRepository;

    public LokalizacjaResource(LokalizacjaRepository lokalizacjaRepository) {
        this.lokalizacjaRepository = lokalizacjaRepository;
    }

    /**
     * {@code POST  /lokalizacjas} : Create a new lokalizacja.
     *
     * @param lokalizacja the lokalizacja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lokalizacja, or with status {@code 400 (Bad Request)} if the lokalizacja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lokalizacjas")
    public ResponseEntity<Lokalizacja> createLokalizacja(@RequestBody Lokalizacja lokalizacja) throws URISyntaxException {
        log.debug("REST request to save Lokalizacja : {}", lokalizacja);
        if (lokalizacja.getId() != null) {
            throw new BadRequestAlertException("A new lokalizacja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lokalizacja result = lokalizacjaRepository.save(lokalizacja);
        return ResponseEntity.created(new URI("/api/lokalizacjas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lokalizacjas} : Updates an existing lokalizacja.
     *
     * @param lokalizacja the lokalizacja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lokalizacja,
     * or with status {@code 400 (Bad Request)} if the lokalizacja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lokalizacja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lokalizacjas")
    public ResponseEntity<Lokalizacja> updateLokalizacja(@RequestBody Lokalizacja lokalizacja) throws URISyntaxException {
        log.debug("REST request to update Lokalizacja : {}", lokalizacja);
        if (lokalizacja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lokalizacja result = lokalizacjaRepository.save(lokalizacja);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lokalizacja.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lokalizacjas} : get all the lokalizacjas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lokalizacjas in body.
     */
    @GetMapping("/lokalizacjas")
    public List<Lokalizacja> getAllLokalizacjas() {
        log.debug("REST request to get all Lokalizacjas");
        return lokalizacjaRepository.findAll();
    }

    /**
     * {@code GET  /lokalizacjas/:id} : get the "id" lokalizacja.
     *
     * @param id the id of the lokalizacja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lokalizacja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lokalizacjas/{id}")
    public ResponseEntity<Lokalizacja> getLokalizacja(@PathVariable Long id) {
        log.debug("REST request to get Lokalizacja : {}", id);
        Optional<Lokalizacja> lokalizacja = lokalizacjaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lokalizacja);
    }

    /**
     * {@code DELETE  /lokalizacjas/:id} : delete the "id" lokalizacja.
     *
     * @param id the id of the lokalizacja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lokalizacjas/{id}")
    public ResponseEntity<Void> deleteLokalizacja(@PathVariable Long id) {
        log.debug("REST request to delete Lokalizacja : {}", id);
        lokalizacjaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
