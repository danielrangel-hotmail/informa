package com.objective.informa.repository;

import com.objective.informa.domain.Arquivo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Arquivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

    @Query("select arquivo from Arquivo arquivo where arquivo.usuario.login = ?#{principal.username}")
    List<Arquivo> findByUsuarioIsCurrentUser();

}
