package com.objective.informa.repository;

import com.objective.informa.domain.PerfilGrupo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PerfilGrupo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilGrupoRepository extends JpaRepository<PerfilGrupo, Long> {

}
