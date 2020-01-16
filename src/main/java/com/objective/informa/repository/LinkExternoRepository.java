package com.objective.informa.repository;

import com.objective.informa.domain.LinkExterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the LinkExterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinkExternoRepository extends JpaRepository<LinkExterno, Long> {

    @Query("select linkExterno from LinkExterno linkExterno where linkExterno.usuario.login = ?#{principal.username}")
    List<LinkExterno> findByUsuarioIsCurrentUser();

}
