package pl.grzegorz.web.rest;

import pl.grzegorz.domain.Mieszkanie;
import pl.grzegorz.repository.MieszkanieRepository;
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
 * REST controller for managing {@link pl.grzegorz.domain.Mieszkanie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MieszkanieResource {

    private final Logger log = LoggerFactory.getLogger(MieszkanieResource.class);

    private static final String ENTITY_NAME = "mieszkanie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MieszkanieRepository mieszkanieRepository;

    public MieszkanieResource(MieszkanieRepository mieszkanieRepository) {
        this.mieszkanieRepository = mieszkanieRepository;
    }

    /**
     * {@code POST  /mieszkanies} : Create a new mieszkanie.
     *
     * @param mieszkanie the mieszkanie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mieszkanie, or with status {@code 400 (Bad Request)} if the mieszkanie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mieszkanies")
    public ResponseEntity<Mieszkanie> createMieszkanie(@RequestBody Mieszkanie mieszkanie) throws URISyntaxException {
        log.debug("REST request to save Mieszkanie : {}", mieszkanie);
        if (mieszkanie.getId() != null) {
            throw new BadRequestAlertException("A new mieszkanie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mieszkanie result = mieszkanieRepository.save(mieszkanie);
        return ResponseEntity.created(new URI("/api/mieszkanies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mieszkanies} : Updates an existing mieszkanie.
     *
     * @param mieszkanie the mieszkanie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mieszkanie,
     * or with status {@code 400 (Bad Request)} if the mieszkanie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mieszkanie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mieszkanies")
    public ResponseEntity<Mieszkanie> updateMieszkanie(@RequestBody Mieszkanie mieszkanie) throws URISyntaxException {
        log.debug("REST request to update Mieszkanie : {}", mieszkanie);
        if (mieszkanie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mieszkanie result = mieszkanieRepository.save(mieszkanie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mieszkanie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mieszkanies} : get all the mieszkanies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mieszkanies in body.
     */
    @GetMapping("/mieszkanies")
    public List<Mieszkanie> getAllMieszkanies() {
        log.debug("REST request to get all Mieszkanies");
        return mieszkanieRepository.findAll();
    }

    /**
     * {@code GET  /mieszkanies/:id} : get the "id" mieszkanie.
     *
     * @param id the id of the mieszkanie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mieszkanie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mieszkanies/{id}")
    public ResponseEntity<Mieszkanie> getMieszkanie(@PathVariable Long id) {
        log.debug("REST request to get Mieszkanie : {}", id);
        Optional<Mieszkanie> mieszkanie = mieszkanieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mieszkanie);
    }

    /**
     * {@code DELETE  /mieszkanies/:id} : delete the "id" mieszkanie.
     *
     * @param id the id of the mieszkanie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mieszkanies/{id}")
    public ResponseEntity<Void> deleteMieszkanie(@PathVariable Long id) {
        log.debug("REST request to delete Mieszkanie : {}", id);
        mieszkanieRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
