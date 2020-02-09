package com.objective.informa.service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.objective.informa.domain.Grupo;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.repository.PerfilGrupoRepository;
import com.objective.informa.security.AuthoritiesConstants;
import com.objective.informa.security.SecurityFacade;
import com.objective.informa.service.dto.GrupoDTO;
import com.objective.informa.service.dto.SimpleUserDTO;
import com.objective.informa.service.dto.TopicoDTO;
import com.objective.informa.service.mapper.GrupoMapper;

/**
 * Service Implementation for managing {@link Grupo}.
 */
@Service
@Transactional
public class GrupoService {

    private final Logger log = LoggerFactory.getLogger(GrupoService.class);

    private final GrupoRepository grupoRepository;
    private final TopicoService topicoService;
    private final PerfilGrupoService perfilGrupoService;
    private final PerfilGrupoRepository perfilGrupoRepository;
    private final GrupoMapper grupoMapper;
    private final SecurityFacade securityFacade;


    public GrupoService(GrupoRepository grupoRepository, TopicoService topicoService,
			PerfilGrupoService perfilGrupoService, PerfilGrupoRepository perfilGrupoRepository, GrupoMapper grupoMapper,
			SecurityFacade securityFacade) {
		super();
		this.grupoRepository = grupoRepository;
		this.topicoService = topicoService;
		this.perfilGrupoService = perfilGrupoService;
		this.perfilGrupoRepository = perfilGrupoRepository;
		this.grupoMapper = grupoMapper;
		this.securityFacade = securityFacade;
	}

    
    public GrupoDTO create(GrupoDTO grupoDTO) {
    	return this.save(grupoDTO, new Grupo());
    }
    
    public GrupoDTO save(GrupoDTO grupoDTO) {
    	Grupo grupo = grupoRepository.getOne(grupoDTO.getId());

    	if (!this.securityFacade.isCurrentUserInRole(AuthoritiesConstants.ADMIN) && !logadoEModeradorDoGrupo(grupo)) {
    		throw new AccessDeniedException("Grupo só pode ser alterado por admins ou moderadores");
    	}
    	return this.save(grupoDTO, grupo);
    }


	private boolean logadoEModeradorDoGrupo(Grupo grupo) {
    	String currentUserLogin = this.securityFacade.getCurrentUserLogin().get();
		return grupo.getUsuarios().stream()
    		.anyMatch(perfilGrupo -> perfilGrupo.isModerador() && perfilGrupo.getPerfil().getUsuario().getLogin().equals(currentUserLogin));
	}
    
	/**
     * Save a grupo.
     *
     * @param grupoDTO the entity to save.
     * @return the persisted entity.
     */
    private GrupoDTO save(GrupoDTO grupoDTO, Grupo grupo) {
    	criaNovosTopicos(grupoDTO);
    	log.debug("Request to save Grupo : {}", grupoDTO);
        grupoMapper.updateGrupoFromDto(grupoDTO, grupo);
        ZonedDateTime now = ZonedDateTime.now();
        grupo.setUltimaEdicao(now);
        if (grupo.getCriacao() == null ) {
            grupo.setCriacao(now);
            grupo = grupoRepository.save(grupo);
        }
        this.acertaModeradores(grupo, grupoDTO.getModeradores());
        grupo = grupoRepository.save(grupo);
        return grupoMapper.toDto(grupo);
    }

	private void criaNovosTopicos(GrupoDTO grupoDTO) {
		HashSet<TopicoDTO> topicosParaRemover = new HashSet<TopicoDTO>();
    	HashSet<TopicoDTO> topicosParaAdicionar = new HashSet<TopicoDTO>();
    	grupoDTO.getTopicos()
        	.stream()
        	.filter(filtroDTO -> filtroDTO.getId() == null)
        	.forEach(topicoDTO -> {
        		TopicoDTO topicoNovoDTO = this.topicoService.save(topicoDTO);
        		topicosParaRemover.add(topicoDTO);
        		topicosParaAdicionar.add(topicoNovoDTO);
        	});
    	grupoDTO.getTopicos().removeAll(topicosParaRemover);
    	grupoDTO.getTopicos().addAll(topicosParaAdicionar);
	}
	
	private void acertaModeradores(Grupo grupo, Set<SimpleUserDTO> moderadores) {
		Map<Long, SimpleUserDTO> moderadoresById = moderadores.
				stream().
				collect(Collectors.toMap(SimpleUserDTO::getId, simpleUser -> simpleUser));
		
		grupo.getUsuarios().forEach(perfilGrupo -> {
			if (moderadoresById.containsKey(perfilGrupo.getPerfil().getId())) {
				perfilGrupo.setModerador(true);
				moderadoresById.remove(perfilGrupo.getPerfil().getId());
			} else {
				perfilGrupo.setModerador(false);
				this.perfilGrupoRepository.save(perfilGrupo);
			}
		});
		
		moderadoresById.values().forEach(simpleUser -> {
			try {
				this.perfilGrupoService.criaPerfilModerador(simpleUser.getId(), grupo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

    /**
     * Get all the grupos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoDTO> findAll() {
        log.debug("Request to get all Grupos");
        return grupoRepository.findAllWithEagerRelationships().stream()
            .map(grupoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the grupos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return grupoRepository.findAllWithEagerRelationships(pageable).map(grupoMapper::toDto);
    }
    

    /**
     * Get one grupo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoDTO> findOne(Long id) {
        log.debug("Request to get Grupo : {}", id);
        return grupoRepository.findOneWithEagerRelationships(id)
            .map(grupoMapper::toDto);
    }

    /**
     * Delete the grupo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Grupo : {}", id);
        grupoRepository.deleteById(id);
    }

	public Optional<GrupoDTO> findOneComUsuarios(Long id) {
        return grupoRepository.findOneWithEagerRelationships(id)
                .map(grupoMapper::toDto);
	}
}
