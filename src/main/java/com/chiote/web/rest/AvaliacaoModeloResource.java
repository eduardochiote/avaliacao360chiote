package com.chiote.web.rest;

import com.chiote.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import com.chiote.domain.AvaliacaoModelo;
import com.chiote.service.AvaliacaoModeloService;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AvaliacaoModelo.
 */
@RestController
@RequestMapping("/api")
public class AvaliacaoModeloResource {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoModeloResource.class);

    @Inject
    private AvaliacaoModeloService avaliacaoModeloService;

    /**
     * POST  /avaliacao-modelos : Create a new avaliacaoModelo.
     *
     * @param avaliacaoModelo the avaliacaoModelo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avaliacaoModelo, or with status 400 (Bad Request) if the avaliacaoModelo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avaliacao-modelos")
    @Timed
    @Secured(AuthoritiesConstants.TEAM_LEADER)
    public ResponseEntity<AvaliacaoModelo> createAvaliacaoModelo(@Valid @RequestBody AvaliacaoModelo avaliacaoModelo) throws URISyntaxException {
        log.debug("REST request to save AvaliacaoModelo : {}", avaliacaoModelo);
        if (avaliacaoModelo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("avaliacaoModelo", "idexists", "A new avaliacaoModelo cannot already have an ID")).body(null);
        }
        AvaliacaoModelo result = avaliacaoModeloService.save(avaliacaoModelo);
        return ResponseEntity.created(new URI("/api/avaliacao-modelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("avaliacaoModelo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avaliacao-modelos : Updates an existing avaliacaoModelo.
     *
     * @param avaliacaoModelo the avaliacaoModelo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avaliacaoModelo,
     * or with status 400 (Bad Request) if the avaliacaoModelo is not valid,
     * or with status 500 (Internal Server Error) if the avaliacaoModelo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avaliacao-modelos")
    @Timed
    @Secured(AuthoritiesConstants.TEAM_LEADER)
    public ResponseEntity<AvaliacaoModelo> updateAvaliacaoModelo(@Valid @RequestBody AvaliacaoModelo avaliacaoModelo) throws URISyntaxException {
        log.debug("REST request to update AvaliacaoModelo : {}", avaliacaoModelo);
        if (avaliacaoModelo.getId() == null) {
            return createAvaliacaoModelo(avaliacaoModelo);
        }
        AvaliacaoModelo result = avaliacaoModeloService.save(avaliacaoModelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("avaliacaoModelo", avaliacaoModelo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avaliacao-modelos : get all the avaliacaoModelos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of avaliacaoModelos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/avaliacao-modelos")
    @Timed
    @Secured(AuthoritiesConstants.TEAM_LEADER)
    public ResponseEntity<List<AvaliacaoModelo>> getAllAvaliacaoModelos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of AvaliacaoModelos");
        Page<AvaliacaoModelo> page = avaliacaoModeloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/avaliacao-modelos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /avaliacao-modelos/:id : get the "id" avaliacaoModelo.
     *
     * @param id the id of the avaliacaoModelo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avaliacaoModelo, or with status 404 (Not Found)
     */
    @GetMapping("/avaliacao-modelos/{id}")
    @Timed
    @Secured(AuthoritiesConstants.TEAM_LEADER)
    public ResponseEntity<AvaliacaoModelo> getAvaliacaoModelo(@PathVariable Long id) {
        log.debug("REST request to get AvaliacaoModelo : {}", id);
        AvaliacaoModelo avaliacaoModelo = avaliacaoModeloService.findOne(id);
        return Optional.ofNullable(avaliacaoModelo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /avaliacao-modelos/:id : delete the "id" avaliacaoModelo.
     *
     * @param id the id of the avaliacaoModelo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avaliacao-modelos/{id}")
    @Timed
    @Secured(AuthoritiesConstants.TEAM_LEADER)
    public ResponseEntity<Void> deleteAvaliacaoModelo(@PathVariable Long id) {
        log.debug("REST request to delete AvaliacaoModelo : {}", id);
        avaliacaoModeloService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("avaliacaoModelo", id.toString())).build();
    }

}
