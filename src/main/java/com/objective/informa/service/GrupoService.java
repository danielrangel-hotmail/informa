package com.objective.informa.service;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.Topico;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.service.dto.GrupoDTO;
import com.objective.informa.service.dto.TopicoDTO;
import com.objective.informa.service.mapper.GrupoMapper;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    private final TopicoService topicoService;
    private final GrupoMapper grupoMapper;

    public GrupoService(GrupoRepository grupoRepository, GrupoMapper grupoMapper, TopicoService topicoService) {
        this.grupoRepository = grupoRepository;
        this.grupoMapper = grupoMapper;
        this.topicoService = topicoService;
    }

    /**
     * Save a grupo.
     *
     * @param grupoDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoDTO save(GrupoDTO grupoDTO) {
    	
    	criaNovosTopicos(grupoDTO);
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

	private void criaNovosTopicos(GrupoDTO grupoDTO) {
		HashSet<TopicoDTO> topicosParaRemover = new HashSet<TopicoDTO>();
    	HashSet<TopicoDTO> topicosParaAdicionar = new HashSet<TopicoDTO>();
    	grupoDTO.getTopicos()
        	.stream()
        	.filter(filtroDTO -> filtroDTO.getId() == null)
        	.forEach(topicoDTO -> {
        		TopicoDTO topicoNovoDTO = this.topicoService.save(topicoDTO);
        		topicosParaRemover.add(topicoDTO);
        		topicosParaAdicionar.add(topicoNovoDTO);
        	});
    	grupoDTO.getTopicos().removeAll(topicosParaRemover);
    	grupoDTO.getTopicos().addAll(topicosParaAdicionar);
	}

    /**
     * Get all the grupos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoDTO> findAll() {
        log.debug("Request to get all Grupos");
        return grupoRepository.findAllWithEagerRelationships().stream()
            .map(grupoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the grupos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return grupoRepository.findAllWithEagerRelationships(pageable).map(grupoMapper::toDto);
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
        return grupoRepository.findOneWithEagerRelationships(id)
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
