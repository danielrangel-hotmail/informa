package com.objective.informa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.objective.informa.domain.PerfilUsuario;


/**
 * Spring Data  repository for the PerfilUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Long> {
	

}
