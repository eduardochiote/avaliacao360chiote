package com.chiote.service;

import com.chiote.domain.Pergunta;
import com.chiote.repository.PerguntaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Pergunta.
 */
@Service
@Transactional
public class PerguntaService {

    private final Logger log = LoggerFactory.getLogger(PerguntaService.class);
    
    @Inject
    private PerguntaRepository perguntaRepository;

    /**
     * Save a pergunta.
     *
     * @param pergunta the entity to save
     * @return the persisted entity
     */
    public Pergunta save(Pergunta pergunta) {
        log.debug("Request to save Pergunta : {}", pergunta);
        Pergunta result = perguntaRepository.save(pergunta);
        return result;
    }

    /**
     *  Get all the perguntas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Pergunta> findAll(Pageable pageable) {
        log.debug("Request to get all Perguntas");
        Page<Pergunta> result = perguntaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pergunta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Pergunta findOne(Long id) {
        log.debug("Request to get Pergunta : {}", id);
        Pergunta pergunta = perguntaRepository.findOne(id);
        return pergunta;
    }

    /**
     *  Delete the  pergunta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pergunta : {}", id);
        perguntaRepository.delete(id);
    }
}
