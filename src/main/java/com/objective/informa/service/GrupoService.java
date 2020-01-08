package com.objective.informa.service;

import com.objective.informa.domain.Grupo;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.service.dto.GrupoDTO;
import com.objective.informa.service.mapper.GrupoMapper;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Grupo}.
 */
@Service
@Transactional
public class GrupoService {

    private final Logger log = LoggerFactory.getLogger(GrupoService.class);

    private final GrupoRepository grupoRepository;

    private final GrupoMapper grupoMapper;

    public GrupoService(GrupoRepository grupoRepository, GrupoMapper grupoMapper) {
        this.grupoRepository = grupoRepository;
        this.grupoMapper = grupoMapper;
    }

    /**
     * Save a grupo.
     *
     * @param grupoDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoDTO save(GrupoDTO grupoDTO) {
        log.debug("Request to save Grupo : {}", grupoDTO);
        Grupo grupo = grupoMapper.toEntity(grupoDTO);
        ZonedDateTime now = ZonedDateTime.now();
        if (grupo.getCriacao() == null) {
            grupo.setCriacao(now);
        }
        grupo.setUltimaEdicao(now);
        grupo = grupoRepository.save(grupo);
        return grupoMapper.toDto(grupo);
    }

    /**
     * Get all the grupos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoDTO> findAll() {
        log.debug("Request to get all Grupos");
        return grupoRepository.findAll().stream()
            .map(grupoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one grupo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoDTO> findOne(Long id) {
        log.debug("Request to get Grupo : {}", id);
        return grupoRepository.findById(id)
            .map(grupoMapper::toDto);
    }

    /**
     * Delete the grupo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Grupo : {}", id);
        grupoRepository.deleteById(id);
    }
}
