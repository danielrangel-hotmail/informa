package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.repository.MensagemRepository;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.dto.MensagemDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Mensagem} and its DTO {@link MensagemDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class MensagemMapper implements EntityMapper<MensagemDTO, Mensagem> {

    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private MensagemRepository mensagemRepository;

    @Mapping(source = "autor.id", target = "autorId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "conversa.id", target = "conversaId")
    @Mapping(source = "autor.firstName", target = "autorNome")
    public abstract MensagemDTO toDto(Mensagem mensagem);

    @Mapping(target = "arquivos", ignore = true)
    @Mapping(target = "removeArquivos", ignore = true)
    @Mapping(source = "autorId", target = "autor")
    @Mapping(source = "postId", target = "post")
    @Mapping(source = "conversaId", target = "conversa", qualifiedByName = "conversaFromId")
    public abstract Mensagem toEntity(MensagemDTO mensagemDTO);

    public Post postFromId(Long id) {
        if (id == null) {
            return null;
        }
        return postRepository.getOne(id);
    }

    public User autorFromId(Long id) {
        if (id == null) {
            return null;
        }
        return userRepository.findOneWithAuthoritiesById(id).get();
    }

    @Named("conversaFromId")
    public Mensagem conversaFromId(Long id) {
        if (id == null) {
            return null;
        }
        return mensagemRepository.findById(id).get();
    }

    public Mensagem fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mensagem mensagem = new Mensagem();
        mensagem.setId(id);
        return mensagem;
    }
}
