package com.chiote.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chiote.domain.AvaliacaoControle;
import com.chiote.service.AvaliacaoControleService;
import com.chiote.web.rest.util.HeaderUtil;
import com.chiote.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AvaliacaoControle.
 */
@RestController
@RequestMapping("/api")
public class AvaliacaoControleResource {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoControleResource.class);
        
    @Inject
    private AvaliacaoControleService avaliacaoControleService;

    /**
     * POST  /avaliacao-controles : Create a new avaliacaoControle.
     *
     * @param avaliacaoControle the avaliacaoControle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avaliacaoControle, or with status 400 (Bad Request) if the avaliacaoControle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avaliacao-controles")
    @Timed
    public ResponseEntity<AvaliacaoControle> createAvaliacaoControle(@Valid @RequestBody AvaliacaoControle avaliacaoControle) throws URISyntaxException {
        log.debug("REST request to save AvaliacaoControle : {}", avaliacaoControle);
        if (avaliacaoControle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("avaliacaoControle", "idexists", "A new avaliacaoControle cannot already have an ID")).body(null);
        }
        AvaliacaoControle result = avaliacaoControleService.save(avaliacaoControle);
        return ResponseEntity.created(new URI("/api/avaliacao-controles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("avaliacaoControle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avaliacao-controles : Updates an existing avaliacaoControle.
     *
     * @param avaliacaoControle the avaliacaoControle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avaliacaoControle,
     * or with status 400 (Bad Request) if the avaliacaoControle is not valid,
     * or with status 500 (Internal Server Error) if the avaliacaoControle couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avaliacao-controles")
    @Timed
    public ResponseEntity<AvaliacaoControle> updateAvaliacaoControle(@Valid @RequestBody AvaliacaoControle avaliacaoControle) throws URISyntaxException {
        log.debug("REST request to update AvaliacaoControle : {}", avaliacaoControle);
        if (avaliacaoControle.getId() == null) {
            return createAvaliacaoControle(avaliacaoControle);
        }
        AvaliacaoControle result = avaliacaoControleService.save(avaliacaoControle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("avaliacaoControle", avaliacaoControle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avaliacao-controles : get all the avaliacaoControles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of avaliacaoControles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/avaliacao-controles")
    @Timed
    public ResponseEntity<List<AvaliacaoControle>> getAllAvaliacaoControles(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of AvaliacaoControles");
        Page<AvaliacaoControle> page = avaliacaoControleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/avaliacao-controles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /avaliacao-controles/:id : get the "id" avaliacaoControle.
     *
     * @param id the id of the avaliacaoControle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avaliacaoControle, or with status 404 (Not Found)
     */
    @GetMapping("/avaliacao-controles/{id}")
    @Timed
    public ResponseEntity<AvaliacaoControle> getAvaliacaoControle(@PathVariable Long id) {
        log.debug("REST request to get AvaliacaoControle : {}", id);
        AvaliacaoControle avaliacaoControle = avaliacaoControleService.findOne(id);
        return Optional.ofNullable(avaliacaoControle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /avaliacao-controles/:id : delete the "id" avaliacaoControle.
     *
     * @param id the id of the avaliacaoControle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avaliacao-controles/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvaliacaoControle(@PathVariable Long id) {
        log.debug("REST request to delete AvaliacaoControle : {}", id);
        avaliacaoControleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("avaliacaoControle", id.toString())).build();
    }

}
