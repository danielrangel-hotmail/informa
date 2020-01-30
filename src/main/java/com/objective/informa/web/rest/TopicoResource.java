package com.objective.informa.web.rest;

import com.objective.informa.service.TopicoService;
import com.objective.informa.web.rest.errors.BadRequestAlertException;
import com.objective.informa.service.dto.TopicoDTO;

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
 * REST controller for managing {@link com.objective.informa.domain.Topico}.
 */
@RestController
@RequestMapping("/api")
public class TopicoResource {

    private final Logger log = LoggerFactory.getLogger(TopicoResource.class);

    private static final String ENTITY_NAME = "topico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicoService topicoService;

    public TopicoResource(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    /**
     * {@code POST  /topicos} : Create a new topico.
     *
     * @param topicoDTO the topicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicoDTO, or with status {@code 400 (Bad Request)} if the topico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topicos")
    public ResponseEntity<TopicoDTO> createTopico(@Valid @RequestBody TopicoDTO topicoDTO) throws URISyntaxException {
        log.debug("REST request to save Topico : {}", topicoDTO);
        if (topicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new topico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicoDTO result = topicoService.save(topicoDTO);
        return ResponseEntity.created(new URI("/api/topicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topicos} : Updates an existing topico.
     *
     * @param topicoDTO the topicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicoDTO,
     * or with status {@code 400 (Bad Request)} if the topicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topicos")
    public ResponseEntity<TopicoDTO> updateTopico(@Valid @RequestBody TopicoDTO topicoDTO) throws URISyntaxException {
        log.debug("REST request to update Topico : {}", topicoDTO);
        if (topicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicoDTO result = topicoService.save(topicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topicos} : get all the topicos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicos in body.
     */
    @GetMapping("/topicos")
    public List<TopicoDTO> getAllTopicos() {
        log.debug("REST request to get all Topicos");
        return topicoService.findAll();
    }

    /**
     * {@code GET  /topicos/:id} : get the "id" topico.
     *
     * @param id the id of the topicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topicos/{id}")
    public ResponseEntity<TopicoDTO> getTopico(@PathVariable Long id) {
        log.debug("REST request to get Topico : {}", id);
        Optional<TopicoDTO> topicoDTO = topicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicoDTO);
    }

    /**
     * {@code DELETE  /topicos/:id} : delete the "id" topico.
     *
     * @param id the id of the topicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topicos/{id}")
    public ResponseEntity<Void> deleteTopico(@PathVariable Long id) {
        log.debug("REST request to delete Topico : {}", id);
        topicoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
