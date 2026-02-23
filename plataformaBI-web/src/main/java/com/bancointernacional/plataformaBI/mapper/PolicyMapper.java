package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.template.policy.response.PolicyDTO;
import com.bancointernacional.plataformaBI.domain.model.template.Policy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PolicyMapper {

    @Mappings({
            @Mapping(source = "policyId", target = "idInsurance"),
            @Mapping(source = "policyName", target = "nameInsurance"),
            @Mapping(source = "policyStatus", target = "status"),
            @Mapping(source = "policyDescription", target = "description")
    })
    PolicyDTO toDTO(Policy policy);

    @Mappings({
            @Mapping(target = "policyId", source = "idInsurance"),
            @Mapping(target = "policyName", source = "nameInsurance"),
            @Mapping(target = "policyStatus", source = "status"),
            @Mapping(target = "policyDescription", source = "description")
    })
    Policy toEntity(PolicyDTO dto);
}
