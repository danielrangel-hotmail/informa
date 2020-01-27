package com.objective.informa.service.mapper;

import com.objective.informa.domain.PerfilGrupo;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.service.dto.PerfilGrupoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-26T18:09:21-0300",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.20.0.v20191203-2131, environment: Java 11.0.5 (AdoptOpenJDK)"
)
@Component
public class PerfilGrupoMapperImpl extends PerfilGrupoMapper {

    @Autowired
    private PerfilUsuarioMapper perfilUsuarioMapper;
    @Autowired
    private GrupoMapper grupoMapper;

    @Override
    public List<PerfilGrupoDTO> toDto(List<PerfilGrupo> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PerfilGrupoDTO> list = new ArrayList<PerfilGrupoDTO>( arg0.size() );
        for ( PerfilGrupo perfilGrupo : arg0 ) {
            list.add( toDto( perfilGrupo ) );
        }

        return list;
    }

    @Override
    public List<PerfilGrupo> toEntity(List<PerfilGrupoDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PerfilGrupo> list = new ArrayList<PerfilGrupo>( arg0.size() );
        for ( PerfilGrupoDTO perfilGrupoDTO : arg0 ) {
            list.add( toEntity( perfilGrupoDTO ) );
        }

        return list;
    }

    @Override
    public PerfilGrupoDTO toDto(PerfilGrupo perfilGrupo) {
        if ( perfilGrupo == null ) {
            return null;
        }

        PerfilGrupoDTO perfilGrupoDTO = new PerfilGrupoDTO();

        perfilGrupoDTO.setPerfilId( perfilGrupoPerfilId( perfilGrupo ) );
        perfilGrupoDTO.setCriacao( perfilGrupo.getCriacao() );
        perfilGrupoDTO.setFavorito( perfilGrupo.isFavorito() );
        perfilGrupoDTO.setGrupo( grupoMapper.toDto( perfilGrupo.getGrupo() ) );
        perfilGrupoDTO.setId( perfilGrupo.getId() );
        perfilGrupoDTO.setNotifica( perfilGrupo.isNotifica() );
        perfilGrupoDTO.setUltimaEdicao( perfilGrupo.getUltimaEdicao() );
        perfilGrupoDTO.setVersao( perfilGrupo.getVersao() );

        return perfilGrupoDTO;
    }

    @Override
    public PerfilGrupo toEntity(PerfilGrupoDTO perfilGrupoDTO) {
        if ( perfilGrupoDTO == null ) {
            return null;
        }

        PerfilGrupo perfilGrupo = new PerfilGrupo();

        perfilGrupo.setGrupo( grupoFromGrupo( perfilGrupoDTO.getGrupo() ) );
        perfilGrupo.setPerfil( perfilUsuarioMapper.fromId( perfilGrupoDTO.getPerfilId() ) );
        perfilGrupo.setCriacao( perfilGrupoDTO.getCriacao() );
        perfilGrupo.setFavorito( perfilGrupoDTO.isFavorito() );
        perfilGrupo.setNotifica( perfilGrupoDTO.isNotifica() );
        perfilGrupo.setId( perfilGrupoDTO.getId() );
        perfilGrupo.ultimaEdicao( perfilGrupoDTO.getUltimaEdicao() );
        perfilGrupo.versao( perfilGrupoDTO.getVersao() );

        return perfilGrupo;
    }

    private Long perfilGrupoPerfilId(PerfilGrupo perfilGrupo) {
        if ( perfilGrupo == null ) {
            return null;
        }
        PerfilUsuario perfil = perfilGrupo.getPerfil();
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
