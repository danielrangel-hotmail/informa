package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.repository.TopicoRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.dto.TopicoDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Topico} and its DTO {@link TopicoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class TopicoMapper implements EntityMapper<TopicoDTO, Topico> {

	
    @Autowired private UserRepository userRepository;
    @Autowired private TopicoRepository topicoRepository;
    
    @Mapping(source = "autor.id", target = "autorId", qualifiedByName = "autor")
    @Mapping(source = "substituto.id", target = "substitutoId", qualifiedByName = "substituido")
    public abstract TopicoDTO toDto(Topico topico);

    @Mapping(target = "substituidos", ignore = true)
    @Mapping(target = "removeSubstituidos", ignore = true)
    @Mapping(source = "autorId", target = "autor")
    @Mapping(source = "substitutoId", target = "substituto")
    @Mapping(target = "grupos", ignore = true)
    @Mapping(target = "removeGrupos", ignore = true)
    public abstract Topico toEntity(TopicoDTO topicoDTO);

    @Named("autor")
    public User autorFromId(Long id) {
        if (id == null) {
            return null;
        }
        return userRepository.findOneWithAuthoritiesById(id).get();
    }

    @Named("substituido")
    public Topico substituidoFromId(Long id) {
        if (id == null) {
            return null;
        }
        return topicoRepository.findById(id).get();
    }

    
    public Topico fromId(Long id) {
        if (id == null) {
            return null;
        }
        Topico topico = new Topico();
        topico.setId(id);
        return topico;
    }
}
