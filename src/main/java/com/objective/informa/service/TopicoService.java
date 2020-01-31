package com.objective.informa.service;

import com.objective.informa.domain.Topico;
import com.objective.informa.domain.User;
import com.objective.informa.repository.TopicoRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.security.SecurityUtils;
import com.objective.informa.service.dto.TopicoDTO;
import com.objective.informa.service.mapper.TopicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Topico}.
 */
@Service
@Transactional
public class TopicoService {

    private final Logger log = LoggerFactory.getLogger(TopicoService.class);

    private final TopicoRepository topicoRepository;

    private final TopicoMapper topicoMapper;
    
    private final UserRepository userRepository;

    public TopicoService(TopicoRepository topicoRepository, TopicoMapper topicoMapper, UserRepository userRepository) {
        this.topicoRepository = topicoRepository;
        this.topicoMapper = topicoMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a topico.
     *
     * @param topicoDTO the entity to save.
     * @return the persisted entity.
     */
    public TopicoDTO save(TopicoDTO topicoDTO) {
        log.debug("Request to save Topico : {}", topicoDTO);
        Topico topico = topicoMapper.toEntity(topicoDTO);
        final Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
        topico.setAutor(user.get());
        topico = topicoRepository.saveAndFlush(topico);
        return topicoMapper.toDto(topico);
    }

    /**
     * Get all the topicos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TopicoDTO> findAll() {
        log.debug("Request to get all Topicos");
        return topicoRepository.findAll().stream()
            .map(topicoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one topico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TopicoDTO> findOne(Long id) {
        log.debug("Request to get Topico : {}", id);
        return topicoRepository.findById(id)
            .map(topicoMapper::toDto);
    }

    /**
     * Delete the topico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Topico : {}", id);
        topicoRepository.deleteById(id);
    }
}
