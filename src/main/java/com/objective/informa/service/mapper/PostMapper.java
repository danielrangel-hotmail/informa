package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.dto.PostDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArquivoMapper.class, LinkExternoMapper.class})
public abstract class PostMapper implements EntityMapper<PostDTO, Post> {

    @Autowired private GrupoRepository grupoRepository;
    @Autowired private UserRepository userRepository;

    @Mapping(source = "autor.id", target = "autorId")
    @Mapping(source = "autor.firstName", target = "autorNome")
    @Mapping(source = "grupo.id", target = "grupoId")
    @Mapping(source = "grupo.nome", target = "grupoNome")
    public abstract PostDTO toDto(Post post);

    @Mapping(target = "arquivos", ignore = true)
    @Mapping(target = "removeArquivos", ignore = true)
    @Mapping(target = "linksExternos", ignore = true)
    @Mapping(target = "removeLinksExternos", ignore = true)
    @Mapping(source = "autorId", target = "autor")
    @Mapping(source = "grupoId", target = "grupo")
    public abstract Post toEntity(PostDTO postDTO);

    public User autorFromId(Long id) {
        if (id == null) {
            return null;
        }
        return userRepository.findOneWithAuthoritiesById(id).get();
    }

    public Grupo grupoFromId(Long id) {
        if (id == null) {
            return null;
        }
        return grupoRepository.findById(id).get();
    }

    public Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
