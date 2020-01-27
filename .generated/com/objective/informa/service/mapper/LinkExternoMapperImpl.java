package com.objective.informa.service.mapper;

import com.objective.informa.domain.LinkExterno;
import com.objective.informa.domain.Mensagem;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.service.dto.LinkExternoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-25T14:55:41-0300",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.20.0.v20191203-2131, environment: Java 11.0.5 (AdoptOpenJDK)"
)
@Component
public class LinkExternoMapperImpl extends LinkExternoMapper {

    @Override
    public List<LinkExterno> toEntity(List<LinkExternoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<LinkExterno> list = new ArrayList<LinkExterno>( dtoList.size() );
        for ( LinkExternoDTO linkExternoDTO : dtoList ) {
            list.add( toEntity( linkExternoDTO ) );
        }

        return list;
    }

    @Override
    public List<LinkExternoDTO> toDto(List<LinkExterno> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LinkExternoDTO> list = new ArrayList<LinkExternoDTO>( entityList.size() );
        for ( LinkExterno linkExterno : entityList ) {
            list.add( toDto( linkExterno ) );
        }

        return list;
    }

    @Override
    public LinkExternoDTO toDto(LinkExterno linkExterno) {
        if ( linkExterno == null ) {
            return null;
        }

        LinkExternoDTO linkExternoDTO = new LinkExternoDTO();

        linkExternoDTO.setPostId( linkExternoPostId( linkExterno ) );
        linkExternoDTO.setMensagemId( linkExternoMensagemId( linkExterno ) );
        linkExternoDTO.setUsuarioId( linkExternoUsuarioId( linkExterno ) );
        linkExternoDTO.setId( linkExterno.getId() );
        linkExternoDTO.setVersao( linkExterno.getVersao() );
        linkExternoDTO.setCriacao( linkExterno.getCriacao() );
        linkExternoDTO.setUltimaEdicao( linkExterno.getUltimaEdicao() );
        linkExternoDTO.setTipo( linkExterno.getTipo() );
        linkExternoDTO.setLink( linkExterno.getLink() );

        return linkExternoDTO;
    }

    @Override
    public LinkExterno toEntity(LinkExternoDTO linkExternoDTO) {
        if ( linkExternoDTO == null ) {
            return null;
        }

        LinkExterno linkExterno = new LinkExterno();

        linkExterno.setUsuario( usuarioFromId( linkExternoDTO.getUsuarioId() ) );
        linkExterno.setPost( postFromId( linkExternoDTO.getPostId() ) );
        linkExterno.setMensagem( mensagemFromId( linkExternoDTO.getMensagemId() ) );
        linkExterno.setId( linkExternoDTO.getId() );
        linkExterno.setVersao( linkExternoDTO.getVersao() );
        linkExterno.setCriacao( linkExternoDTO.getCriacao() );
        linkExterno.setUltimaEdicao( linkExternoDTO.getUltimaEdicao() );
        linkExterno.setTipo( linkExternoDTO.getTipo() );
        linkExterno.setLink( linkExternoDTO.getLink() );

        return linkExterno;
    }

    private Long linkExternoPostId(LinkExterno linkExterno) {
        if ( linkExterno == null ) {
            return null;
        }
        Post post = linkExterno.getPost();
        if ( post == null ) {
            return null;
        }
        Long id = post.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long linkExternoMensagemId(LinkExterno linkExterno) {
        if ( linkExterno == null ) {
            return null;
        }
        Mensagem mensagem = linkExterno.getMensagem();
        if ( mensagem == null ) {
            return null;
        }
        Long id = mensagem.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long linkExternoUsuarioId(LinkExterno linkExterno) {
        if ( linkExterno == null ) {
            return null;
        }
        User usuario = linkExterno.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        Long id = usuario.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
