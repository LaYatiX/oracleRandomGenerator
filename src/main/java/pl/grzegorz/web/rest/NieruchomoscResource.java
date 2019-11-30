package pl.grzegorz.web.rest;

import pl.grzegorz.domain.Nieruchomosc;
import pl.grzegorz.repository.NieruchomoscRepository;
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
 * REST controller for managing {@link pl.grzegorz.domain.Nieruchomosc}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NieruchomoscResource {

    private final Logger log = LoggerFactory.getLogger(NieruchomoscResource.class);

    private static final String ENTITY_NAME = "nieruchomosc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NieruchomoscRepository nieruchomoscRepository;

    public NieruchomoscResource(NieruchomoscRepository nieruchomoscRepository) {
        this.nieruchomoscRepository = nieruchomoscRepository;
    }

    /**
     * {@code POST  /nieruchomoscs} : Create a new nieruchomosc.
     *
     * @param nieruchomosc the nieruchomosc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nieruchomosc, or with status {@code 400 (Bad Request)} if the nieruchomosc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nieruchomoscs")
    public ResponseEntity<Nieruchomosc> createNieruchomosc(@RequestBody Nieruchomosc nieruchomosc) throws URISyntaxException {
        log.debug("REST request to save Nieruchomosc : {}", nieruchomosc);
        if (nieruchomosc.getId() != null) {
            throw new BadRequestAlertException("A new nieruchomosc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nieruchomosc result = nieruchomoscRepository.save(nieruchomosc);
        return ResponseEntity.created(new URI("/api/nieruchomoscs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nieruchomoscs} : Updates an existing nieruchomosc.
     *
     * @param nieruchomosc the nieruchomosc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nieruchomosc,
     * or with status {@code 400 (Bad Request)} if the nieruchomosc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nieruchomosc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nieruchomoscs")
    public ResponseEntity<Nieruchomosc> updateNieruchomosc(@RequestBody Nieruchomosc nieruchomosc) throws URISyntaxException {
        log.debug("REST request to update Nieruchomosc : {}", nieruchomosc);
        if (nieruchomosc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nieruchomosc result = nieruchomoscRepository.save(nieruchomosc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nieruchomosc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nieruchomoscs} : get all the nieruchomoscs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nieruchomoscs in body.
     */
    @GetMapping("/nieruchomoscs")
    public List<Nieruchomosc> getAllNieruchomoscs() {
        log.debug("REST request to get all Nieruchomoscs");
        return nieruchomoscRepository.findAll();
    }

    /**
     * {@code GET  /nieruchomoscs/:id} : get the "id" nieruchomosc.
     *
     * @param id the id of the nieruchomosc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nieruchomosc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nieruchomoscs/{id}")
    public ResponseEntity<Nieruchomosc> getNieruchomosc(@PathVariable Long id) {
        log.debug("REST request to get Nieruchomosc : {}", id);
        Optional<Nieruchomosc> nieruchomosc = nieruchomoscRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nieruchomosc);
    }

    /**
     * {@code DELETE  /nieruchomoscs/:id} : delete the "id" nieruchomosc.
     *
     * @param id the id of the nieruchomosc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nieruchomoscs/{id}")
    public ResponseEntity<Void> deleteNieruchomosc(@PathVariable Long id) {
        log.debug("REST request to delete Nieruchomosc : {}", id);
        nieruchomoscRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
