package com.objective.informa.repository;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select post from Post post where post.autor.login = ?#{principal.username}")
    Page<Post> findByAutorIsCurrentUser(Pageable pageable);

    @Query("select post from Post post where post.autor.login = ?#{principal.username} and post.publicacao is null")
    Page<Post> findByAutorIsCurrentUserAndPublicacaoIsNull(Pageable pageable);

    @Query("select count(post) from Post post where post.autor.login = ?#{principal.username} and post.publicacao is null")
    Long countByAutorIsCurrentUserAndPublicacaoIsNull();

    Page<Post> findByPublicacaoIsNotNull(Pageable pageable);
    
    Page<Post> findByPublicacaoIsNotNullAndGrupoIn(List<Grupo> grupos, Pageable pageable);
    
    Page<Post> findByPublicacaoIsNotNullAndGrupoIdIn(Long grupoId, Pageable pageable);
    
    @Query("select post from Post post where post.publicacao is not null and post.grupo.formal = true")
    Page<Post> findByPublicacaoIsNotNullAndGrupoIsTrabalho(Pageable pageable);

    @Query("select post from Post post where post.publicacao is not null and post.grupo.formal = false")
    Page<Post> findByPublicacaoIsNotNullAndGrupoIsInformal(Pageable pageable);


}
