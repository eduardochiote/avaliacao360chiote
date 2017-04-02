package com.chiote.service;

import com.chiote.domain.Avaliacao;
import com.chiote.repository.AvaliacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Avaliacao.
 */
@Service
@Transactional
public class AvaliacaoService {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoService.class);
    
    @Inject
    private AvaliacaoRepository avaliacaoRepository;

    /**
     * Save a avaliacao.
     *
     * @param avaliacao the entity to save
     * @return the persisted entity
     */
    public Avaliacao save(Avaliacao avaliacao) {
        log.debug("Request to save Avaliacao : {}", avaliacao);
        Avaliacao result = avaliacaoRepository.save(avaliacao);
        return result;
    }

    /**
     *  Get all the avaliacaos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Avaliacao> findAll(Pageable pageable) {
        log.debug("Request to get all Avaliacaos");
        Page<Avaliacao> result = avaliacaoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one avaliacao by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Avaliacao findOne(Long id) {
        log.debug("Request to get Avaliacao : {}", id);
        Avaliacao avaliacao = avaliacaoRepository.findOne(id);
        return avaliacao;
    }

    /**
     *  Delete the  avaliacao by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Avaliacao : {}", id);
        avaliacaoRepository.delete(id);
    }
}
