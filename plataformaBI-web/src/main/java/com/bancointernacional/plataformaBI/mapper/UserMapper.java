package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import com.bancointernacional.plataformaBI.domain.model.settings.SubsidiaryUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "userLastName", target = "lastName"),
            @Mapping(source = "userName", target = "name"),
            @Mapping(source = "userStatus", target = "status")
    })
    UserDTO toDTO(SubsidiaryUser subsidiaryUser);

    @Mappings({
            @Mapping(target = "userLastName", source = "lastName"),
            @Mapping(target = "userName", source = "name"),
            @Mapping(target = "userStatus", source = "status")
    })
    SubsidiaryUser toEntity(UserDTO dto);
}