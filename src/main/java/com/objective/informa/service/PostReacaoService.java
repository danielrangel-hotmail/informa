package com.objective.informa.service;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.PostReacao;
import com.objective.informa.repository.PerfilUsuarioRepository;
import com.objective.informa.repository.PostReacaoRepository;
import com.objective.informa.security.SecurityFacade;
import com.objective.informa.service.dto.PostReacaoDTO;
import com.objective.informa.service.dto.PostReacoesDTO;
import com.objective.informa.service.mapper.PostReacaoMapper;
import com.objective.informa.service.post.PostNonAuthorizedException;
import com.objective.informa.service.post.PostUpdateNullException;

/**
 * Service Implementation for managing {@link PostReacao}.
 */
@Service
@Transactional
public class PostReacaoService {

    private final Logger log = LoggerFactory.getLogger(PostReacaoService.class);

    private final PostReacaoRepository postReacaoRepository;

    private final PostReacaoMapper postReacaoMapper;
    
    private final PerfilUsuarioRepository perfilUsuarioRepository;
    
    private final SecurityFacade securityFacade;

    public PostReacaoService(PostReacaoRepository postReacaoRepository, PostReacaoMapper postReacaoMapper, SecurityFacade securityFacade, PerfilUsuarioRepository perfilUsuarioRepository) {
    	this.securityFacade = securityFacade;
        this.postReacaoRepository = postReacaoRepository;
        this.postReacaoMapper = postReacaoMapper;
        this.perfilUsuarioRepository = perfilUsuarioRepository;
    }

    /**
     * Save a postReacao.
     *
     * @param postReacaoDTO the entity to save.
     * @return the persisted entity.
     * @throws PostNonAuthorizedException 
     * @throws PostUpdateNullException 
     */
    public PostReacoesDTO create(PostReacaoDTO postReacaoDTO) {
        log.debug("Request to save PostReacao : {}", postReacaoDTO);
        final PostReacao postReacao = postReacaoMapper.toEntity(postReacaoDTO);
        ZonedDateTime now = ZonedDateTime.now();
        postReacao.setCriacao(now);
        postReacao.setUltimaEdicao(now);
        final Optional<PerfilUsuario> perfilUsuario = this.securityFacade.getCurrentUserLogin().flatMap(perfilUsuarioRepository::findOneByLogin);
        postReacao.setPerfil(perfilUsuario.get());
        postReacaoRepository.saveAndFlush(postReacao);
        return this.postReacoesDTOFromPostId(postReacaoDTO.getPostId());
    }
    
    /**
     * Save a postReacao.
     *
     * @param postReacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public PostReacoesDTO save(PostReacaoDTO postReacaoDTO) {
        log.debug("Request to save PostReacao : {}", postReacaoDTO);
        PostReacao postReacao = postReacaoMapper.toEntity(postReacaoDTO);
        postReacao = postReacaoRepository.saveAndFlush(postReacao);
        return this.postReacoesDTOFromPostId(postReacaoDTO.getPostId());
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
    public PostReacoesDTO delete(Long id) {
        log.debug("Request to delete PostReacao : {}", id);
        Optional<PostReacaoDTO> findOne = this.findOne(id);
		if (findOne.isPresent()) {
			Long postId = findOne.get().getPostId();
			postReacaoRepository.deleteById(id);
			return postReacoesDTOFromPostId(postId);
		}
		return null;
    }
    
    public PostReacoesDTO postReacoesDTOFromPostId(Long id) {
    	String userLogin = securityFacade.getCurrentUserLogin().get();
    	List<PostReacao> reacoes = this.postReacaoRepository.findByPostId(id);
        PostReacao reacaoLogado = reacoes.stream()
        		.filter(r -> r.getPerfil().getUsuario().getLogin().equals(userLogin))
        		.findAny()
        		.orElse(null);
        List<PostReacaoDTO> reacoesDTO = reacoes.stream()
                .map(postReacaoMapper::toDto)
                .collect(Collectors.toList());
        PostReacaoDTO reacaoLogadoDTO = postReacaoMapper.toDto(reacaoLogado);
    	return new PostReacoesDTO(reacoesDTO, reacaoLogadoDTO);
    }
}
