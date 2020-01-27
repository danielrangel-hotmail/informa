package com.objective.informa.service.mapper;

import com.objective.informa.domain.Grupo;
import com.objective.informa.service.dto.GrupoDTO;
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
public class GrupoMapperImpl extends GrupoMapper {

    @Override
    public GrupoDTO toDto(Grupo entity) {
        if ( entity == null ) {
            return null;
        }

        GrupoDTO grupoDTO = new GrupoDTO();

        grupoDTO.setId( entity.getId() );
        grupoDTO.setVersao( entity.getVersao() );
        grupoDTO.setCriacao( entity.getCriacao() );
        grupoDTO.setUltimaEdicao( entity.getUltimaEdicao() );
        grupoDTO.setNome( entity.getNome() );
        grupoDTO.setDescricao( entity.getDescricao() );
        grupoDTO.setFormal( entity.isFormal() );
        grupoDTO.setOpcional( entity.isOpcional() );

        return grupoDTO;
    }

    @Override
    public List<Grupo> toEntity(List<GrupoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Grupo> list = new ArrayList<Grupo>( dtoList.size() );
        for ( GrupoDTO grupoDTO : dtoList ) {
            list.add( toEntity( grupoDTO ) );
        }

        return list;
    }

    @Override
    public List<GrupoDTO> toDto(List<Grupo> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GrupoDTO> list = new ArrayList<GrupoDTO>( entityList.size() );
        for ( Grupo grupo : entityList ) {
            list.add( toDto( grupo ) );
        }

        return list;
    }

    @Override
    public Grupo toEntity(GrupoDTO grupoDTO) {
        if ( grupoDTO == null ) {
            return null;
        }

        Grupo grupo = new Grupo();

        grupo.setId( grupoDTO.getId() );
        grupo.setVersao( grupoDTO.getVersao() );
        grupo.setCriacao( grupoDTO.getCriacao() );
        grupo.setUltimaEdicao( grupoDTO.getUltimaEdicao() );
        grupo.setNome( grupoDTO.getNome() );
        grupo.setDescricao( grupoDTO.getDescricao() );
        grupo.setFormal( grupoDTO.isFormal() );
        grupo.setOpcional( grupoDTO.isOpcional() );

        return grupo;
    }
}
