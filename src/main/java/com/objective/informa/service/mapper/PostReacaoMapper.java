package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.service.dto.PostReacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostReacao} and its DTO {@link PostReacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilUsuarioMapper.class, PostMapper.class})
public interface PostReacaoMapper extends EntityMapper<PostReacaoDTO, PostReacao> {

    @Mapping(source = "perfil.id", target = "perfilId")
    @Mapping(source = "post.id", target = "postId")
    PostReacaoDTO toDto(PostReacao postReacao);

    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(source = "postId", target = "post")
    PostReacao toEntity(PostReacaoDTO postReacaoDTO);

    default PostReacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostReacao postReacao = new PostReacao();
        postReacao.setId(id);
        return postReacao;
    }
}
