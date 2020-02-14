package com.objective.informa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.objective.informa.domain.PostReacao;


/**
 * Spring Data  repository for the PostReacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostReacaoRepository extends JpaRepository<PostReacao, Long> {
	
	public List<PostReacao> findByPostId(Long id);
	
}
