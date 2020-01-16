package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.repository.MensagemRepository;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.dto.LinkExternoDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link LinkExterno} and its DTO {@link LinkExternoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PostMapper.class, MensagemMapper.class})
public abstract class LinkExternoMapper implements EntityMapper<LinkExternoDTO, LinkExterno> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "mensagem.id", target = "mensagemId")
    public abstract LinkExternoDTO toDto(LinkExterno linkExterno);

    @Mapping(source = "usuarioId", target = "usuario", qualifiedByName = "usarioArquivo")
    @Mapping(source = "postId", target = "post", qualifiedByName = "postArquivo")
    @Mapping(source = "mensagemId", target = "mensagem", qualifiedByName = "mensagemArquivo")
    public abstract LinkExterno toEntity(LinkExternoDTO linkExternoDTO);

    @Named("usarioArquivo")
    public User usuarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        return userRepository.findOneWithAuthoritiesById(id).get();
    }

    @Named("mensagemArquivo")
    public Mensagem mensagemFromId(Long id) {
        if (id == null) {
            return null;
        }
        return mensagemRepository.findById(id).get();
    }

    @Named("postArquivo")
    public Post postFromId(Long id) {
        if (id == null) {
            return null;
        }
        return postRepository.findById(id).get();
    }

    public LinkExterno fromId(Long id) {
        if (id == null) {
            return null;
        }
        LinkExterno linkExterno = new LinkExterno();
        linkExterno.setId(id);
        return linkExterno;
    }
}
