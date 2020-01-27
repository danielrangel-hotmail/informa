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
    date = "2020-01-27T09:16:03-0300",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.20.0.v20191203-2131, environment: Java 11.0.5 (AdoptOpenJDK)"
)
@Component
public class ArquivoMapperImpl extends ArquivoMapper {

    @Override
    public List<ArquivoDTO> toDto(List<Arquivo> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ArquivoDTO> list = new ArrayList<ArquivoDTO>( arg0.size() );
        for ( Arquivo arquivo : arg0 ) {
            list.add( toDto( arquivo ) );
        }

        return list;
    }

    @Override
    public List<Arquivo> toEntity(List<ArquivoDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Arquivo> list = new ArrayList<Arquivo>( arg0.size() );
        for ( ArquivoDTO arquivoDTO : arg0 ) {
            list.add( toEntity( arquivoDTO ) );
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
        arquivoDTO.setColecao( arquivo.getColecao() );
        arquivoDTO.setCriacao( arquivo.getCriacao() );
        arquivoDTO.setId( arquivo.getId() );
        arquivoDTO.setLink( arquivo.getLink() );
        arquivoDTO.setNome( arquivo.getNome() );
        arquivoDTO.setTipo( arquivo.getTipo() );
        arquivoDTO.setUltimaEdicao( arquivo.getUltimaEdicao() );
        arquivoDTO.setUploadConfirmado( arquivo.isUploadConfirmado() );
        arquivoDTO.setVersao( arquivo.getVersao() );

        return arquivoDTO;
    }

    @Override
    public Arquivo toEntity(ArquivoDTO arquivoDTO) {
        if ( arquivoDTO == null ) {
            return null;
        }

        Arquivo arquivo = new Arquivo();

        arquivo.usuario( usuarioFromId( arquivoDTO.getUsuarioId() ) );
        arquivo.setPost( postFromId( arquivoDTO.getPostId() ) );
        arquivo.setMensagem( mensagemFromId( arquivoDTO.getMensagemId() ) );
        arquivo.setColecao( arquivoDTO.getColecao() );
        arquivo.setCriacao( arquivoDTO.getCriacao() );
        arquivo.setLink( arquivoDTO.getLink() );
        arquivo.setNome( arquivoDTO.getNome() );
        arquivo.setId( arquivoDTO.getId() );
        arquivo.tipo( arquivoDTO.getTipo() );
        arquivo.ultimaEdicao( arquivoDTO.getUltimaEdicao() );
        arquivo.uploadConfirmado( arquivoDTO.isUploadConfirmado() );
        arquivo.versao( arquivoDTO.getVersao() );

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
