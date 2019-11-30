package pl.grzegorz.web.rest;

import pl.grzegorz.domain.GodzinyOtwarcia;
import pl.grzegorz.repository.GodzinyOtwarciaRepository;
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
 * REST controller for managing {@link pl.grzegorz.domain.GodzinyOtwarcia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GodzinyOtwarciaResource {

    private final Logger log = LoggerFactory.getLogger(GodzinyOtwarciaResource.class);

    private static final String ENTITY_NAME = "godzinyOtwarcia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GodzinyOtwarciaRepository godzinyOtwarciaRepository;

    public GodzinyOtwarciaResource(GodzinyOtwarciaRepository godzinyOtwarciaRepository) {
        this.godzinyOtwarciaRepository = godzinyOtwarciaRepository;
    }

    /**
     * {@code POST  /godziny-otwarcias} : Create a new godzinyOtwarcia.
     *
     * @param godzinyOtwarcia the godzinyOtwarcia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new godzinyOtwarcia, or with status {@code 400 (Bad Request)} if the godzinyOtwarcia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/godziny-otwarcias")
    public ResponseEntity<GodzinyOtwarcia> createGodzinyOtwarcia(@RequestBody GodzinyOtwarcia godzinyOtwarcia) throws URISyntaxException {
        log.debug("REST request to save GodzinyOtwarcia : {}", godzinyOtwarcia);
        if (godzinyOtwarcia.getId() != null) {
            throw new BadRequestAlertException("A new godzinyOtwarcia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GodzinyOtwarcia result = godzinyOtwarciaRepository.save(godzinyOtwarcia);
        return ResponseEntity.created(new URI("/api/godziny-otwarcias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /godziny-otwarcias} : Updates an existing godzinyOtwarcia.
     *
     * @param godzinyOtwarcia the godzinyOtwarcia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated godzinyOtwarcia,
     * or with status {@code 400 (Bad Request)} if the godzinyOtwarcia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the godzinyOtwarcia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/godziny-otwarcias")
    public ResponseEntity<GodzinyOtwarcia> updateGodzinyOtwarcia(@RequestBody GodzinyOtwarcia godzinyOtwarcia) throws URISyntaxException {
        log.debug("REST request to update GodzinyOtwarcia : {}", godzinyOtwarcia);
        if (godzinyOtwarcia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GodzinyOtwarcia result = godzinyOtwarciaRepository.save(godzinyOtwarcia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, godzinyOtwarcia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /godziny-otwarcias} : get all the godzinyOtwarcias.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of godzinyOtwarcias in body.
     */
    @GetMapping("/godziny-otwarcias")
    public List<GodzinyOtwarcia> getAllGodzinyOtwarcias() {
        log.debug("REST request to get all GodzinyOtwarcias");
        return godzinyOtwarciaRepository.findAll();
    }

    /**
     * {@code GET  /godziny-otwarcias/:id} : get the "id" godzinyOtwarcia.
     *
     * @param id the id of the godzinyOtwarcia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the godzinyOtwarcia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/godziny-otwarcias/{id}")
    public ResponseEntity<GodzinyOtwarcia> getGodzinyOtwarcia(@PathVariable Long id) {
        log.debug("REST request to get GodzinyOtwarcia : {}", id);
        Optional<GodzinyOtwarcia> godzinyOtwarcia = godzinyOtwarciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(godzinyOtwarcia);
    }

    /**
     * {@code DELETE  /godziny-otwarcias/:id} : delete the "id" godzinyOtwarcia.
     *
     * @param id the id of the godzinyOtwarcia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/godziny-otwarcias/{id}")
    public ResponseEntity<Void> deleteGodzinyOtwarcia(@PathVariable Long id) {
        log.debug("REST request to delete GodzinyOtwarcia : {}", id);
        godzinyOtwarciaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
