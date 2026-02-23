package com.bancointernacional.plataformaBI.services.serviceInterface;

import java.util.List;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.entities.Answer;

public interface AnswerService {

    public AnswerDTO  findQuestionByAnswer(UUID idAnswer);

    public List<AnswerDTO> findAnswerByProcessQuestandQuestionnaire(UUID idQuest, Long idQuestionnaire);

    public List<AnswerDTO> findByIdProcessQuestionary(UUID idQuest);

    public List<AnswerDTO> findByProcessQuestionaryAndUser(UUID idQuest, UUID idUser );

    public List<AnswerDTO> findByProcessAndQuestion(UUID idQuest, Long idQuestion);

    public List<AnswerDTO> saveAnswer(AnswerDTO answerDTO);

    public AnswerDTO updateAnswer(AnswerDTO answerDTO);

    public AnswerDTO deleteAnswer(AnswerDTO answerDTO);

    public List<AnswerDTO> deleteAnswersByIdQuestAndIdQuestion(UUID idQuest, Long idQuestion);

    AnswerDTO updateOwnerInAnswer(AnswerDTO answerToUpdate);

    Integer countSolvedOrCompleteAnswersByIdQuest(UUID idQuest);

}
