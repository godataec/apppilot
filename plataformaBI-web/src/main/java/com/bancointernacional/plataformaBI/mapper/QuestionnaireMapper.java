package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.projection.QuestionnaireProjectionDTO;
import com.bancointernacional.plataformaBI.domain.projections.QuestionnaireProjection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionnaireMapper {

    /**
     * Convert a projection to a DTO
     *
     * @param projection The projection to convert
     * @return The resulting DTO
     */
    public QuestionnaireProjectionDTO toDto(QuestionnaireProjection projection) {
        if (projection == null) {
            return null;
        }

        return new QuestionnaireProjectionDTO(
                projection.getAssignedQuestionnaireID(),
                projection.getQuestionnaireName(),
                projection.getStatus(),
                projection.getDateStart(),
                projection.getDateEnd()
        );
    }

    /**
     * Convert a list of projections to a list of DTOs
     *
     * @param projections The projections to convert
     * @return The resulting list of DTOs
     */
    public List<QuestionnaireProjectionDTO> toDtoList(List<QuestionnaireProjection> projections) {
        return projections.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}