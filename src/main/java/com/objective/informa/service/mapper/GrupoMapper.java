package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.service.dto.GrupoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Grupo} and its DTO {@link GrupoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public abstract class GrupoMapper implements EntityMapper<GrupoDTO, Grupo> {



    public Grupo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grupo grupo = new Grupo();
        grupo.setId(id);
        return grupo;
    }
}
