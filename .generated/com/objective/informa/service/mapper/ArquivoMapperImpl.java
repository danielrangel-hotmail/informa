package com.objective.informa.service.mapper;

import com.objective.informa.domain.Arquivo;
import com.objective.informa.domain.Mensagem;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.service.dto.ArquivoDTO;
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
public class ArquivoMapperImpl extends ArquivoMapper {

    @Override
    public List<Arquivo> toEntity(List<ArquivoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Arquivo> list = new ArrayList<Arquivo>( dtoList.size() );
        for ( ArquivoDTO arquivoDTO : dtoList ) {
            list.add( toEntity( arquivoDTO ) );
        }

        return list;
    }

    @Override
    public List<ArquivoDTO> toDto(List<Arquivo> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ArquivoDTO> list = new ArrayList<ArquivoDTO>( entityList.size() );
        for ( Arquivo arquivo : entityList ) {
            list.add( toDto( arquivo ) );
        }

        return list;
    }

    @Override
    public ArquivoDTO toDto(Arquivo arquivo) {
        if ( arquivo == null ) {
            return null;
        }

        ArquivoDTO arquivoDTO = new ArquivoDTO();

        arquivoDTO.setPostId( arquivoPostId( arquivo ) );
        arquivoDTO.setMensagemId( arquivoMensagemId( arquivo ) );
        arquivoDTO.setUsuarioId( arquivoUsuarioId( arquivo ) );
        arquivoDTO.setId( arquivo.getId() );
        arquivoDTO.setVersao( arquivo.getVersao() );
        arquivoDTO.setCriacao( arquivo.getCriacao() );
        arquivoDTO.setUltimaEdicao( arquivo.getUltimaEdicao() );
        arquivoDTO.setNome( arquivo.getNome() );
        arquivoDTO.setColecao( arquivo.getColecao() );
        arquivoDTO.setTipo( arquivo.getTipo() );
        arquivoDTO.setLink( arquivo.getLink() );
        arquivoDTO.setUploadConfirmado( arquivo.isUploadConfirmado() );

        return arquivoDTO;
    }

    @Override
    public Arquivo toEntity(ArquivoDTO arquivoDTO) {
        if ( arquivoDTO == null ) {
            return null;
        }

        Arquivo arquivo = new Arquivo();

        arquivo.setUsuario( usuarioFromId( arquivoDTO.getUsuarioId() ) );
        arquivo.setPost( postFromId( arquivoDTO.getPostId() ) );
        arquivo.setMensagem( mensagemFromId( arquivoDTO.getMensagemId() ) );
        arquivo.setId( arquivoDTO.getId() );
        arquivo.setVersao( arquivoDTO.getVersao() );
        arquivo.setCriacao( arquivoDTO.getCriacao() );
        arquivo.setUltimaEdicao( arquivoDTO.getUltimaEdicao() );
        arquivo.setNome( arquivoDTO.getNome() );
        arquivo.setColecao( arquivoDTO.getColecao() );
        arquivo.setTipo( arquivoDTO.getTipo() );
        arquivo.setLink( arquivoDTO.getLink() );
        arquivo.setUploadConfirmado( arquivoDTO.isUploadConfirmado() );

        return arquivo;
    }

    private Long arquivoPostId(Arquivo arquivo) {
        if ( arquivo == null ) {
            return null;
        }
        Post post = arquivo.getPost();
        if ( post == null ) {
            return null;
        }
        Long id = post.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long arquivoMensagemId(Arquivo arquivo) {
        if ( arquivo == null ) {
            return null;
        }
        Mensagem mensagem = arquivo.getMensagem();
        if ( mensagem == null ) {
            return null;
        }
        Long id = mensagem.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long arquivoUsuarioId(Arquivo arquivo) {
        if ( arquivo == null ) {
            return null;
        }
        User usuario = arquivo.getUsuario();
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
