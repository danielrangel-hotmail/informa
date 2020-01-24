package com.objective.informa.web.rest;

import com.objective.informa.service.PushSubscriptionService;
import com.objective.informa.web.rest.errors.BadRequestAlertException;
import com.objective.informa.service.dto.PushSubscriptionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.objective.informa.domain.PushSubscription}.
 */
@RestController
@RequestMapping("/api")
public class PushSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(PushSubscriptionResource.class);

    private static final String ENTITY_NAME = "pushSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PushSubscriptionService pushSubscriptionService;

    public PushSubscriptionResource(PushSubscriptionService pushSubscriptionService) {
        this.pushSubscriptionService = pushSubscriptionService;
    }

    /**
     * {@code POST  /push-subscriptions} : Create a new pushSubscription.
     *
     * @param pushSubscriptionDTO the pushSubscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pushSubscriptionDTO, or with status {@code 400 (Bad Request)} if the pushSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/push-subscriptions")
    public ResponseEntity<PushSubscriptionDTO> createPushSubscription(@Valid @RequestBody PushSubscriptionDTO pushSubscriptionDTO) throws URISyntaxException {
        log.debug("REST request to save PushSubscription : {}", pushSubscriptionDTO);
        if (pushSubscriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new pushSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PushSubscriptionDTO result = pushSubscriptionService.create(pushSubscriptionDTO);
        return ResponseEntity.created(new URI("/api/push-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /push-subscriptions} : Updates an existing pushSubscription.
     *
     * @param pushSubscriptionDTO the pushSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pushSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the pushSubscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pushSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/push-subscriptions")
    public ResponseEntity<PushSubscriptionDTO> updatePushSubscription(@Valid @RequestBody PushSubscriptionDTO pushSubscriptionDTO) throws URISyntaxException {
        log.debug("REST request to update PushSubscription : {}", pushSubscriptionDTO);
        if (pushSubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PushSubscriptionDTO result = pushSubscriptionService.save(pushSubscriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pushSubscriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /push-subscriptions} : get all the pushSubscriptions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pushSubscriptions in body.
     */
    @GetMapping("/push-subscriptions")
    public List<PushSubscriptionDTO> getAllPushSubscriptions() {
        log.debug("REST request to get all PushSubscriptions");
        return pushSubscriptionService.findAll();
    }

    /**
     * {@code GET  /push-subscriptions/:id} : get the "id" pushSubscription.
     *
     * @param id the id of the pushSubscriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pushSubscriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/push-subscriptions/{id}")
    public ResponseEntity<PushSubscriptionDTO> getPushSubscription(@PathVariable Long id) {
        log.debug("REST request to get PushSubscription : {}", id);
        Optional<PushSubscriptionDTO> pushSubscriptionDTO = pushSubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pushSubscriptionDTO);
    }

    /**
     * {@code DELETE  /push-subscriptions/:id} : delete the "id" pushSubscription.
     *
     * @param id the id of the pushSubscriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/push-subscriptions/{id}")
    public ResponseEntity<Void> deletePushSubscription(@PathVariable Long id) {
        log.debug("REST request to delete PushSubscription : {}", id);
        pushSubscriptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
