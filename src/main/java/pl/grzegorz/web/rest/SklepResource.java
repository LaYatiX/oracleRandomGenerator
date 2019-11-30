package pl.grzegorz.web.rest;

import pl.grzegorz.domain.Sklep;
import pl.grzegorz.repository.SklepRepository;
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
 * REST controller for managing {@link pl.grzegorz.domain.Sklep}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SklepResource {

    private final Logger log = LoggerFactory.getLogger(SklepResource.class);

    private static final String ENTITY_NAME = "sklep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SklepRepository sklepRepository;

    public SklepResource(SklepRepository sklepRepository) {
        this.sklepRepository = sklepRepository;
    }

    /**
     * {@code POST  /skleps} : Create a new sklep.
     *
     * @param sklep the sklep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sklep, or with status {@code 400 (Bad Request)} if the sklep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skleps")
    public ResponseEntity<Sklep> createSklep(@RequestBody Sklep sklep) throws URISyntaxException {
        log.debug("REST request to save Sklep : {}", sklep);
        if (sklep.getId() != null) {
            throw new BadRequestAlertException("A new sklep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sklep result = sklepRepository.save(sklep);
        return ResponseEntity.created(new URI("/api/skleps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skleps} : Updates an existing sklep.
     *
     * @param sklep the sklep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sklep,
     * or with status {@code 400 (Bad Request)} if the sklep is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sklep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skleps")
    public ResponseEntity<Sklep> updateSklep(@RequestBody Sklep sklep) throws URISyntaxException {
        log.debug("REST request to update Sklep : {}", sklep);
        if (sklep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sklep result = sklepRepository.save(sklep);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sklep.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skleps} : get all the skleps.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skleps in body.
     */
    @GetMapping("/skleps")
    public List<Sklep> getAllSkleps() {
        log.debug("REST request to get all Skleps");
        return sklepRepository.findAll();
    }

    /**
     * {@code GET  /skleps/:id} : get the "id" sklep.
     *
     * @param id the id of the sklep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sklep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skleps/{id}")
    public ResponseEntity<Sklep> getSklep(@PathVariable Long id) {
        log.debug("REST request to get Sklep : {}", id);
        Optional<Sklep> sklep = sklepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sklep);
    }

    /**
     * {@code DELETE  /skleps/:id} : delete the "id" sklep.
     *
     * @param id the id of the sklep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skleps/{id}")
    public ResponseEntity<Void> deleteSklep(@PathVariable Long id) {
        log.debug("REST request to delete Sklep : {}", id);
        sklepRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
