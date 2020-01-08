package com.objective.informa.service;

import com.objective.informa.domain.Mensagem;
import com.objective.informa.repository.MensagemRepository;
import com.objective.informa.service.dto.MensagemDTO;
import com.objective.informa.service.mapper.MensagemMapper;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Mensagem}.
 */
@Service
@Transactional
public class MensagemService {

    private final Logger log = LoggerFactory.getLogger(MensagemService.class);

    private final MensagemRepository mensagemRepository;

    private final MensagemMapper mensagemMapper;

    private final UserService userService;

    public MensagemService(MensagemRepository mensagemRepository, MensagemMapper mensagemMapper,
        UserService userService) {
        this.mensagemRepository = mensagemRepository;
        this.mensagemMapper = mensagemMapper;
        this.userService = userService;
    }

    /**
     * Save a mensagem.
     *
     * @param mensagemDTO the entity to save.
     * @return the persisted entity.
     */
    public MensagemDTO save(MensagemDTO mensagemDTO) {
        log.debug("Request to save Mensagem : {}", mensagemDTO);
        Mensagem mensagem = mensagemMapper.toEntity(mensagemDTO);
        ZonedDateTime now = ZonedDateTime.now();
        if (mensagem.getCriacao() == null) {
            mensagem.setCriacao(now);
            mensagem.setAutor(userService.getUserWithAuthorities().get());
        }
        mensagem.setUltimaEdicao(now);

        mensagem = mensagemRepository.save(mensagem);
        return mensagemMapper.toDto(mensagem);
    }

    /**
     * Get all the mensagems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MensagemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mensagems");
        return mensagemRepository.findAll(pageable)
            .map(mensagemMapper::toDto);
    }


    /**
     * Get one mensagem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MensagemDTO> findOne(Long id) {
        log.debug("Request to get Mensagem : {}", id);
        return mensagemRepository.findById(id)
            .map(mensagemMapper::toDto);
    }

    /**
     * Delete the mensagem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mensagem : {}", id);
        mensagemRepository.deleteById(id);
    }
}
