package com.objective.informa.repository;

import com.objective.informa.domain.Mensagem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Mensagem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    @Query("select mensagem from Mensagem mensagem where mensagem.autor.login = ?#{principal.username}")
    List<Mensagem> findByAutorIsCurrentUser();

}
