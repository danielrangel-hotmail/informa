package com.objective.informa.service;

import com.objective.informa.domain.Arquivo;
import com.objective.informa.domain.User;
import com.objective.informa.repository.ArquivoRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.security.SecurityUtils;
import com.objective.informa.service.dto.ArquivoDTO;
import com.objective.informa.service.mapper.ArquivoMapper;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Arquivo}.
 */
@Service
@Transactional
public class ArquivoService {

    private final Logger log = LoggerFactory.getLogger(ArquivoService.class);

    private final ArquivoRepository arquivoRepository;

    private final ArquivoMapper arquivoMapper;

    private final UserRepository userRepository;

    public ArquivoService(ArquivoRepository arquivoRepository, ArquivoMapper arquivoMapper,
        UserRepository userRepository) {
        this.arquivoRepository = arquivoRepository;
        this.arquivoMapper = arquivoMapper;
        this.userRepository = userRepository;
    }

    /**
     * Create um arquivo.
     *
     * @param arquivoDTO the entity to save.
     * @return the persisted entity.
     */
    public ArquivoDTO create(ArquivoDTO arquivoDTO) {
        log.debug("Request to save Arquivo : {}", arquivoDTO);
        Arquivo arquivo = arquivoMapper.toEntity(arquivoDTO);
        ZonedDateTime now = ZonedDateTime.now();
        arquivo.setCriacao(now);
        arquivo.setUltimaEdicao(now);
        final Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
        arquivo.setUsuario(user.get());
        arquivo.getPost().addArquivos(arquivo);
        arquivo = arquivoRepository.save(arquivo);
        return arquivoMapper.toDto(arquivo);
    }

    /**
     * Save a arquivo.
     *
     * @param arquivoDTO the entity to save.
     * @return the persisted entity.
     */
    public ArquivoDTO save(ArquivoDTO arquivoDTO) {
        log.debug("Request to save Arquivo : {}", arquivoDTO);
        Arquivo arquivo = arquivoMapper.toEntity(arquivoDTO);
        arquivo = arquivoRepository.save(arquivo);
        return arquivoMapper.toDto(arquivo);
    }

    /**
     * Get all the arquivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArquivoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Arquivos");
        return arquivoRepository.findAll(pageable)
            .map(arquivoMapper::toDto);
    }


    /**
     * Get one arquivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArquivoDTO> findOne(Long id) {
        log.debug("Request to get Arquivo : {}", id);
        return arquivoRepository.findById(id)
            .map(arquivoMapper::toDto);
    }

    /**
     * Delete the arquivo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Arquivo : {}", id);
        arquivoRepository.deleteById(id);
    }
}
