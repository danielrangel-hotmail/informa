package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.repository.MensagemRepository;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.dto.ArquivoDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Arquivo} and its DTO {@link ArquivoDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class ArquivoMapper implements EntityMapper<ArquivoDTO, Arquivo> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "mensagem.id", target = "mensagemId")
    @Mapping(target = "s3PresignedURL", ignore = true)
    public abstract ArquivoDTO toDto(Arquivo arquivo);

    @Mapping(source = "usuarioId", target = "usuario", qualifiedByName = "usarioArquivo")
    @Mapping(source = "postId", target = "post", qualifiedByName = "postArquivo")
    @Mapping(source = "mensagemId", target = "mensagem", qualifiedByName = "mensagemArquivo")
    public abstract Arquivo toEntity(ArquivoDTO arquivoDTO);

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

    public Arquivo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Arquivo arquivo = new Arquivo();
        arquivo.setId(id);
        return arquivo;
    }

}
