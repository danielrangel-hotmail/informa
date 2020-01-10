package com.objective.informa.service.post;

import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.security.SecurityUtils;
import com.objective.informa.service.UserService;
import com.objective.informa.service.dto.PostDTO;
import com.objective.informa.service.dto.SimplePostDTO;
import com.objective.informa.service.mapper.PostMapper;
import java.time.ZonedDateTime;
import javax.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostService {



    private final Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostMapper postMapper,
        UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
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
        ZonedDateTime now = ZonedDateTime.now();
        post.setCriacao(now);
        post.setUltimaEdicao(now);
        final Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
        post.setAutor(user.get());
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    /**
     * Update a post.
     *
     * @param postDTO the entity to save.
     * @return the persisted entity.
     */
    public PostDTO update(PostDTO postDTO)
        throws PostUpdateNullException, PostNonAuthorizedException {
        postPrepareUpdate(postDTO.getId(), postDTO.getVersao());
        Post post = postMapper.toEntity(postDTO);
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
        if (!postAntigo.get().getAutor().getLogin().equals(SecurityUtils.getCurrentUserLogin().get())) {
            throw new PostNonAuthorizedException(new SimplePostDTO(id, versao));
        }

        if (!postAntigo.get().getVersao().equals(versao)) {
            throw new OptimisticLockException(postAntigo);
        }
        return postAntigo.get();
    }

    public PostDTO publica(SimplePostDTO simplePostDTO)
        throws PostException {
        Post post = postPrepareUpdate(simplePostDTO.getId(), simplePostDTO.getVersao());
        if (post.getPublicacao() != null) {
            throw new PostException(simplePostDTO, "Post já estava publicado");
        }
        post.setPublicacao(ZonedDateTime.now());
        post = postRepository.save(post);
        return postMapper.toDto(post);
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
        postRepository.deleteById(id);
    }
}
