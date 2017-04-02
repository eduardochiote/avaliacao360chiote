package com.chiote.repository;

import com.chiote.domain.AvaliacaoControle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AvaliacaoControle entity.
 */
@SuppressWarnings("unused")
public interface AvaliacaoControleRepository extends JpaRepository<AvaliacaoControle,Long> {

    @Query("select avaliacaoControle from AvaliacaoControle avaliacaoControle where avaliacaoControle.avaliador.login = ?#{principal.username}")
    List<AvaliacaoControle> findByAvaliadorIsCurrentUser();

}
