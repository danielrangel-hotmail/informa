package com.objective.informa.service;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.objective.informa.domain.LinkExterno;
import com.objective.informa.domain.User;
import com.objective.informa.repository.LinkExternoRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.security.SecurityUtils;
import com.objective.informa.service.dto.LinkExternoDTO;
import com.objective.informa.service.mapper.LinkExternoMapper;
import com.objective.informa.service.post.PostNonAuthorizedException;
import com.objective.informa.service.post.PostService;
import com.objective.informa.service.post.PostUpdateNullException;

/**
 * Service Implementation for managing {@link LinkExterno}.
 */
@Service
@Transactional
public class LinkExternoService {

    private final Logger log = LoggerFactory.getLogger(LinkExternoService.class);

    private final PostService postService;
    private final LinkExternoRepository linkExternoRepository;
    private final LinkExternoMapper linkExternoMapper;
    private final UserRepository userRepository;

    public LinkExternoService(PostService postService,
        LinkExternoRepository linkExternoRepository, LinkExternoMapper linkExternoMapper,
        UserRepository userRepository) {
        this.postService = postService;
        this.linkExternoRepository = linkExternoRepository;
        this.linkExternoMapper = linkExternoMapper;
        this.userRepository = userRepository;
    }

    /**
     * Create um arquivo.
     *
     * @param linkExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public LinkExternoDTO create(LinkExternoDTO linkExternoDTO) {

        log.debug("Request to create LinkExterno : {}", linkExternoDTO);
        LinkExterno linkExterno = linkExternoMapper.toEntity(linkExternoDTO);
        ZonedDateTime now = ZonedDateTime.now();
        linkExterno.setCriacao(now);
        linkExterno.setUltimaEdicao(now);
        final Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
        linkExterno.setUsuario(user.get());
        linkExterno.getPost().addLinksExternos(linkExterno);
        linkExterno = linkExternoRepository.save(linkExterno);
        return linkExternoMapper.toDto(linkExterno);

    }

    /**
     * Save a linkExterno.
     *
     * @param linkExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public LinkExternoDTO save(LinkExternoDTO linkExternoDTO) {
        log.debug("Request to save LinkExterno : {}", linkExternoDTO);
        LinkExterno linkExterno = linkExternoMapper.toEntity(linkExternoDTO);
        ZonedDateTime now = ZonedDateTime.now();
        if (linkExterno.getCriacao() == null) {
            linkExterno.setCriacao(now);
        }
        linkExterno.setUltimaEdicao(now);
        linkExterno = linkExternoRepository.saveAndFlush(linkExterno);
        return linkExternoMapper.toDto(linkExterno);
    }

    /**
     * Get all the linkExternos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LinkExternoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LinkExternos");
        return linkExternoRepository.findAll(pageable)
            .map(linkExternoMapper::toDto);
    }


    /**
     * Get one linkExterno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LinkExternoDTO> findOne(Long id) {
        log.debug("Request to get LinkExterno : {}", id);
        return linkExternoRepository.findById(id)
            .map(linkExternoMapper::toDto);
    }

    /**
     * Delete the linkExterno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) throws PostUpdateNullException, PostNonAuthorizedException {
        log.debug("Request to delete LinkExterno : {}", id);
        final Optional<LinkExterno> optionalLinkExterno = linkExternoRepository.findById(id);
        if (!optionalLinkExterno.isPresent()) return;
        LinkExterno linkExterno = optionalLinkExterno.get();
        if (linkExterno.getPost() != null) {
            postService.removeLink(linkExterno);
        } else {
            if (linkExterno.getMensagem() != null) {
        }}
        linkExternoRepository.deleteById(id);
    }
}
