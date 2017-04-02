package com.chiote.service;

import com.chiote.domain.Resposta;
import com.chiote.repository.RespostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Resposta.
 */
@Service
@Transactional
public class RespostaService {

    private final Logger log = LoggerFactory.getLogger(RespostaService.class);
    
    @Inject
    private RespostaRepository respostaRepository;

    /**
     * Save a resposta.
     *
     * @param resposta the entity to save
     * @return the persisted entity
     */
    public Resposta save(Resposta resposta) {
        log.debug("Request to save Resposta : {}", resposta);
        Resposta result = respostaRepository.save(resposta);
        return result;
    }

    /**
     *  Get all the respostas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Resposta> findAll(Pageable pageable) {
        log.debug("Request to get all Respostas");
        Page<Resposta> result = respostaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one resposta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Resposta findOne(Long id) {
        log.debug("Request to get Resposta : {}", id);
        Resposta resposta = respostaRepository.findOne(id);
        return resposta;
    }

    /**
     *  Delete the  resposta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Resposta : {}", id);
        respostaRepository.delete(id);
    }
}
