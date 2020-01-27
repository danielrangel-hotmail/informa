package com.objective.informa.web.rest;

import com.objective.informa.service.PerfilGrupoService;
import com.objective.informa.web.rest.errors.BadRequestAlertException;
import com.objective.informa.service.dto.PerfilGrupoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.objective.informa.domain.PerfilGrupo}.
 */
@RestController
@RequestMapping("/api")
public class PerfilGrupoResource {

    private final Logger log = LoggerFactory.getLogger(PerfilGrupoResource.class);

    private static final String ENTITY_NAME = "perfilGrupo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilGrupoService perfilGrupoService;

    public PerfilGrupoResource(PerfilGrupoService perfilGrupoService) {
        this.perfilGrupoService = perfilGrupoService;
    }

    /**
     * {@code POST  /perfil-grupos} : Create a new perfilGrupo.
     *
     * @param perfilGrupoDTO the perfilGrupoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilGrupoDTO, or with status {@code 400 (Bad Request)} if the perfilGrupo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perfil-grupos")
    public ResponseEntity<PerfilGrupoDTO> createPerfilGrupo(@Valid @RequestBody PerfilGrupoDTO perfilGrupoDTO) throws URISyntaxException {
        log.debug("REST request to save PerfilGrupo : {}", perfilGrupoDTO);
        if (perfilGrupoDTO.getId() != null) {
            throw new BadRequestAlertException("A new perfilGrupo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfilGrupoDTO result;
		try {
			result = perfilGrupoService.create(perfilGrupoDTO);
		} catch (Exception e) {
			throw new BadRequestAlertException("Perfil já com gruop", ENTITY_NAME, "idnull");
		}
        return ResponseEntity.created(new URI("/api/perfil-grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perfil-grupos} : Updates an existing perfilGrupo.
     *
     * @param perfilGrupoDTO the perfilGrupoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilGrupoDTO,
     * or with status {@code 400 (Bad Request)} if the perfilGrupoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilGrupoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perfil-grupos")
    public ResponseEntity<PerfilGrupoDTO> updatePerfilGrupo(@Valid @RequestBody PerfilGrupoDTO perfilGrupoDTO) throws URISyntaxException {
        log.debug("REST request to update PerfilGrupo : {}", perfilGrupoDTO);
        if (perfilGrupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerfilGrupoDTO result = perfilGrupoService.save(perfilGrupoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilGrupoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perfil-grupos} : get all the perfilGrupos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilGrupos in body.
     */
    @GetMapping("/perfil-grupos")
    public ResponseEntity<List<PerfilGrupoDTO>> getAllPerfilGrupos(Pageable pageable) {
        log.debug("REST request to get a page of PerfilGrupos");
        Page<PerfilGrupoDTO> page = perfilGrupoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /perfil-grupos-management} : get all the perfilGrupos.
     * Retorna também um perfil grupo zerado para cada grupo que não rolou join
     * Também só retorna de self

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilGrupos in body.
     */
    @GetMapping("/perfil-grupos-management")
    public List<PerfilGrupoDTO> getAllPerfilGruposManagement() {
        log.debug("REST request to get PerfilGrupos Manaement");
        List<PerfilGrupoDTO> list = perfilGrupoService.findAllManagement();
        return list;
    }

    
    /**
     * {@code GET  /perfil-grupos/:id} : get the "id" perfilGrupo.
     *
     * @param id the id of the perfilGrupoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilGrupoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perfil-grupos/{id}")
    public ResponseEntity<PerfilGrupoDTO> getPerfilGrupo(@PathVariable Long id) {
        log.debug("REST request to get PerfilGrupo : {}", id);
        Optional<PerfilGrupoDTO> perfilGrupoDTO = perfilGrupoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilGrupoDTO);
    }

    /**
     * {@code DELETE  /perfil-grupos/:id} : delete the "id" perfilGrupo.
     *
     * @param id the id of the perfilGrupoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perfil-grupos/{id}")
    public ResponseEntity<Void> deletePerfilGrupo(@PathVariable Long id) {
        log.debug("REST request to delete PerfilGrupo : {}", id);
        try {
        	perfilGrupoService.delete(id);
		} catch (Exception e) {
			throw new BadRequestAlertException("Perfil de outro usuário", ENTITY_NAME, "idnull");
		}

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
