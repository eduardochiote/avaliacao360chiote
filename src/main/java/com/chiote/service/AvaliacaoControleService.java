package com.chiote.service;

import com.chiote.domain.AvaliacaoControle;
import com.chiote.repository.AvaliacaoControleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing AvaliacaoControle.
 */
@Service
@Transactional
public class AvaliacaoControleService {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoControleService.class);
    
    @Inject
    private AvaliacaoControleRepository avaliacaoControleRepository;

    /**
     * Save a avaliacaoControle.
     *
     * @param avaliacaoControle the entity to save
     * @return the persisted entity
     */
    public AvaliacaoControle save(AvaliacaoControle avaliacaoControle) {
        log.debug("Request to save AvaliacaoControle : {}", avaliacaoControle);
        AvaliacaoControle result = avaliacaoControleRepository.save(avaliacaoControle);
        return result;
    }

    /**
     *  Get all the avaliacaoControles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<AvaliacaoControle> findAll(Pageable pageable) {
        log.debug("Request to get all AvaliacaoControles");
        Page<AvaliacaoControle> result = avaliacaoControleRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one avaliacaoControle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AvaliacaoControle findOne(Long id) {
        log.debug("Request to get AvaliacaoControle : {}", id);
        AvaliacaoControle avaliacaoControle = avaliacaoControleRepository.findOne(id);
        return avaliacaoControle;
    }

    /**
     *  Delete the  avaliacaoControle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AvaliacaoControle : {}", id);
        avaliacaoControleRepository.delete(id);
    }
}
