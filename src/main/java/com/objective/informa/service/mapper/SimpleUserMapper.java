package com.objective.informa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.objective.informa.domain.User;
import com.objective.informa.service.dto.SimpleUserDTO;


@Mapper(componentModel = "spring")
public interface SimpleUserMapper extends EntityMapper<SimpleUserDTO, User>{

	SimpleUserDTO toDto(User user);

	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	@Mapping(target = "perfilUsuario", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "activated", ignore = true)
	@Mapping(target = "activationKey", ignore = true)
	@Mapping(target = "resetKey", ignore = true)
	@Mapping(target = "resetDate", ignore = true)
	@Mapping(target = "imageUrl", ignore = true)
	@Mapping(target = "langKey", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	User toEntity(SimpleUserDTO user);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
