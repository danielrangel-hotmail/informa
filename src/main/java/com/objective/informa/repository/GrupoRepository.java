package com.objective.informa.repository;

import com.objective.informa.domain.Grupo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Grupo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
