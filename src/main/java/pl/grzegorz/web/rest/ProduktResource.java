package pl.grzegorz.web.rest;

import pl.grzegorz.domain.Produkt;
import pl.grzegorz.repository.ProduktRepository;
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
 * REST controller for managing {@link pl.grzegorz.domain.Produkt}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProduktResource {

    private final Logger log = LoggerFactory.getLogger(ProduktResource.class);

    private static final String ENTITY_NAME = "produkt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduktRepository produktRepository;

    public ProduktResource(ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }

    /**
     * {@code POST  /produkts} : Create a new produkt.
     *
     * @param produkt the produkt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produkt, or with status {@code 400 (Bad Request)} if the produkt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produkts")
    public ResponseEntity<Produkt> createProdukt(@RequestBody Produkt produkt) throws URISyntaxException {
        log.debug("REST request to save Produkt : {}", produkt);
        if (produkt.getId() != null) {
            throw new BadRequestAlertException("A new produkt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Produkt result = produktRepository.save(produkt);
        return ResponseEntity.created(new URI("/api/produkts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produkts} : Updates an existing produkt.
     *
     * @param produkt the produkt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produkt,
     * or with status {@code 400 (Bad Request)} if the produkt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produkt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produkts")
    public ResponseEntity<Produkt> updateProdukt(@RequestBody Produkt produkt) throws URISyntaxException {
        log.debug("REST request to update Produkt : {}", produkt);
        if (produkt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Produkt result = produktRepository.save(produkt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produkt.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produkts} : get all the produkts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produkts in body.
     */
    @GetMapping("/produkts")
    public List<Produkt> getAllProdukts() {
        log.debug("REST request to get all Produkts");
        return produktRepository.findAll();
    }

    /**
     * {@code GET  /produkts/:id} : get the "id" produkt.
     *
     * @param id the id of the produkt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produkt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produkts/{id}")
    public ResponseEntity<Produkt> getProdukt(@PathVariable Long id) {
        log.debug("REST request to get Produkt : {}", id);
        Optional<Produkt> produkt = produktRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(produkt);
    }

    /**
     * {@code DELETE  /produkts/:id} : delete the "id" produkt.
     *
     * @param id the id of the produkt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produkts/{id}")
    public ResponseEntity<Void> deleteProdukt(@PathVariable Long id) {
        log.debug("REST request to delete Produkt : {}", id);
        produktRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
