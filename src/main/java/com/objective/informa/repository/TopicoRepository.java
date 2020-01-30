package com.objective.informa.repository;

import com.objective.informa.domain.Topico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Topico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("select topico from Topico topico where topico.autor.login = ?#{principal.username}")
    List<Topico> findByAutorIsCurrentUser();

}
