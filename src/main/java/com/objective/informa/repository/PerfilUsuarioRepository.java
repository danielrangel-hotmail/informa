package com.objective.informa.repository;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PerfilUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Long> {
    @Query("select perfil from PerfilUsuario perfil left join fetch perfil.usuario where ((perfil.usuario.login LIKE %:texto%) or (perfil.usuario.email LIKE %:texto%) or (perfil.usuario.firstName LIKE %:texto%) or (perfil.usuario.lastName LIKE %:texto%)) ")
    List<PerfilUsuario> searchPerfil(@Param("texto") String texto, Sort sort);

    @Query("select perfil from PerfilUsuario perfil left join fetch perfil.usuario where perfil.usuario.login = :login")
    Optional<PerfilUsuario> findOneByLogin(@Param("login") String login);
    
}
