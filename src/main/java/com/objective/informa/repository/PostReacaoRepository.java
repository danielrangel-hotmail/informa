package com.objective.informa.repository;

import com.objective.informa.domain.PostReacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PostReacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostReacaoRepository extends JpaRepository<PostReacao, Long> {

}
