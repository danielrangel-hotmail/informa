package com.objective.informa.service.mapper;

import com.objective.informa.domain.Mensagem;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.service.dto.MensagemDTO;
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
public class MensagemMapperImpl extends MensagemMapper {

    @Override
    public List<MensagemDTO> toDto(List<Mensagem> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<MensagemDTO> list = new ArrayList<MensagemDTO>( arg0.size() );
        for ( Mensagem mensagem : arg0 ) {
            list.add( toDto( mensagem ) );
        }

        return list;
    }

    @Override
    public List<Mensagem> toEntity(List<MensagemDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Mensagem> list = new ArrayList<Mensagem>( arg0.size() );
        for ( MensagemDTO mensagemDTO : arg0 ) {
            list.add( toEntity( mensagemDTO ) );
        }

        return list;
    }

    @Override
    public MensagemDTO toDto(Mensagem mensagem) {
        if ( mensagem == null ) {
            return null;
        }

        MensagemDTO mensagemDTO = new MensagemDTO();

        mensagemDTO.setConversaId( mensagemConversaId( mensagem ) );
        mensagemDTO.setAutorNome( mensagemAutorFirstName( mensagem ) );
        mensagemDTO.setPostId( mensagemPostId( mensagem ) );
        mensagemDTO.setAutorId( mensagemAutorId( mensagem ) );
        mensagemDTO.setAutorEmail( mensagemAutorEmail( mensagem ) );
        mensagemDTO.setConteudo( mensagem.getConteudo() );
        mensagemDTO.setCriacao( mensagem.getCriacao() );
        mensagemDTO.setId( mensagem.getId() );
        mensagemDTO.setTemConversa( mensagem.isTemConversa() );
        mensagemDTO.setUltimaEdicao( mensagem.getUltimaEdicao() );
        mensagemDTO.setVersao( mensagem.getVersao() );

        return mensagemDTO;
    }

    @Override
    public Mensagem toEntity(MensagemDTO mensagemDTO) {
        if ( mensagemDTO == null ) {
            return null;
        }

        Mensagem mensagem = new Mensagem();

        mensagem.setPost( postFromId( mensagemDTO.getPostId() ) );
        mensagem.setConversa( conversaFromId( mensagemDTO.getConversaId() ) );
        mensagem.setAutor( autorFromId( mensagemDTO.getAutorId() ) );
        mensagem.setConteudo( mensagemDTO.getConteudo() );
        mensagem.setCriacao( mensagemDTO.getCriacao() );
        mensagem.setId( mensagemDTO.getId() );
        mensagem.temConversa( mensagemDTO.isTemConversa() );
        mensagem.ultimaEdicao( mensagemDTO.getUltimaEdicao() );
        mensagem.versao( mensagemDTO.getVersao() );

        return mensagem;
    }

    private Long mensagemConversaId(Mensagem mensagem) {
        if ( mensagem == null ) {
            return null;
        }
        Mensagem conversa = mensagem.getConversa();
        if ( conversa == null ) {
            return null;
        }
        Long id = conversa.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String mensagemAutorFirstName(Mensagem mensagem) {
        if ( mensagem == null ) {
            return null;
        }
        User autor = mensagem.getAutor();
        if ( autor == null ) {
            return null;
        }
        String firstName = autor.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private Long mensagemPostId(Mensagem mensagem) {
        if ( mensagem == null ) {
            return null;
        }
        Post post = mensagem.getPost();
        if ( post == null ) {
            return null;
        }
        Long id = post.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long mensagemAutorId(Mensagem mensagem) {
        if ( mensagem == null ) {
            return null;
        }
        User autor = mensagem.getAutor();
        if ( autor == null ) {
            return null;
        }
        Long id = autor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String mensagemAutorEmail(Mensagem mensagem) {
        if ( mensagem == null ) {
            return null;
        }
        User autor = mensagem.getAutor();
        if ( autor == null ) {
            return null;
        }
        String email = autor.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
