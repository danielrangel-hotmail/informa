package com.objective.informa.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.PerfilGrupo;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.repository.PerfilGrupoRepository;
import com.objective.informa.repository.PerfilUsuarioRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.security.SecurityUtils;
import com.objective.informa.service.dto.PerfilGrupoDTO;
import com.objective.informa.service.mapper.GrupoMapper;
import com.objective.informa.service.mapper.PerfilGrupoMapper;

/**
 * Service Implementation for managing {@link PerfilGrupo}.
 */
@Service
@Transactional
public class PerfilGrupoService {

    private final Logger log = LoggerFactory.getLogger(PerfilGrupoService.class);

    private final PerfilGrupoRepository perfilGrupoRepository;

    private final PerfilGrupoMapper perfilGrupoMapper;
    
    private final GrupoMapper grupoMapper;
    
    private final UserRepository userRepository;

    private final GrupoRepository grupoRepository;
    
    private final PerfilUsuarioRepository perfillUsuarioRepository; 
    
    public PerfilGrupoService(PerfilGrupoRepository perfilGrupoRepository, PerfilGrupoMapper perfilGrupoMapper,UserRepository userRepository, 
    		GrupoRepository grupoRepository, GrupoMapper grupoMapper, PerfilUsuarioRepository perfillUsuarioRepository) {
        this.perfillUsuarioRepository = perfillUsuarioRepository;
		this.userRepository = userRepository;
		this.perfilGrupoRepository = perfilGrupoRepository;
        this.perfilGrupoMapper = perfilGrupoMapper;
        this.grupoRepository = grupoRepository;
        this.grupoMapper = grupoMapper;
    }

    /**
     * Save a perfilGrupo.
     *
     * @param perfilGrupoDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilGrupoDTO save(PerfilGrupoDTO perfilGrupoDTO) {
        log.debug("Request to save PerfilGrupo : {}", perfilGrupoDTO);
        PerfilGrupo perfilGrupo = perfilGrupoMapper.toEntity(perfilGrupoDTO);
        perfilGrupo = perfilGrupoRepository.save(perfilGrupo);
        return perfilGrupoMapper.toDto(perfilGrupo);
    }

    /**
     * Save a perfilGrupo.
     *
     * @param perfilGrupoDTO the entity to save.
     * @return the persisted entity.
     * @throws Exception 
     */
    public PerfilGrupoDTO create(PerfilGrupoDTO perfilGrupoDTO) throws Exception {
        log.debug("Request to save PerfilGrupo : {}", perfilGrupoDTO);
        final PerfilGrupo perfilGrupo = perfilGrupoMapper.toEntity(perfilGrupoDTO);
        PerfilUsuario perfilUsuario = this.perfilUsuarioLogado();
        associaPerfilAGrupo(perfilGrupo, perfilUsuario);
        PerfilGrupo novoPerfilGrupo = perfilGrupoRepository.save(perfilGrupo);
        return perfilGrupoMapper.toDto(novoPerfilGrupo);
    }

	private void associaPerfilAGrupo(final PerfilGrupo perfilGrupo, PerfilUsuario perfilUsuario)
			throws Exception {
		if (perfilUsuario.getGrupos()
        	.stream()
        	.anyMatch(perfilGrupoExistente -> perfilGrupoExistente.getGrupo() == perfilGrupo.getGrupo())) {;
        		throw new Exception("Usuário já está dentro do grupo");
        	}
        perfilUsuario.addGrupos(perfilGrupo);
        perfilGrupo.setPerfil(perfilUsuario);
        perfilGrupo.getGrupo().addUsuarios(perfilGrupo);
	}

    public void criaPerfilModerador(Long idUsuario, Grupo grupo) throws Exception {
    	PerfilUsuario perfilUsuario = this.perfillUsuarioRepository.getOne(idUsuario);
    	final PerfilGrupo perfilGrupo = new PerfilGrupo();
    	perfilGrupo.setGrupo(grupo);
    	perfilGrupo.setModerador(true);
    	this.associaPerfilAGrupo(perfilGrupo, perfilUsuario);
        perfilGrupoRepository.save(perfilGrupo);    	
    }

    
    
    /**
     * Get all the perfilGrupos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilGrupoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PerfilGrupos");
        return perfilGrupoRepository.findAll(pageable)
            .map(perfilGrupoMapper::toDto);
    }


    /**
     * Get one perfilGrupo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilGrupoDTO> findOne(Long id) {
        log.debug("Request to get PerfilGrupo : {}", id);
        return perfilGrupoRepository.findById(id)
            .map(perfilGrupoMapper::toDto);
    }

    /**
     * Delete the perfilGrupo by id.
     *
     * @param id the id of the entity.
     * @throws Exception 
     */
    public void delete(Long id) throws Exception {
        log.debug("Request to delete PerfilGrupo : {}", id);
        PerfilGrupo perfilGrupo = this.perfilGrupoRepository.findById(id).get();
        PerfilUsuario perfil = perfilGrupo.getPerfil();
		if (!perfil.getUsuario().getLogin().equals(SecurityUtils.getCurrentUserLogin().get())) {
        	throw new Exception("Tentativa de alterar perfil de outro usuário");
        }
        perfil.removeGrupos(perfilGrupo);        
        perfillUsuarioRepository.save(perfil);
        perfilGrupoRepository.deleteById(id);
    }

	public List<PerfilGrupoDTO> findAllManagement() {
		PerfilUsuario perfilUsuario = perfilUsuarioLogado();
		Set<PerfilGrupo> perfilGrupos = perfilUsuario.getGrupos();
		List<Grupo> grupos = this.grupoRepository.findAll();
		Map<Grupo, PerfilGrupo> perfilGruposMap = perfilGrupos.stream().collect(Collectors.toMap(perfil -> perfil.getGrupo(), grupo -> grupo));
		
		List<PerfilGrupoDTO> perfilsParaManagement = grupos.stream()
			.map(grupo -> {
				PerfilGrupo perfilGrupo = perfilGruposMap.get(grupo);
				if (perfilGrupo != null) {
					return perfilGrupoMapper.toDto(perfilGrupo);
				} else {
					PerfilGrupoDTO perfilGrupoDTO =  new PerfilGrupoDTO();
					perfilGrupoDTO.setGrupo(this.grupoMapper.toDto(grupo));
					return perfilGrupoDTO;
				}
			})
			.collect(Collectors.toList());
		return perfilsParaManagement;
	}

	private PerfilUsuario perfilUsuarioLogado() {
		PerfilUsuario perfilUsuario = this.userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getPerfilUsuario();
		return perfilUsuario;
	}

	public void criaPerfilObrigatorio(PerfilUsuario criado) {
		this.grupoRepository.findAll()
			.stream()
			.filter(Grupo::isAutomatico)
			.forEach(grupo -> {
				PerfilGrupo pf = new PerfilGrupo();
				pf.setPerfil(criado);
				pf.setGrupo(grupo);
				pf.setFavorito(false);
				pf.setModerador(false);
				try {
					this.associaPerfilAGrupo(pf, criado);
				} catch (Exception e) {
					// Não tem como rolar, já que o perfil acabou de ser criado...
					e.printStackTrace();
				}
				perfilGrupoRepository.save(pf);    	
			});
	}
}
