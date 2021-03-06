package com.objective.informa.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.objective.informa.service.ArquivoService;
import com.objective.informa.service.dto.ArquivoDTO;
import com.objective.informa.service.post.PostException;
import com.objective.informa.service.post.PostNonAuthorizedException;
import com.objective.informa.service.signers.AWSS3Signer;
import com.objective.informa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.objective.informa.domain.Arquivo}.
 */
@RestController
@RequestMapping("/api")
public class ArquivoResource {

    private final Logger log = LoggerFactory.getLogger(ArquivoResource.class);

    private static final String ENTITY_NAME = "arquivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArquivoService arquivoService;
    
    private final AWSS3Signer awsS3Signer;

    public ArquivoResource(ArquivoService arquivoService, AWSS3Signer awsS3Signer) {

        this.arquivoService = arquivoService;
        this.awsS3Signer = awsS3Signer; 
    }

    /**
     * {@code POST  /arquivos} : Create a new arquivo.
     *
     * @param arquivoDTO the arquivoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arquivoDTO, or with status {@code 400 (Bad Request)} if the arquivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arquivos")
    public ResponseEntity<ArquivoDTO> createArquivo(@Valid @RequestBody ArquivoDTO arquivoDTO) throws URISyntaxException {
        log.debug("REST request to save Arquivo : {}", arquivoDTO);
        if (arquivoDTO.getId() != null) {
            throw new BadRequestAlertException("A new arquivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        arquivoDTO.setLink(UUID.randomUUID().toString()+arquivoDTO.getNome());
        ArquivoDTO result = arquivoService.create(arquivoDTO);
        this.awsS3Signer.sign(result);
        return ResponseEntity.created(new URI("/api/arquivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arquivos} : Updates an existing arquivo.
     *
     * @param arquivoDTO the arquivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arquivoDTO,
     * or with status {@code 400 (Bad Request)} if the arquivoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arquivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arquivos")
    public ResponseEntity<ArquivoDTO> updateArquivo(@Valid @RequestBody ArquivoDTO arquivoDTO) throws URISyntaxException {
        log.debug("REST request to update Arquivo : {}", arquivoDTO);
        if (arquivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArquivoDTO result = arquivoService.save(arquivoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arquivoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arquivos} : get all the arquivos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arquivos in body.
     */
    @GetMapping("/arquivos")
    public ResponseEntity<List<ArquivoDTO>> getAllArquivos(Pageable pageable) {
        log.debug("REST request to get a page of Arquivos");
        Page<ArquivoDTO> page = arquivoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arquivos/bucket-url} : get the "id" arquivo.
     *
     * @param id the id of the arquivoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arquivoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arquivos/bucket-url")
    public ResponseEntity<BucketUrl> getArquivosURL() {
        return ResponseEntity.ok().body(new BucketUrl(this.awsS3Signer.getArquivosURL())); 
    }

    
    /**
     * {@code GET  /arquivos/:id} : get the "id" arquivo.
     *
     * @param id the id of the arquivoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arquivoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arquivos/{id}")
    public ResponseEntity<ArquivoDTO> getArquivo(@PathVariable Long id) {
        log.debug("REST request to get Arquivo : {}", id);
        Optional<ArquivoDTO> arquivoDTO = arquivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arquivoDTO);
    }

    /**
     * {@code DELETE  /arquivos/:id} : delete the "id" arquivo.
     *
     * @param id the id of the arquivoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arquivos/{id}")
    public ResponseEntity<Void> deleteArquivo(@PathVariable Long id) {
        log.debug("REST request to delete Arquivo : {}", id);
        try {
            arquivoService.delete(id);
        } catch (PostNonAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PostException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, id.toString());
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    private class BucketUrl {
    	@SuppressWarnings("unused")
		public String url;
    	public BucketUrl(String url) {
    		this.url = url;
    	}
    }
}
