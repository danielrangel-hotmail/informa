package com.objective.informa.service.mapper;

import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.User;
import com.objective.informa.service.dto.PerfilUsuarioDTO;
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
public class PerfilUsuarioMapperImpl extends PerfilUsuarioMapper {

    @Override
    public List<PerfilUsuario> toEntity(List<PerfilUsuarioDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PerfilUsuario> list = new ArrayList<PerfilUsuario>( dtoList.size() );
        for ( PerfilUsuarioDTO perfilUsuarioDTO : dtoList ) {
            list.add( toEntity( perfilUsuarioDTO ) );
        }

        return list;
    }

    @Override
    public List<PerfilUsuarioDTO> toDto(List<PerfilUsuario> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PerfilUsuarioDTO> list = new ArrayList<PerfilUsuarioDTO>( entityList.size() );
        for ( PerfilUsuario perfilUsuario : entityList ) {
            list.add( toDto( perfilUsuario ) );
        }

        return list;
    }

    @Override
    public PerfilUsuarioDTO toDto(PerfilUsuario perfilUsuario) {
        if ( perfilUsuario == null ) {
            return null;
        }

        PerfilUsuarioDTO perfilUsuarioDTO = new PerfilUsuarioDTO();

        perfilUsuarioDTO.setUsuarioId( perfilUsuarioUsuarioId( perfilUsuario ) );
        perfilUsuarioDTO.setId( perfilUsuario.getId() );
        perfilUsuarioDTO.setCriacao( perfilUsuario.getCriacao() );
        perfilUsuarioDTO.setUltimaEdicao( perfilUsuario.getUltimaEdicao() );
        perfilUsuarioDTO.setVersao( perfilUsuario.getVersao() );
        perfilUsuarioDTO.setEntradaNaEmpresa( perfilUsuario.getEntradaNaEmpresa() );
        perfilUsuarioDTO.setSaidaDaEmpresa( perfilUsuario.getSaidaDaEmpresa() );
        perfilUsuarioDTO.setNascimento( perfilUsuario.getNascimento() );
        perfilUsuarioDTO.setSkype( perfilUsuario.getSkype() );

        return perfilUsuarioDTO;
    }

    @Override
    public PerfilUsuario toEntity(PerfilUsuarioDTO perfilUsuarioDTO) {
        if ( perfilUsuarioDTO == null ) {
            return null;
        }

        PerfilUsuario perfilUsuario = new PerfilUsuario();

        perfilUsuario.setUsuario( usuarioPerfilFromId( perfilUsuarioDTO.getUsuarioId() ) );
        perfilUsuario.setId( perfilUsuarioDTO.getId() );
        perfilUsuario.setCriacao( perfilUsuarioDTO.getCriacao() );
        perfilUsuario.setUltimaEdicao( perfilUsuarioDTO.getUltimaEdicao() );
        perfilUsuario.setVersao( perfilUsuarioDTO.getVersao() );
        perfilUsuario.setEntradaNaEmpresa( perfilUsuarioDTO.getEntradaNaEmpresa() );
        perfilUsuario.setSaidaDaEmpresa( perfilUsuarioDTO.getSaidaDaEmpresa() );
        perfilUsuario.setNascimento( perfilUsuarioDTO.getNascimento() );
        perfilUsuario.setSkype( perfilUsuarioDTO.getSkype() );

        return perfilUsuario;
    }

    private Long perfilUsuarioUsuarioId(PerfilUsuario perfilUsuario) {
        if ( perfilUsuario == null ) {
            return null;
        }
        User usuario = perfilUsuario.getUsuario();
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
