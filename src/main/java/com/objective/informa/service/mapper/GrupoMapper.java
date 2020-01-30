package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.service.dto.GrupoDTO;
import com.objective.informa.service.dto.PerfilUsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Grupo} and its DTO {@link GrupoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TopicoMapper.class})
public abstract class GrupoMapper implements EntityMapper<GrupoDTO, Grupo> {

    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "removeUsuarios", ignore = true)
    @Mapping(target = "removeTopicos", ignore = true)
    public abstract Grupo toEntity(GrupoDTO grupoDTO);

    public Grupo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grupo grupo = new Grupo();
        grupo.setId(id);
        return grupo;
    }
}
