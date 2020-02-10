package com.objective.informa.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.PerfilGrupo;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.service.dto.GrupoDTO;
import com.objective.informa.service.dto.SimpleUserDTO;

/**
 * Mapper for the entity {@link Grupo} and its DTO {@link GrupoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TopicoMapper.class})
public abstract class GrupoMapper implements EntityMapper<GrupoDTO, Grupo> {

	@Autowired SimpleUserMapper simpleUserMapper;
	@Autowired GrupoRepository grupoRepository;
	
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "removeUsuarios", ignore = true)
    @Mapping(target = "removeTopicos", ignore = true)
    public abstract Grupo toEntity(GrupoDTO grupoDTO);

    @Mapping(source = "usuarios", target = "moderadores", qualifiedByName = "moderadores")
    public abstract GrupoDTO toDto(Grupo grupo);

    public abstract void updateGrupoFromDto(GrupoDTO grupoDto, @MappingTarget Grupo grupo);
    
    @Named("moderadores")
    protected Set<SimpleUserDTO> moderadoresFromUsuarios(Set<PerfilGrupo> usuarios) {
    	return usuarios.stream()
    		.filter(perfilGrupo -> (perfilGrupo.isModerador() != null ) && (perfilGrupo.isModerador()))
    		.map(PerfilGrupo::getPerfil)
    		.map(PerfilUsuario::getUsuario)
    		.map(simpleUserMapper::toDto)
    		.collect(Collectors.toSet());
    }
    
    public Grupo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grupo grupo = new Grupo();
        grupo.setId(id);
        return grupo;
    }

}
