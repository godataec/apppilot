package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.period.process.response.ProcessDTO;
import com.bancointernacional.plataformaBI.domain.model.period.Process;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ProcessMapper {

    @Mappings({
            @Mapping(source = "processId", target = "processId"),
            @Mapping(source = "processDescription", target = "processName"),
            @Mapping(source = "startDate", target = "processStartDate"),
            @Mapping(source = "endDate", target = "processEndDate"),
            @Mapping(source = "processStatus", target = "processStatus"),
            @Mapping(source = "reopened", target = "reopened")
    })
    ProcessDTO toDTO(Process process);

    @Mappings({
            @Mapping(target = "processId", source = "processId"),
            @Mapping(target = "processDescription", source = "processName"),
            @Mapping(target = "startDate", source = "processStartDate"),
            @Mapping(target = "endDate", source = "processEndDate"),
            @Mapping(target = "processStatus", source = "processStatus"),
            @Mapping(target = "isDeleted", constant = "false"),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "reopened", constant = "false"),
            @Mapping(target = "subsidiaryId", ignore = true)
    })
    Process toEntity(ProcessDTO dto);

    // Convert LocalDateTime to LocalDate (for entity to DTO)
    default LocalDate map(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }

    // Convert LocalDate to LocalDateTime (for DTO to entity)
    // Sets time to start of day (00:00:00)
    default LocalDateTime map(LocalDate date) {
        return date != null ? date.atStartOfDay() : null;
    }
}
