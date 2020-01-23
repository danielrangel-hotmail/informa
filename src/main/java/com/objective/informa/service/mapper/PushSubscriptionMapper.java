package com.objective.informa.service.mapper;

import com.objective.informa.domain.*;
import com.objective.informa.service.dto.PushSubscriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PushSubscription} and its DTO {@link PushSubscriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilUsuarioMapper.class})
public interface PushSubscriptionMapper extends EntityMapper<PushSubscriptionDTO, PushSubscription> {

    @Mapping(source = "perfil.id", target = "perfilId")
    PushSubscriptionDTO toDto(PushSubscription pushSubscription);

    @Mapping(source = "perfilId", target = "perfil")
    PushSubscription toEntity(PushSubscriptionDTO pushSubscriptionDTO);

    default PushSubscription fromId(Long id) {
        if (id == null) {
            return null;
        }
        PushSubscription pushSubscription = new PushSubscription();
        pushSubscription.setId(id);
        return pushSubscription;
    }
}
