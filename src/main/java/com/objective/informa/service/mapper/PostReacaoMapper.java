package com.objective.informa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.PostReacao;
import com.objective.informa.repository.PostRepository;
import com.objective.informa.service.dto.PostReacaoDTO;

/**
 * Mapper for the entity {@link PostReacao} and its DTO {@link PostReacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilUsuarioMapper.class, PostMapper.class})
public abstract class PostReacaoMapper implements EntityMapper<PostReacaoDTO, PostReacao> {

	@Autowired private PostRepository postRepository; 
    @Mapping(source = "perfil.id", target = "perfilId")
    @Mapping(source = "perfil", target = "perfilNome")
    @Mapping(source = "post.id", target = "postId")
    public abstract PostReacaoDTO toDto(PostReacao postReacao);

    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(source = "postId", target = "post", qualifiedByName = "postById")
    public abstract PostReacao toEntity(PostReacaoDTO postReacaoDTO);

    public String perfilNomeFromPerfil(PerfilUsuario perfil) {
    	return perfil.getUsuario().getFirstName() + " " + perfil.getUsuario().getLastName(); 
    }
    
    @Named("postById")
    public Post postById(Long id ) {
    	return this.postRepository.findById(id).orElse(null);
    }
    
    public PostReacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostReacao postReacao = new PostReacao();
        postReacao.setId(id);
        return postReacao;
    }
}
