package com.objective.informa.service.mapper;

import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.PushSubscription;
import com.objective.informa.service.dto.PushSubscriptionDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-25T14:55:41-0300",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.20.0.v20191203-2131, environment: Java 11.0.5 (AdoptOpenJDK)"
)
@Component
public class PushSubscriptionMapperImpl implements PushSubscriptionMapper {

    @Autowired
    private PerfilUsuarioMapper perfilUsuarioMapper;

    @Override
    public List<PushSubscription> toEntity(List<PushSubscriptionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PushSubscription> list = new ArrayList<PushSubscription>( dtoList.size() );
        for ( PushSubscriptionDTO pushSubscriptionDTO : dtoList ) {
            list.add( toEntity( pushSubscriptionDTO ) );
        }

        return list;
    }

    @Override
    public List<PushSubscriptionDTO> toDto(List<PushSubscription> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PushSubscriptionDTO> list = new ArrayList<PushSubscriptionDTO>( entityList.size() );
        for ( PushSubscription pushSubscription : entityList ) {
            list.add( toDto( pushSubscription ) );
        }

        return list;
    }

    @Override
    public PushSubscriptionDTO toDto(PushSubscription pushSubscription) {
        if ( pushSubscription == null ) {
            return null;
        }

        PushSubscriptionDTO pushSubscriptionDTO = new PushSubscriptionDTO();

        pushSubscriptionDTO.setPerfilId( pushSubscriptionPerfilId( pushSubscription ) );
        pushSubscriptionDTO.setId( pushSubscription.getId() );
        pushSubscriptionDTO.setVersao( pushSubscription.getVersao() );
        pushSubscriptionDTO.setCriacao( pushSubscription.getCriacao() );
        pushSubscriptionDTO.setEndpoint( pushSubscription.getEndpoint() );
        pushSubscriptionDTO.setKey( pushSubscription.getKey() );
        pushSubscriptionDTO.setAuth( pushSubscription.getAuth() );

        return pushSubscriptionDTO;
    }

    @Override
    public PushSubscription toEntity(PushSubscriptionDTO pushSubscriptionDTO) {
        if ( pushSubscriptionDTO == null ) {
            return null;
        }

        PushSubscription pushSubscription = new PushSubscription();

        pushSubscription.setPerfil( perfilUsuarioMapper.fromId( pushSubscriptionDTO.getPerfilId() ) );
        pushSubscription.setId( pushSubscriptionDTO.getId() );
        pushSubscription.setVersao( pushSubscriptionDTO.getVersao() );
        pushSubscription.setCriacao( pushSubscriptionDTO.getCriacao() );
        pushSubscription.setEndpoint( pushSubscriptionDTO.getEndpoint() );
        pushSubscription.setKey( pushSubscriptionDTO.getKey() );
        pushSubscription.setAuth( pushSubscriptionDTO.getAuth() );

        return pushSubscription;
    }

    private Long pushSubscriptionPerfilId(PushSubscription pushSubscription) {
        if ( pushSubscription == null ) {
            return null;
        }
        PerfilUsuario perfil = pushSubscription.getPerfil();
        if ( perfil == null ) {
            return null;
        }
        Long id = perfil.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
