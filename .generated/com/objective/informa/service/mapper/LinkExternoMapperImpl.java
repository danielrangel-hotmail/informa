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
    date = "2020-01-27T09:16:03-0300",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.20.0.v20191203-2131, environment: Java 11.0.5 (AdoptOpenJDK)"
)
@Component
public class LinkExternoMapperImpl extends LinkExternoMapper {

    @Override
    public List<LinkExternoDTO> toDto(List<LinkExterno> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<LinkExternoDTO> list = new ArrayList<LinkExternoDTO>( arg0.size() );
        for ( LinkExterno linkExterno : arg0 ) {
            list.add( toDto( linkExterno ) );
        }

        return list;
    }

    @Override
    public List<LinkExterno> toEntity(List<LinkExternoDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<LinkExterno> list = new ArrayList<LinkExterno>( arg0.size() );
        for ( LinkExternoDTO linkExternoDTO : arg0 ) {
            list.add( toEntity( linkExternoDTO ) );
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
        linkExternoDTO.setCriacao( linkExterno.getCriacao() );
        linkExternoDTO.setId( linkExterno.getId() );
        linkExternoDTO.setLink( linkExterno.getLink() );
        linkExternoDTO.setTipo( linkExterno.getTipo() );
        linkExternoDTO.setUltimaEdicao( linkExterno.getUltimaEdicao() );
        linkExternoDTO.setVersao( linkExterno.getVersao() );

        return linkExternoDTO;
    }

    @Override
    public LinkExterno toEntity(LinkExternoDTO linkExternoDTO) {
        if ( linkExternoDTO == null ) {
            return null;
        }

        LinkExterno linkExterno = new LinkExterno();

        linkExterno.usuario( usuarioFromId( linkExternoDTO.getUsuarioId() ) );
        linkExterno.setPost( postFromId( linkExternoDTO.getPostId() ) );
        linkExterno.setMensagem( mensagemFromId( linkExternoDTO.getMensagemId() ) );
        linkExterno.setCriacao( linkExternoDTO.getCriacao() );
        linkExterno.setLink( linkExternoDTO.getLink() );
        linkExterno.setId( linkExternoDTO.getId() );
        linkExterno.tipo( linkExternoDTO.getTipo() );
        linkExterno.ultimaEdicao( linkExternoDTO.getUltimaEdicao() );
        linkExterno.versao( linkExternoDTO.getVersao() );

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
