package pl.grzegorz.web.rest;

import pl.grzegorz.domain.Adres;
import pl.grzegorz.repository.AdresRepository;
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
 * REST controller for managing {@link pl.grzegorz.domain.Adres}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdresResource {

    private final Logger log = LoggerFactory.getLogger(AdresResource.class);

    private static final String ENTITY_NAME = "adres";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresRepository adresRepository;

    public AdresResource(AdresRepository adresRepository) {
        this.adresRepository = adresRepository;
    }

    /**
     * {@code POST  /adres} : Create a new adres.
     *
     * @param adres the adres to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adres, or with status {@code 400 (Bad Request)} if the adres has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adres")
    public ResponseEntity<Adres> createAdres(@RequestBody Adres adres) throws URISyntaxException {
        log.debug("REST request to save Adres : {}", adres);
        if (adres.getId() != null) {
            throw new BadRequestAlertException("A new adres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Adres result = adresRepository.save(adres);
        return ResponseEntity.created(new URI("/api/adres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adres} : Updates an existing adres.
     *
     * @param adres the adres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adres,
     * or with status {@code 400 (Bad Request)} if the adres is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adres")
    public ResponseEntity<Adres> updateAdres(@RequestBody Adres adres) throws URISyntaxException {
        log.debug("REST request to update Adres : {}", adres);
        if (adres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Adres result = adresRepository.save(adres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adres.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /adres} : get all the adres.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adres in body.
     */
    @GetMapping("/adres")
    public List<Adres> getAllAdres() {
        log.debug("REST request to get all Adres");
        return adresRepository.findAll();
    }

    /**
     * {@code GET  /adres/:id} : get the "id" adres.
     *
     * @param id the id of the adres to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adres, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adres/{id}")
    public ResponseEntity<Adres> getAdres(@PathVariable Long id) {
        log.debug("REST request to get Adres : {}", id);
        Optional<Adres> adres = adresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adres);
    }

    /**
     * {@code DELETE  /adres/:id} : delete the "id" adres.
     *
     * @param id the id of the adres to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adres/{id}")
    public ResponseEntity<Void> deleteAdres(@PathVariable Long id) {
        log.debug("REST request to delete Adres : {}", id);
        adresRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
