package com.chiote.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chiote.domain.Pergunta;
import com.chiote.service.PerguntaService;
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
 * REST controller for managing Pergunta.
 */
@RestController
@RequestMapping("/api")
public class PerguntaResource {

    private final Logger log = LoggerFactory.getLogger(PerguntaResource.class);
        
    @Inject
    private PerguntaService perguntaService;

    /**
     * POST  /perguntas : Create a new pergunta.
     *
     * @param pergunta the pergunta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pergunta, or with status 400 (Bad Request) if the pergunta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/perguntas")
    @Timed
    public ResponseEntity<Pergunta> createPergunta(@Valid @RequestBody Pergunta pergunta) throws URISyntaxException {
        log.debug("REST request to save Pergunta : {}", pergunta);
        if (pergunta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pergunta", "idexists", "A new pergunta cannot already have an ID")).body(null);
        }
        Pergunta result = perguntaService.save(pergunta);
        return ResponseEntity.created(new URI("/api/perguntas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pergunta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /perguntas : Updates an existing pergunta.
     *
     * @param pergunta the pergunta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pergunta,
     * or with status 400 (Bad Request) if the pergunta is not valid,
     * or with status 500 (Internal Server Error) if the pergunta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/perguntas")
    @Timed
    public ResponseEntity<Pergunta> updatePergunta(@Valid @RequestBody Pergunta pergunta) throws URISyntaxException {
        log.debug("REST request to update Pergunta : {}", pergunta);
        if (pergunta.getId() == null) {
            return createPergunta(pergunta);
        }
        Pergunta result = perguntaService.save(pergunta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pergunta", pergunta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /perguntas : get all the perguntas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of perguntas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/perguntas")
    @Timed
    public ResponseEntity<List<Pergunta>> getAllPerguntas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Perguntas");
        Page<Pergunta> page = perguntaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/perguntas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /perguntas/:id : get the "id" pergunta.
     *
     * @param id the id of the pergunta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pergunta, or with status 404 (Not Found)
     */
    @GetMapping("/perguntas/{id}")
    @Timed
    public ResponseEntity<Pergunta> getPergunta(@PathVariable Long id) {
        log.debug("REST request to get Pergunta : {}", id);
        Pergunta pergunta = perguntaService.findOne(id);
        return Optional.ofNullable(pergunta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /perguntas/:id : delete the "id" pergunta.
     *
     * @param id the id of the pergunta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/perguntas/{id}")
    @Timed
    public ResponseEntity<Void> deletePergunta(@PathVariable Long id) {
        log.debug("REST request to delete Pergunta : {}", id);
        perguntaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pergunta", id.toString())).build();
    }

}
