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
import org.springframework.http.HttpStatus;
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

import com.objective.informa.service.LinkExternoService;
import com.objective.informa.service.dto.LinkExternoDTO;
import com.objective.informa.service.post.PostException;
import com.objective.informa.service.post.PostNonAuthorizedException;
import com.objective.informa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.objective.informa.domain.LinkExterno}.
 */
@RestController
@RequestMapping("/api")
public class LinkExternoResource {

    private final Logger log = LoggerFactory.getLogger(LinkExternoResource.class);

    private static final String ENTITY_NAME = "linkExterno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinkExternoService linkExternoService;

    public LinkExternoResource(LinkExternoService linkExternoService) {
        this.linkExternoService = linkExternoService;
    }

    /**
     * {@code POST  /link-externos} : Create a new linkExterno.
     *
     * @param linkExternoDTO the linkExternoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linkExternoDTO, or with status {@code 400 (Bad Request)} if the linkExterno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/link-externos")
    public ResponseEntity<LinkExternoDTO> createLinkExterno(@Valid @RequestBody LinkExternoDTO linkExternoDTO) throws URISyntaxException {
        log.debug("REST request to save LinkExterno : {}", linkExternoDTO);
        if (linkExternoDTO.getId() != null) {
            throw new BadRequestAlertException("A new linkExterno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinkExternoDTO result = linkExternoService.create(linkExternoDTO);
        return ResponseEntity.created(new URI("/api/link-externos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /link-externos} : Updates an existing linkExterno.
     *
     * @param linkExternoDTO the linkExternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkExternoDTO,
     * or with status {@code 400 (Bad Request)} if the linkExternoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linkExternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/link-externos")
    public ResponseEntity<LinkExternoDTO> updateLinkExterno(@Valid @RequestBody LinkExternoDTO linkExternoDTO) throws URISyntaxException {
        log.debug("REST request to update LinkExterno : {}", linkExternoDTO);
        if (linkExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LinkExternoDTO result = linkExternoService.save(linkExternoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linkExternoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /link-externos} : get all the linkExternos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linkExternos in body.
     */
    @GetMapping("/link-externos")
    public ResponseEntity<List<LinkExternoDTO>> getAllLinkExternos(Pageable pageable) {
        log.debug("REST request to get a page of LinkExternos");
        Page<LinkExternoDTO> page = linkExternoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /link-externos/:id} : get the "id" linkExterno.
     *
     * @param id the id of the linkExternoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linkExternoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/link-externos/{id}")
    public ResponseEntity<LinkExternoDTO> getLinkExterno(@PathVariable Long id) {
        log.debug("REST request to get LinkExterno : {}", id);
        Optional<LinkExternoDTO> linkExternoDTO = linkExternoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(linkExternoDTO);
    }

    /**
     * {@code DELETE  /link-externos/:id} : delete the "id" linkExterno.
     *
     * @param id the id of the linkExternoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/link-externos/{id}")
    public ResponseEntity<Void> deleteLinkExterno(@PathVariable Long id) {
        log.debug("REST request to delete LinkExterno : {}", id);
        try {
            linkExternoService.delete(id);
        } catch (PostNonAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PostException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, id.toString());
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
