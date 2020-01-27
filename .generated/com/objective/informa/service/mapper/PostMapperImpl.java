package com.objective.informa.service.mapper;

import com.objective.informa.domain.Arquivo;
import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.LinkExterno;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.service.dto.ArquivoDTO;
import com.objective.informa.service.dto.LinkExternoDTO;
import com.objective.informa.service.dto.PostDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-25T14:55:41-0300",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.20.0.v20191203-2131, environment: Java 11.0.5 (AdoptOpenJDK)"
)
@Component
public class PostMapperImpl extends PostMapper {

    @Autowired
    private ArquivoMapper arquivoMapper;
    @Autowired
    private LinkExternoMapper linkExternoMapper;

    @Override
    public List<Post> toEntity(List<PostDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Post> list = new ArrayList<Post>( dtoList.size() );
        for ( PostDTO postDTO : dtoList ) {
            list.add( toEntity( postDTO ) );
        }

        return list;
    }

    @Override
    public List<PostDTO> toDto(List<Post> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PostDTO> list = new ArrayList<PostDTO>( entityList.size() );
        for ( Post post : entityList ) {
            list.add( toDto( post ) );
        }

        return list;
    }

    @Override
    public PostDTO toDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setNumeroDeMensagens( numeroDeMensagensFromId( post.getId() ) );
        postDTO.setAutorNome( postAutorFirstName( post ) );
        postDTO.setAutorId( postAutorId( post ) );
        postDTO.setGrupoNome( postGrupoNome( post ) );
        postDTO.setAutorEmail( postAutorEmail( post ) );
        postDTO.setGrupoId( postGrupoId( post ) );
        postDTO.setId( post.getId() );
        postDTO.setVersao( post.getVersao() );
        postDTO.setCriacao( post.getCriacao() );
        postDTO.setUltimaEdicao( post.getUltimaEdicao() );
        postDTO.setConteudo( post.getConteudo() );
        postDTO.setPublicacao( post.getPublicacao() );
        postDTO.setOficial( post.isOficial() );
        postDTO.setArquivos( arquivoSetToArquivoDTOList( post.getArquivos() ) );
        postDTO.setLinksExternos( linkExternoSetToLinkExternoDTOList( post.getLinksExternos() ) );

        return postDTO;
    }

    @Override
    public Post toEntity(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setGrupo( grupoFromId( postDTO.getGrupoId() ) );
        post.setAutor( autorFromId( postDTO.getAutorId() ) );
        post.setId( postDTO.getId() );
        post.setVersao( postDTO.getVersao() );
        post.setCriacao( postDTO.getCriacao() );
        post.setUltimaEdicao( postDTO.getUltimaEdicao() );
        post.setConteudo( postDTO.getConteudo() );
        post.setOficial( postDTO.isOficial() );
        post.setPublicacao( postDTO.getPublicacao() );

        return post;
    }

    private String postAutorFirstName(Post post) {
        if ( post == null ) {
            return null;
        }
        User autor = post.getAutor();
        if ( autor == null ) {
            return null;
        }
        String firstName = autor.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private Long postAutorId(Post post) {
        if ( post == null ) {
            return null;
        }
        User autor = post.getAutor();
        if ( autor == null ) {
            return null;
        }
        Long id = autor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String postGrupoNome(Post post) {
        if ( post == null ) {
            return null;
        }
        Grupo grupo = post.getGrupo();
        if ( grupo == null ) {
            return null;
        }
        String nome = grupo.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private String postAutorEmail(Post post) {
        if ( post == null ) {
            return null;
        }
        User autor = post.getAutor();
        if ( autor == null ) {
            return null;
        }
        String email = autor.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private Long postGrupoId(Post post) {
        if ( post == null ) {
            return null;
        }
        Grupo grupo = post.getGrupo();
        if ( grupo == null ) {
            return null;
        }
        Long id = grupo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected List<ArquivoDTO> arquivoSetToArquivoDTOList(Set<Arquivo> set) {
        if ( set == null ) {
            return null;
        }

        List<ArquivoDTO> list = new ArrayList<ArquivoDTO>( set.size() );
        for ( Arquivo arquivo : set ) {
            list.add( arquivoMapper.toDto( arquivo ) );
        }

        return list;
    }

    protected List<LinkExternoDTO> linkExternoSetToLinkExternoDTOList(Set<LinkExterno> set) {
        if ( set == null ) {
            return null;
        }

        List<LinkExternoDTO> list = new ArrayList<LinkExternoDTO>( set.size() );
        for ( LinkExterno linkExterno : set ) {
            list.add( linkExternoMapper.toDto( linkExterno ) );
        }

        return list;
    }
}
