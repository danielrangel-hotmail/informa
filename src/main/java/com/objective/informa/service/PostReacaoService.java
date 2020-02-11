package com.objective.informa.service;

import com.objective.informa.domain.PostReacao;
import com.objective.informa.repository.PostReacaoRepository;
import com.objective.informa.service.dto.PostReacaoDTO;
import com.objective.informa.service.mapper.PostReacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PostReacao}.
 */
@Service
@Transactional
public class PostReacaoService {

    private final Logger log = LoggerFactory.getLogger(PostReacaoService.class);

    private final PostReacaoRepository postReacaoRepository;

    private final PostReacaoMapper postReacaoMapper;

    public PostReacaoService(PostReacaoRepository postReacaoRepository, PostReacaoMapper postReacaoMapper) {
        this.postReacaoRepository = postReacaoRepository;
        this.postReacaoMapper = postReacaoMapper;
    }

    /**
     * Save a postReacao.
     *
     * @param postReacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public PostReacaoDTO save(PostReacaoDTO postReacaoDTO) {
        log.debug("Request to save PostReacao : {}", postReacaoDTO);
        PostReacao postReacao = postReacaoMapper.toEntity(postReacaoDTO);
        postReacao = postReacaoRepository.save(postReacao);
        return postReacaoMapper.toDto(postReacao);
    }

    /**
     * Get all the postReacaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PostReacaoDTO> findAll() {
        log.debug("Request to get all PostReacaos");
        return postReacaoRepository.findAll().stream()
            .map(postReacaoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one postReacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PostReacaoDTO> findOne(Long id) {
        log.debug("Request to get PostReacao : {}", id);
        return postReacaoRepository.findById(id)
            .map(postReacaoMapper::toDto);
    }

    /**
     * Delete the postReacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PostReacao : {}", id);
        postReacaoRepository.deleteById(id);
    }
}
