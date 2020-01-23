package com.objective.informa.service;

import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.repository.PerfilUsuarioRepository;
import com.objective.informa.service.dto.PerfilUsuarioDTO;
import com.objective.informa.service.mapper.PerfilUsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PerfilUsuario}.
 */
@Service
@Transactional
public class PerfilUsuarioService {

    private final Logger log = LoggerFactory.getLogger(PerfilUsuarioService.class);

    private final PerfilUsuarioRepository perfilUsuarioRepository;

    private final PerfilUsuarioMapper perfilUsuarioMapper;

    public PerfilUsuarioService(PerfilUsuarioRepository perfilUsuarioRepository, PerfilUsuarioMapper perfilUsuarioMapper) {
        this.perfilUsuarioRepository = perfilUsuarioRepository;
        this.perfilUsuarioMapper = perfilUsuarioMapper;
    }

    /**
     * Save a perfilUsuario.
     *
     * @param perfilUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilUsuarioDTO save(PerfilUsuarioDTO perfilUsuarioDTO) {
        log.debug("Request to save PerfilUsuario : {}", perfilUsuarioDTO);
        PerfilUsuario perfilUsuario = perfilUsuarioMapper.toEntity(perfilUsuarioDTO);
        perfilUsuario = perfilUsuarioRepository.save(perfilUsuario);
        return perfilUsuarioMapper.toDto(perfilUsuario);
    }

    /**
     * Get all the perfilUsuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilUsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PerfilUsuarios");
        return perfilUsuarioRepository.findAll(pageable)
            .map(perfilUsuarioMapper::toDto);
    }


    /**
     * Get one perfilUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilUsuarioDTO> findOne(Long id) {
        log.debug("Request to get PerfilUsuario : {}", id);
        return perfilUsuarioRepository.findById(id)
            .map(perfilUsuarioMapper::toDto);
    }

    /**
     * Delete the perfilUsuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilUsuario : {}", id);
        perfilUsuarioRepository.deleteById(id);
    }
}
