package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.dto.PerfilUsuarioDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link PerfilUsuario} and its DTO {@link PerfilUsuarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class PerfilUsuarioMapper implements EntityMapper<PerfilUsuarioDTO, PerfilUsuario> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    public abstract PerfilUsuarioDTO toDto(PerfilUsuario perfilUsuario);

    @Autowired
    private UserRepository userRepository;

    @Mapping(source = "usuarioId", target = "usuario", qualifiedByName = "usuarioPerfil")
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "removeSubscriptions", ignore = true)
    @Mapping(target = "grupos", ignore = true)
    @Mapping(target = "removeGrupos", ignore = true)
    public abstract PerfilUsuario toEntity(PerfilUsuarioDTO perfilUsuarioDTO);

    @Named("usuarioPerfil")
    public User usuarioPerfilFromId(Long id) {
        if (id == null) {
            return null;
        }
        return userRepository.findOneWithAuthoritiesById(id).get();
    }

    public PerfilUsuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        PerfilUsuario perfilUsuario = new PerfilUsuario();
        perfilUsuario.setId(id);
        return perfilUsuario;
    }
}
