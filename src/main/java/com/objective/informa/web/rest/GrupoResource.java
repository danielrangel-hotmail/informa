package com.objective.informa.web.rest;

import com.objective.informa.service.GrupoService;
import com.objective.informa.web.rest.errors.BadRequestAlertException;
import com.objective.informa.service.dto.GrupoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.objective.informa.domain.Grupo}.
 */
@RestController
@RequestMapping("/api")
public class GrupoResource {

    private final Logger log = LoggerFactory.getLogger(GrupoResource.class);

    private static final String ENTITY_NAME = "grupo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoService grupoService;

    public GrupoResource(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    /**
     * {@code POST  /grupos} : Create a new grupo.
     *
     * @param grupoDTO the grupoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoDTO, or with status {@code 400 (Bad Request)} if the grupo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupos")
    public ResponseEntity<GrupoDTO> createGrupo(@RequestBody GrupoDTO grupoDTO) throws URISyntaxException {
        log.debug("REST request to save Grupo : {}", grupoDTO);
        if (grupoDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoDTO result = grupoService.create(grupoDTO);
        return ResponseEntity.created(new URI("/api/grupos/" + result.getId()))
            .headers(null)
            .body(result);
    }

    /**
     * {@code PUT  /grupos} : Updates an existing grupo.
     *
     * @param grupoDTO the grupoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoDTO,
     * or with status {@code 400 (Bad Request)} if the grupoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupos")
    public ResponseEntity<GrupoDTO> updateGrupo(@RequestBody GrupoDTO grupoDTO) throws URISyntaxException {
        log.debug("REST request to update Grupo : {}", grupoDTO);
        if (grupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoDTO result = grupoService.save(grupoDTO);
        return ResponseEntity.ok()
            .headers(null)
            .body(result);
    }

    /**
     * {@code GET  /grupos} : get all the grupos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupos in body.
     */
    @GetMapping("/grupos")
    public List<GrupoDTO> getAllGrupos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Grupos");
        return grupoService.findAll();
    }

    /**
     * {@code GET  /grupos/:id} : get the "id" grupo.
     *
     * @param id the id of the grupoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupos/{id}")
    public ResponseEntity<GrupoDTO> getGrupo(@PathVariable Long id) {
        log.debug("REST request to get Grupo : {}", id);
        Optional<GrupoDTO> grupoDTO = grupoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoDTO);
    }

    /**
     * {@code GET  /grupos/:id} : get the "id" grupo.
     *
     * @param id the id of the grupoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupos-com-usuarios/{id}")
    public ResponseEntity<GrupoDTO> getGrupoComUsuarios(@PathVariable Long id) {
        log.debug("REST request to get Grupo : {}", id);
        Optional<GrupoDTO> grupoDTO = grupoService.findOneComUsuarios(id);
        return ResponseUtil.wrapOrNotFound(grupoDTO);
    }

    /**
     * {@code DELETE  /grupos/:id} : delete the "id" grupo.
     *
     * @param id the id of the grupoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupos/{id}")
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        log.debug("REST request to delete Grupo : {}", id);
        grupoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
