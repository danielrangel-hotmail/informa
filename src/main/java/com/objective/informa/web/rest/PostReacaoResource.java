package com.objective.informa.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.objective.informa.service.PostReacaoService;
import com.objective.informa.service.dto.PostReacaoDTO;
import com.objective.informa.service.dto.PostReacoesDTO;
import com.objective.informa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.objective.informa.domain.PostReacao}.
 */
@RestController
@RequestMapping("/api")
public class PostReacaoResource {

    private final Logger log = LoggerFactory.getLogger(PostReacaoResource.class);

    private static final String ENTITY_NAME = "postReacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostReacaoService postReacaoService;

    public PostReacaoResource(PostReacaoService postReacaoService) {
        this.postReacaoService = postReacaoService;
    }

    /**
     * {@code POST  /post-reacaos} : Create a new postReacao.
     *
     * @param postReacaoDTO the postReacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postReacaoDTO, or with status {@code 400 (Bad Request)} if the postReacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-reacaos")
    public ResponseEntity<PostReacoesDTO> createPostReacao(@Valid @RequestBody PostReacaoDTO postReacaoDTO) throws URISyntaxException {
        log.debug("REST request to save PostReacao : {}", postReacaoDTO);
        if (postReacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new postReacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostReacoesDTO result = postReacaoService.create(postReacaoDTO);
        return ResponseEntity.created(new URI("/api/post-reacaos/" + result.getReacaoLogado().getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getReacaoLogado().getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-reacaos} : Updates an existing postReacao.
     *
     * @param postReacaoDTO the postReacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postReacaoDTO,
     * or with status {@code 400 (Bad Request)} if the postReacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postReacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-reacaos")
    public ResponseEntity<PostReacoesDTO> updatePostReacao(@Valid @RequestBody PostReacaoDTO postReacaoDTO) throws URISyntaxException {
        log.debug("REST request to update PostReacao : {}", postReacaoDTO);
        if (postReacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostReacoesDTO result = postReacaoService.save(postReacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postReacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-reacaos} : get all the postReacaos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postReacaos in body.
     */
    @GetMapping("/post-reacaos")
    public List<PostReacaoDTO> getAllPostReacaos() {
        log.debug("REST request to get all PostReacaos");
        return postReacaoService.findAll();
    }

    /**
     * {@code GET  /post-reacaos/:id} : get the "id" postReacao.
     *
     * @param id the id of the postReacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postReacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-reacaos/{id}")
    public ResponseEntity<PostReacaoDTO> getPostReacao(@PathVariable Long id) {
        log.debug("REST request to get PostReacao : {}", id);
        Optional<PostReacaoDTO> postReacaoDTO = postReacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postReacaoDTO);
    }

    /**
     * {@code DELETE  /post-reacaos/:id} : delete the "id" postReacao.
     *
     * @param id the id of the postReacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-reacaos/{id}")
    public ResponseEntity<PostReacoesDTO> deletePostReacao(@PathVariable Long id) {
        log.debug("REST request to delete PostReacao : {}", id);
        PostReacoesDTO result = postReacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(result);
    }
}
