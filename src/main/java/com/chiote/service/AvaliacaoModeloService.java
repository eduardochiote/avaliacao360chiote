package com.chiote.service;

import com.chiote.domain.AvaliacaoModelo;
import com.chiote.repository.AvaliacaoModeloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing AvaliacaoModelo.
 */
@Service
@Transactional
public class AvaliacaoModeloService {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoModeloService.class);
    
    @Inject
    private AvaliacaoModeloRepository avaliacaoModeloRepository;

    /**
     * Save a avaliacaoModelo.
     *
     * @param avaliacaoModelo the entity to save
     * @return the persisted entity
     */
    public AvaliacaoModelo save(AvaliacaoModelo avaliacaoModelo) {
        log.debug("Request to save AvaliacaoModelo : {}", avaliacaoModelo);
        AvaliacaoModelo result = avaliacaoModeloRepository.save(avaliacaoModelo);
        return result;
    }

    /**
     *  Get all the avaliacaoModelos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<AvaliacaoModelo> findAll(Pageable pageable) {
        log.debug("Request to get all AvaliacaoModelos");
        Page<AvaliacaoModelo> result = avaliacaoModeloRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one avaliacaoModelo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AvaliacaoModelo findOne(Long id) {
        log.debug("Request to get AvaliacaoModelo : {}", id);
        AvaliacaoModelo avaliacaoModelo = avaliacaoModeloRepository.findOne(id);
        return avaliacaoModelo;
    }

    /**
     *  Delete the  avaliacaoModelo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AvaliacaoModelo : {}", id);
        avaliacaoModeloRepository.delete(id);
    }
}
