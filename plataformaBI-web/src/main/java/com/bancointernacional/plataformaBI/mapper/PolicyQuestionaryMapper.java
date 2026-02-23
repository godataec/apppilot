package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.request.CreateProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.response.PolicyQuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.model.period.PolicyQuestionary;
import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PolicyQuestionaryMapper {

    @Mappings({
            @Mapping(source = "policyQuestionaryId", target = "policyQuestionaryId"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "policyQuestionaryStatus", target = "status"),
            @Mapping(source = "questionaryId", target = "questionaryId"),
            @Mapping(source = "processPolicy.processPolicyId", target = "processPolicyId"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(target = "questionaryName", ignore = true)
    })
    PolicyQuestionaryDTO toDTO(PolicyQuestionary policyQuestionary);

    @Mappings({
            @Mapping(target = "policyQuestionaryId", ignore = true),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "status", target = "policyQuestionaryStatus"),
            @Mapping(source = "questionaryId", target = "questionaryId"),
            @Mapping(source = "processPolicyId", target = "processPolicy", qualifiedByName = "uuidToProcessPolicy"),
            @Mapping(target = "isDeleted", constant = "false"),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDate.now())")
    })
    PolicyQuestionary toEntity(CreateProcessQuestionaryDTO createDTO);

    @Named("uuidToProcessPolicy")
    default ProcessPolicy uuidToProcessPolicy(UUID processPolicyId) {
        if (processPolicyId == null) {
            return null;
        }
        ProcessPolicy processPolicy = new ProcessPolicy();
        processPolicy.setProcessPolicyId(processPolicyId);
        return processPolicy;
    }
}
