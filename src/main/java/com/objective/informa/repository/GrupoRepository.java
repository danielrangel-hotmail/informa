package com.objective.informa.repository;

import com.objective.informa.domain.Grupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Grupo entity.
 */
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    @Query(value = "select distinct grupo from Grupo grupo left join fetch grupo.topicos",
        countQuery = "select count(distinct grupo) from Grupo grupo")
    Page<Grupo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct grupo from Grupo grupo left join fetch grupo.topicos")
    List<Grupo> findAllWithEagerRelationships();

    @Query("select grupo from Grupo grupo left join fetch grupo.topicos where grupo.id =:id")
    Optional<Grupo> findOneWithEagerRelationships(@Param("id") Long id);

}
