package com.objective.informa.service.post;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.LinkExterno;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.Post;
import com.objective.informa.repository.ArquivoRepository;
import com.objective.informa.repository.LinkExternoRepository;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.security.AuthoritiesConstants;
import com.objective.informa.security.SecurityFacade;
import com.objective.informa.service.dto.PostDTO;
import com.objective.informa.service.dto.SimplePostDTO;
import com.objective.informa.service.mapper.PostMapper;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostService {


    private final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostPublisher postPublisher;
    private final SecurityFacade securityFacade;
    private final ArquivoRepository arquivoRepository;
    private final LinkExternoRepository linkExternoRepository;

    public PostService(PostRepository postRepository, PostMapper postMapper,
        PostPublisher postPublisher, SecurityFacade securityFacade, LinkExternoRepository linkExternoRepository, ArquivoRepository arquivoRepository) {
    	this.securityFacade = securityFacade;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.postPublisher = postPublisher;
        this.arquivoRepository = arquivoRepository;
        this.linkExternoRepository = linkExternoRepository;
    }

    /**
     * Create a post.
     *
     * @param postDTO the entity to save.
     * @return the persisted entity.
     */
    public PostDTO create(PostDTO postDTO) {
        log.debug("Request to create Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post.setRemovido(false);
        post.setArquivado(false);
        this.validateOifical(postDTO);
        ZonedDateTime now = ZonedDateTime.now();
        post.setCriacao(now);
        post.setUltimaEdicao(now);
        post.setAutor(securityFacade.getCurrentUser().get());
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    private void validateOifical(PostDTO postDTO) {
    	if (postDTO.isOficial()) {
    		if ((!securityFacade.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) && (!securityFacade.isCurrentUserInRole(AuthoritiesConstants.GESTOR))) {
    			throw new AccessDeniedException("Post oficial só pode ser criado por admins ou gestores");
    		}
    	}
    }
    
    /**
     * Update a post.
     *
     * @param postDTO the entity to save.
     * @return the persisted entity.
     */
    public PostDTO update(PostDTO postDTO)
        throws PostUpdateNullException, PostNonAuthorizedException {
    	validateOifical(postDTO);
    	Post post = postPrepareUpdate(postDTO.getId(), postDTO.getVersao());
        postMapper.updatePostFromDto(postDTO, post);
        return postPosUpdate(post);
    }

    private PostDTO postPosUpdate(Post post) {
        ZonedDateTime now = ZonedDateTime.now();
        post.setUltimaEdicao(now);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    private Post postPrepareUpdate(Long id, Long versao)
        throws PostUpdateNullException, PostNonAuthorizedException {
        Optional<Post> postAntigo = postRepository.findById(id);
        if (!postAntigo.isPresent()) {
            throw new PostUpdateNullException(new SimplePostDTO(id, versao));
        }
        validaPostEDoUsuarioLogado(postAntigo, "Post só pode ser alterado pelo autor");
        if (!postAntigo.get().getVersao().equals(versao)) {
            throw new OptimisticLockException(postAntigo);
        }
        return postAntigo.get();
    }

	private void validaPostEDoUsuarioLogado(Optional<Post> postAntigo, String msg) {
		if (!postAntigo.get().getAutor().getLogin().equals(securityFacade.getCurrentUserLogin().get())) {
            throw new AccessDeniedException(msg);
        }
	}

    public PostDTO publica(SimplePostDTO simplePostDTO)
        throws PostException {
        Post post = postPrepareUpdate(simplePostDTO.getId(), simplePostDTO.getVersao());
        if (post.getPublicacao() != null) {
            throw new PostException(simplePostDTO, "Post já estava publicado");
        }
        post.setPublicacao(ZonedDateTime.now());
        final PostDTO postDTO = this.postPosUpdate(post);
        postPublisher.publish(post);
        return postDTO;
    }

    public void removeLink(LinkExterno linkExterno)
        throws PostUpdateNullException, PostNonAuthorizedException {
        Post post = linkExterno.getPost();
        postPrepareUpdate(post.getId(), post.getVersao());
        post.removeLinksExternos(linkExterno);
        postPosUpdate(post);
    }

    public void processaAlteracao(Function<Post, Post> alteracao, Post post)
        throws PostUpdateNullException, PostNonAuthorizedException {
        postPrepareUpdate(post.getId(), post.getVersao());
        alteracao.apply(post);
        postPosUpdate(post);
    }

    /**
     * Get all the posts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable)
            .map(postMapper::toDto);
    }

    /**
     * Get all the posts from loggedUser.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostDTO> findAllFromLoggedUser(Pageable pageable) {
        log.debug("Request to get all Posts from User");
        return postRepository.findByAutorIsCurrentUser(pageable)
            .map(postMapper::toDto);
    }

    /**
     * Get all the drafts from loggedUser.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostDTO> findAllDraftsFromLoggedUser(Pageable pageable) {
        log.debug("Request to get all Posts from User");
        return postRepository.findByAutorIsCurrentUserAndPublicacaoIsNull(pageable)
            .map(postMapper::toDto);
    }

    /**
     * Count all the drafts from loggedUser.
     * @return the count.
     */
    @Transactional(readOnly = true)
    public Long countAllDraftsFromLoggedUser() {
        log.debug("Request to get all Posts from User");
        return postRepository.countByAutorIsCurrentUserAndPublicacaoIsNull();
    }

    /**
     * Get all the posts from loggedUser.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostDTO> findAllPublicados(Pageable pageable) {
        log.debug("Request to get all Posts from User");
        return postRepository.findByPublicacaoIsNotNull(pageable)
            .map(postMapper::toDto);
    }

    /**
     * Get one post by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PostDTO> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id)
            .map(postMapper::toDto);
    }

    /**
     * Delete the post by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
    	Optional<Post> toBeDeletedOptional = postRepository.findById(id);
    	validaPostEDoUsuarioLogado(toBeDeletedOptional, "Post só pode ser excluido pelo autor");
    	Post toBeDeleted = toBeDeletedOptional.get();
    	if (toBeDeleted.getPublicacao() != null) {
    		toBeDeleted.setRemovido(true);
    		toBeDeleted.setMomentoRemocao(ZonedDateTime.now());
    		toBeDeleted.setRemovedor(securityFacade.getCurrentUser().get());
    		postRepository.save(toBeDeleted);
    	} else {
    		toBeDeleted.getArquivos().forEach(arquivo -> arquivoRepository.deleteById(arquivo.getId()));
    		toBeDeleted.getLinksExternos().forEach(link -> linkExternoRepository.deleteById(link.getId()));
            postRepository.deleteById(id);    		
    	}
    }

	public Page<PostDTO> findAllPublicadosMeusGrupos(Pageable pageable) {
		List<Grupo> grupos = this.perfilUsuarioLogado().getGrupos().stream()
			.map(grupo->grupo.getGrupo())
			.collect(Collectors.toList());
		if (grupos.isEmpty()) return new PageImpl<PostDTO>(new ArrayList<PostDTO>());
		return this.postRepository.findByPublicacaoIsNotNullAndGrupoIn(grupos, pageable)
				.map(postMapper::toDto);
	}

	public Page<PostDTO> findAllPublicadosInformais(Pageable pageable) {
		return this.postRepository.findByPublicacaoIsNotNullAndGrupoIsInformal(pageable)
				.map(postMapper::toDto);
	}

	public Page<PostDTO> findAllPublicadosTrabalho(Pageable pageable) {
		return this.postRepository.findByPublicacaoIsNotNullAndGrupoIsTrabalho(pageable)
				.map(postMapper::toDto);
	}
	
	private PerfilUsuario perfilUsuarioLogado() {
		PerfilUsuario perfilUsuario = securityFacade.getCurrentUser().get().getPerfilUsuario();
		return perfilUsuario;
	}

	public Page<PostDTO> findAllPublicadosGrupoId(Long id, Pageable pageable) {
		return this.postRepository.findByPublicacaoIsNotNullAndGrupoIdIn(id, pageable)
				.map(postMapper::toDto);	
	}
}
