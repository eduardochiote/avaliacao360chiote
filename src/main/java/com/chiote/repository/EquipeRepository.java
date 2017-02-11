package com.chiote.repository;

import com.chiote.domain.Equipe;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Equipe entity.
 */
@SuppressWarnings("unused")
public interface EquipeRepository extends JpaRepository<Equipe,Long> {

    @Query("select equipe from Equipe equipe where equipe.lider.login = ?#{principal.username}")
    List<Equipe> findByLiderIsCurrentUser();

    @Query("select distinct equipe from Equipe equipe left join fetch equipe.membros")
    List<Equipe> findAllWithEagerRelationships();

    @Query("select equipe from Equipe equipe left join fetch equipe.membros where equipe.id =:id")
    Equipe findOneWithEagerRelationships(@Param("id") Long id);

}
