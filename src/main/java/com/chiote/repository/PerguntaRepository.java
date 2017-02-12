package com.chiote.repository;

import com.chiote.domain.Pergunta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pergunta entity.
 */
@SuppressWarnings("unused")
public interface PerguntaRepository extends JpaRepository<Pergunta,Long> {

}
