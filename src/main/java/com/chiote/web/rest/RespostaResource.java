package com.chiote.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chiote.domain.Resposta;
import com.chiote.service.RespostaService;
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
 * REST controller for managing Resposta.
 */
@RestController
@RequestMapping("/api")
public class RespostaResource {

    private final Logger log = LoggerFactory.getLogger(RespostaResource.class);
        
    @Inject
    private RespostaService respostaService;

    /**
     * POST  /respostas : Create a new resposta.
     *
     * @param resposta the resposta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resposta, or with status 400 (Bad Request) if the resposta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/respostas")
    @Timed
    public ResponseEntity<Resposta> createResposta(@Valid @RequestBody Resposta resposta) throws URISyntaxException {
        log.debug("REST request to save Resposta : {}", resposta);
        if (resposta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resposta", "idexists", "A new resposta cannot already have an ID")).body(null);
        }
        Resposta result = respostaService.save(resposta);
        return ResponseEntity.created(new URI("/api/respostas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resposta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /respostas : Updates an existing resposta.
     *
     * @param resposta the resposta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resposta,
     * or with status 400 (Bad Request) if the resposta is not valid,
     * or with status 500 (Internal Server Error) if the resposta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/respostas")
    @Timed
    public ResponseEntity<Resposta> updateResposta(@Valid @RequestBody Resposta resposta) throws URISyntaxException {
        log.debug("REST request to update Resposta : {}", resposta);
        if (resposta.getId() == null) {
            return createResposta(resposta);
        }
        Resposta result = respostaService.save(resposta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resposta", resposta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /respostas : get all the respostas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of respostas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/respostas")
    @Timed
    public ResponseEntity<List<Resposta>> getAllRespostas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Respostas");
        Page<Resposta> page = respostaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/respostas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /respostas/:id : get the "id" resposta.
     *
     * @param id the id of the resposta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resposta, or with status 404 (Not Found)
     */
    @GetMapping("/respostas/{id}")
    @Timed
    public ResponseEntity<Resposta> getResposta(@PathVariable Long id) {
        log.debug("REST request to get Resposta : {}", id);
        Resposta resposta = respostaService.findOne(id);
        return Optional.ofNullable(resposta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /respostas/:id : delete the "id" resposta.
     *
     * @param id the id of the resposta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/respostas/{id}")
    @Timed
    public ResponseEntity<Void> deleteResposta(@PathVariable Long id) {
        log.debug("REST request to delete Resposta : {}", id);
        respostaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resposta", id.toString())).build();
    }

}
