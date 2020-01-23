package com.objective.informa.service;

import com.objective.informa.domain.PushSubscription;
import com.objective.informa.repository.PushSubscriptionRepository;
import com.objective.informa.service.dto.PushSubscriptionDTO;
import com.objective.informa.service.mapper.PushSubscriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PushSubscription}.
 */
@Service
@Transactional
public class PushSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(PushSubscriptionService.class);

    private final PushSubscriptionRepository pushSubscriptionRepository;

    private final PushSubscriptionMapper pushSubscriptionMapper;

    public PushSubscriptionService(PushSubscriptionRepository pushSubscriptionRepository, PushSubscriptionMapper pushSubscriptionMapper) {
        this.pushSubscriptionRepository = pushSubscriptionRepository;
        this.pushSubscriptionMapper = pushSubscriptionMapper;
    }

    /**
     * Save a pushSubscription.
     *
     * @param pushSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public PushSubscriptionDTO save(PushSubscriptionDTO pushSubscriptionDTO) {
        log.debug("Request to save PushSubscription : {}", pushSubscriptionDTO);
        PushSubscription pushSubscription = pushSubscriptionMapper.toEntity(pushSubscriptionDTO);
        pushSubscription = pushSubscriptionRepository.save(pushSubscription);
        return pushSubscriptionMapper.toDto(pushSubscription);
    }

    /**
     * Get all the pushSubscriptions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PushSubscriptionDTO> findAll() {
        log.debug("Request to get all PushSubscriptions");
        return pushSubscriptionRepository.findAll().stream()
            .map(pushSubscriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pushSubscription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PushSubscriptionDTO> findOne(Long id) {
        log.debug("Request to get PushSubscription : {}", id);
        return pushSubscriptionRepository.findById(id)
            .map(pushSubscriptionMapper::toDto);
    }

    /**
     * Delete the pushSubscription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PushSubscription : {}", id);
        pushSubscriptionRepository.deleteById(id);
    }
}
