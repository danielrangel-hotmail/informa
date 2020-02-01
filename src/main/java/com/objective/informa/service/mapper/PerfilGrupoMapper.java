package com.objective.informa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.PerfilGrupo;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.service.dto.GrupoDTO;
import com.objective.informa.service.dto.PerfilGrupoDTO;

/**
 * Mapper for the entity {@link PerfilGrupo} and its DTO {@link PerfilGrupoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilUsuarioMapper.class, GrupoMapper.class})
public abstract class PerfilGrupoMapper implements EntityMapper<PerfilGrupoDTO, PerfilGrupo> {

	@Autowired
	private GrupoRepository grupoRepository;


    @Mapping(source = "perfil.id", target = "perfilId")
    public abstract PerfilGrupoDTO toDto(PerfilGrupo perfilGrupo);

    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(source = "grupo", target = "grupo", qualifiedByName = "grupoPerfilGrupo")
    public abstract PerfilGrupo toEntity(PerfilGrupoDTO perfilGrupoDTO);

    @Named("grupoPerfilGrupo")
    public Grupo grupoFromGrupo(GrupoDTO grupoDTO) {
    	return grupoRepository.findById(grupoDTO.getId()).get();
    }


    public PerfilGrupo fromId(Long id) {
        if (id == null) {
            return null;
        }
        PerfilGrupo perfilGrupo = new PerfilGrupo();
        perfilGrupo.setId(id);
        return perfilGrupo;
    }
}
