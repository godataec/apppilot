package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.response.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.math.BigInteger;

@Mapper(componentModel = "spring")
public interface ProcessPolicyMapper {
    @Mappings({
            @Mapping(source = "processPolicyId", target = "idPolicy"),
            @Mapping(source = "dateBegin", target = "dateBegin"),
            @Mapping(source = "dateEnd", target = "dateEnd"),
            @Mapping(source = "process.processId", target = "idProcess"),
            @Mapping(source = "insurancePolicy", target = "idInsurance", qualifiedByName = "bigIntegerToLong"),
            @Mapping(source = "processPolicyStatus", target = "status"),
            @Mapping(target = "namePolicy", ignore = true),
            @Mapping(target = "description", ignore = true),
            @Mapping(target = "descriptionProcess", ignore = true),
            @Mapping(target = "percentage", ignore = true)
    })
    ProcessPolicyDTO toDTO(ProcessPolicy policy);

    @Mappings({
            @Mapping(target = "processPolicyId", source = "idPolicy"),
            @Mapping(target = "dateBegin", source = "dateBegin"),
            @Mapping(target = "dateEnd", source = "dateEnd"),
            @Mapping(target = "process.processId", source = "idProcess"),
            @Mapping(target = "insurancePolicy", source = "idInsurance", qualifiedByName = "longToBigInteger"),
            @Mapping(target = "processPolicyStatus", source = "status"),
            @Mapping(target = "isDeleted", constant = "false"),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDate.now())")
    })
    ProcessPolicy toEntity(ProcessPolicyDTO dto);

    @Named("bigIntegerToLong")
    default Long bigIntegerToLong(BigInteger bigInteger) {
        return bigInteger != null ? bigInteger.longValue() : null;
    }

    @Named("longToBigInteger")
    default BigInteger longToBigInteger(Long value) {
        return value != null ? BigInteger.valueOf(value) : null;
    }
}
