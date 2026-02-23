package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.template.question.option.response.QuestionOptionDTO;
import com.bancointernacional.plataformaBI.domain.model.template.QuestionOption;
import com.bancointernacional.plataformaBI.repository.template.QuestionOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;

    /**
     * Get question options by question ID
     */
    public QuestionOptionDTO getQuestionOptionsByQuestionId(Integer questionId) {
        log.info("Fetching question options for question ID: {}", questionId);

        QuestionOption questionOptions = questionOptionRepository.findByQuestionId(questionId);

        log.info("Found {} question options for question ID: {}", questionOptions, questionId);

        return this.mapToDTO(questionOptions);
    }

    /**
     * Get question options by list of question IDs
     */
    public List<QuestionOptionDTO> getQuestionOptionsByQuestionIds(List<Integer> questionIds) {
        log.info("Fetching question options for {} question IDs", questionIds.size());

        List<QuestionOption> questionOptions = questionOptionRepository.findByQuestionIdIn(questionIds);

        log.info("Found {} total question options for provided question IDs", questionOptions.size());

        return questionOptions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Map QuestionOption entity to QuestionOptionDTO
     */
    private QuestionOptionDTO mapToDTO(QuestionOption questionOption) {
        return QuestionOptionDTO.builder()
                .questionId(questionOption.getQuestionId())
                .optionText(questionOption.getOptionText())
                .isDeleted(questionOption.getIsDeleted())
                .build();
    }
}
