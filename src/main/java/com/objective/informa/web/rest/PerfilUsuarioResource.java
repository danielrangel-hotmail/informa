package com.objective.informa.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.objective.informa.service.PerfilUsuarioService;
import com.objective.informa.service.dto.PerfilUsuarioDTO;
import com.objective.informa.service.dto.SimpleUserDTO;
import com.objective.informa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.objective.informa.domain.PerfilUsuario}.
 */
@RestController
@RequestMapping("/api")
public class PerfilUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(PerfilUsuarioResource.class);

    private static final String ENTITY_NAME = "perfilUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilUsuarioService perfilUsuarioService;

    public PerfilUsuarioResource(PerfilUsuarioService perfilUsuarioService) {
        this.perfilUsuarioService = perfilUsuarioService;
    }

    /**
     * {@code POST  /perfil-usuarios} : Create a new perfilUsuario.
     *
     * @param perfilUsuarioDTO the perfilUsuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilUsuarioDTO, or with status {@code 400 (Bad Request)} if the perfilUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perfil-usuarios")
    public ResponseEntity<PerfilUsuarioDTO> createPerfilUsuario(@Valid @RequestBody PerfilUsuarioDTO perfilUsuarioDTO) throws URISyntaxException {
        log.debug("REST request to save PerfilUsuario : {}", perfilUsuarioDTO);
        if (perfilUsuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new perfilUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfilUsuarioDTO result = perfilUsuarioService.save(perfilUsuarioDTO);
        return ResponseEntity.created(new URI("/api/perfil-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perfil-usuarios} : Updates an existing perfilUsuario.
     *
     * @param perfilUsuarioDTO the perfilUsuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilUsuarioDTO,
     * or with status {@code 400 (Bad Request)} if the perfilUsuarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilUsuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perfil-usuarios")
    public ResponseEntity<PerfilUsuarioDTO> updatePerfilUsuario(@Valid @RequestBody PerfilUsuarioDTO perfilUsuarioDTO) throws URISyntaxException {
        log.debug("REST request to update PerfilUsuario : {}", perfilUsuarioDTO);
        if (perfilUsuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerfilUsuarioDTO result = perfilUsuarioService.save(perfilUsuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilUsuarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perfil-usuarios} : get all the perfilUsuarios.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilUsuarios in body.
     */
    @GetMapping("/perfil-usuarios")
    public ResponseEntity<List<PerfilUsuarioDTO>> getAllPerfilUsuarios(Pageable pageable) {
        log.debug("REST request to get a page of PerfilUsuarios");
        Page<PerfilUsuarioDTO> page = perfilUsuarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET /users} : get all users.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/perfil-usuarios/search/{text}")
    public List<SimpleUserDTO> searchPerfilUsuarios(@PathVariable String text) {
    	return perfilUsuarioService.search(text);
    }


    
    /**
     * {@code GET  /perfil-usuarios/:id} : get the "id" perfilUsuario.
     *
     * @param id the id of the perfilUsuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilUsuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perfil-usuarios/{id}")
    public ResponseEntity<PerfilUsuarioDTO> getPerfilUsuario(@PathVariable Long id) {
        log.debug("REST request to get PerfilUsuario : {}", id);
        Optional<PerfilUsuarioDTO> perfilUsuarioDTO = perfilUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilUsuarioDTO);
    }

    /**
     * {@code DELETE  /perfil-usuarios/:id} : delete the "id" perfilUsuario.
     *
     * @param id the id of the perfilUsuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perfil-usuarios/{id}")
    public ResponseEntity<Void> deletePerfilUsuario(@PathVariable Long id) {
        log.debug("REST request to delete PerfilUsuario : {}", id);
        perfilUsuarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
