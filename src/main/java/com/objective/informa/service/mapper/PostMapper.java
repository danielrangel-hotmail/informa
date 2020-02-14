package com.objective.informa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.repository.GrupoRepository;
import com.objective.informa.repository.MensagemRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.PostReacaoService;
import com.objective.informa.service.dto.PostDTO;
import com.objective.informa.service.dto.PostReacoesDTO;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArquivoMapper.class, LinkExternoMapper.class, PostReacaoMapper.class})
public abstract class PostMapper implements EntityMapper<PostDTO, Post> {

    @Autowired private GrupoRepository grupoRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private MensagemRepository mensagemRepository;
    @Autowired private PostReacaoService postReacaoService;
   

    @Mapping(source = "autor.id", target = "autorId")
    @Mapping(source = "autor.firstName", target = "autorNome")
    @Mapping(source = "autor.email", target = "autorEmail")
    @Mapping(source = "grupo.id", target = "grupoId")
    @Mapping(source = "grupo.nome", target = "grupoNome")
    @Mapping(source = "id", target = "numeroDeMensagens", qualifiedByName = "numeroDeMensagens")
    @Mapping(source = "id", target = "reacoes", qualifiedByName = "reacoesDoPost")
    public abstract PostDTO toDto(Post post);

    @Mapping(target = "arquivos", ignore = true)
    @Mapping(target = "removeArquivos", ignore = true)
    @Mapping(target = "linksExternos", ignore = true)
    @Mapping(target = "removeLinksExternos", ignore = true)
    @Mapping(source = "autorId", target = "autor")
    @Mapping(source = "grupoId", target = "grupo")
    public abstract Post toEntity(PostDTO postDTO);

    @Named("numeroDeMensagens")
    public Long numeroDeMensagensFromId(Long id) {
        return this.mensagemRepository.countByPostId(id);
    }

    @Named("reacoesDoPost")
    public PostReacoesDTO reacoesDoPost(Long id) {
    	return this.postReacaoService.postReacoesDTOFromPostId(id);
    }

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
