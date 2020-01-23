package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.service.dto.PerfilUsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerfilUsuario} and its DTO {@link PerfilUsuarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PerfilUsuarioMapper extends EntityMapper<PerfilUsuarioDTO, PerfilUsuario> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    PerfilUsuarioDTO toDto(PerfilUsuario perfilUsuario);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "removeSubscriptions", ignore = true)
    PerfilUsuario toEntity(PerfilUsuarioDTO perfilUsuarioDTO);

    default PerfilUsuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        PerfilUsuario perfilUsuario = new PerfilUsuario();
        perfilUsuario.setId(id);
        return perfilUsuario;
    }
}
