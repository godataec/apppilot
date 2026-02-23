package com.bancointernacional.plataformaBI.services.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bancointernacional.plataformaBI.Enums.RoleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bancointernacional.plataformaBI.Enums.AnswerTypes;
import com.bancointernacional.plataformaBI.ErrorManagment.CustomException;
import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.entities.Answer;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import com.bancointernacional.plataformaBI.models.entities.Question;
import com.bancointernacional.plataformaBI.models.entities.User;
import com.bancointernacional.plataformaBI.repositories.AnswerRespository;
import com.bancointernacional.plataformaBI.repositories.ProcessQuestionaryRepository;
import com.bancointernacional.plataformaBI.repositories.QuestionRepository;
import com.bancointernacional.plataformaBI.repositories.UserRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final Logger logger = LogManager.getLogger(AnswerServiceImpl.class);


    @Autowired
    AnswerRespository answerRespository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ProcessQuestionaryRepository processQuestionaryRepository;


    @Override
    public AnswerDTO findQuestionByAnswer(UUID idAnswer) {

        Optional<Answer> answerFound = answerRespository.findByIdAnswer(idAnswer);

        if (answerFound.isPresent()) {
            return AnswerDTO.build(answerFound.get());
        }
        throw new CustomException("Respuesta no encontrada!", HttpStatus.NOT_FOUND);


    }

    @Override
    public List<AnswerDTO> findByIdProcessQuestionary(UUID idQuest) {

        return answerRespository.findAnswersByIdQuest(idQuest)
                .stream()
                .map(u -> AnswerDTO.build(u))
                .collect(Collectors.toList());
    }


    @Override
    public List<AnswerDTO> findAnswerByProcessQuestandQuestionnaire(UUID idQuest, Long idQuestionnaire) {
        return answerRespository.findAnswersByIdQuestAndIdQuestionnaire(idQuest.toString(), idQuestionnaire)
                .stream()
                .map(u -> AnswerDTO.build(u))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerDTO> findByProcessQuestionaryAndUser(UUID idQuest, UUID idUser) {

        return answerRespository.findAnswersByIdQuestAndIdUser(idQuest, idUser)
                .stream()
                .map(u -> AnswerDTO.build(u))
                .collect(Collectors.toList());

    }


    @Override
    public List<AnswerDTO> findByProcessAndQuestion(UUID idQuest, Long idQuestion) {

        int idQuestConverted = Math.toIntExact(idQuestion);
        List<Answer> answersFound = answerRespository.findByIdQuestAndIdQuestion(idQuest.toString(), idQuestion);
        List<AnswerDTO> questToSend = orderByTypeQuest(answersFound, idQuestConverted);

        return questToSend;

    }

    @Override
    public List<AnswerDTO> saveAnswer(AnswerDTO answerDTO) {
        Optional<User> userFound = userRepository.findByOpenIdUser(answerDTO.getIdUser().toString());
        Optional<ProcessQuestionary> processQuest = processQuestionaryRepository.findByIdQuest(answerDTO.getIdQuest().toString());
        Optional<Question> question = questionRepository.findById(answerDTO.getIdQuestion());
        LocalDateTime now = LocalDateTime.now();
        List<String> respCode = new ArrayList<>();
        if (question.isPresent()) {
            Question questionExtract = question.get();
            if (!questionExtract.getDocumentid().trim().isEmpty()) {
                respCode.add(question.get().getDocumentid());
            } else {
                respCode = getJsonSinceString(questionExtract);
            }
        }
        List<Answer> answersSaved = new ArrayList<>();
        for (String code : respCode) {
            Answer answerToSave = Answer.builder()
                    .answerText("")
                    .createdAt(now.toLocalDate())
                    .updatedAt(now.toLocalDate())
                    .status(answerDTO.getStatus())
                    .user(userFound.get())
                    .processQuest(processQuest.get())
                    .question(question.get())
                    .documentId(code)
                    .updatedBy(userFound.get().getOpenIdUser())
                    .build();
            answersSaved.add(answerRespository.save(answerToSave));
        }

        if (question.get().getTypequestion().equals(AnswerTypes.OPTIONALNORMAL.getWord()) || question.get().getTypequestion().equals(AnswerTypes.SELECTORNORMAL.getWord())) {
            List<Answer> tempoListAnswers = new ArrayList<>(answersSaved);

            for (Answer answer : tempoListAnswers) {
                Answer answerToSave = Answer.builder()
                        .answerText("")
                        .createdAt(now.toLocalDate())
                        .updatedAt(now.toLocalDate())
                        .status(answerDTO.getStatus())
                        .user(userFound.get())
                        .processQuest(processQuest.get())
                        .question(question.get())
                        .documentId(answer.getIdAnswer().toString())
                        .updatedBy(userFound.get().getOpenIdUser())
                        .build();
                answersSaved.add(answerRespository.save(answerToSave));
            }

        }

        List<AnswerDTO> answerToSend = orderByTypeQuest(answersSaved, answerDTO.getIdQuestion());

        return answerToSend;

    }

    @Override
    public AnswerDTO updateAnswer(AnswerDTO answerDTO) {
        Optional<Answer> AnswerFound = answerRespository.findByIdAnswer(answerDTO.getIdAnswer());
        LocalDateTime now = LocalDateTime.now();
        Optional<User> requestUser = userRepository.findByOpenIdUser(answerDTO.getUpdatedBy());
        if (AnswerFound.isPresent()) {
            Answer extractAnswer = AnswerFound.get();
            if (answerDTO.getIdUser().equals(extractAnswer.getUser().getOpenIdUser())
                    || (requestUser.get().getRole().toUpperCase().equals(RoleType.ADMIN.toString())
                    || requestUser.get().getRole().toUpperCase().equals(RoleType.SUPERADMIN.toString()))) {
                Answer answerToUpdate = AnswerFound.get();
                if (answerDTO.getUpdatedBy() == null) {
                    answerToUpdate.setUser(null);
                } else {
                    Optional<User> userFound = userRepository.findByOpenIdUser(answerDTO.getUpdatedBy());
                    if (userFound.isPresent()) {
                        answerToUpdate.setUpdatedBy(answerDTO.getUpdatedBy());
                    }
                }
                answerToUpdate.setProcessQuest(extractAnswer.getProcessQuest());
                answerToUpdate.setQuestion(extractAnswer.getQuestion());
                answerToUpdate.setAnswerText(answerDTO.getAnswerText().isEmpty() ? extractAnswer.getAnswerText() : answerDTO.getAnswerText() );
                answerToUpdate.setCreatedAt(extractAnswer.getCreatedAt());
                answerToUpdate.setUpdatedAt(now.toLocalDate());
                answerToUpdate.setStatus(answerDTO.getStatus().isEmpty() ?extractAnswer.getStatus() : answerDTO.getStatus());
                answerToUpdate.setDocumentId(extractAnswer.getDocumentId());
                return AnswerDTO.build(answerRespository.save(answerToUpdate));
            } else {
                logger.error("Usuario no asignado intenta actualizar respuesta");
                return null;
            }
        }
        throw new CustomException("Error, No se pudo actualizar la pregunta con ID:" + answerDTO.getIdAnswer(), HttpStatus.NOT_FOUND);
    }

    @Override
    public AnswerDTO deleteAnswer(AnswerDTO answerDTO) {
        Optional<Answer> answerFound = answerRespository.findByIdAnswer(answerDTO.getIdAnswer());

        if (answerFound.isPresent()) {
            answerRespository.delete(answerFound.get());
            return answerDTO;
        }


        throw new CustomException("Error al borrar la pregunta con id:" + answerDTO.getIdAnswer(), HttpStatus.NOT_FOUND);


    }

    @Override
    public List<AnswerDTO> deleteAnswersByIdQuestAndIdQuestion(UUID idQuest, Long idQuestion) {
        List<AnswerDTO> anwersFound = answerRespository.findByIdQuestAndIdQuestion(idQuest.toString(), idQuestion)
                .stream()
                .map(u -> AnswerDTO.build(u))
                .collect(Collectors.toList());

        if (!anwersFound.isEmpty()) {
            int questionsDeleted = answerRespository.deleteByIdQuestAndIdQuestion(idQuest.toString(), idQuestion);

            if (questionsDeleted > 0)
                return anwersFound;
            else
                return new ArrayList<>();
        }

        throw new CustomException("Error, no se pudo borrar la pregunta " + idQuestion, HttpStatus.NOT_FOUND);
    }

    @Override
    public AnswerDTO updateOwnerInAnswer(AnswerDTO answerDTO) {
        Optional<Answer> optionalAnswerFound = answerRespository.findByIdAnswer(answerDTO.getIdAnswer());
        Optional<User> requestUser = userRepository.findByOpenIdUser(answerDTO.getUpdatedBy());
        Optional<User> assignationUser = userRepository.findByOpenIdUser(answerDTO.getIdUser());
        if (optionalAnswerFound.isPresent()) {
            Answer answerFound = optionalAnswerFound.get();
            if (requestUser.get().getRole().toUpperCase().equals(RoleType.ADMIN.toString())
                    || requestUser.get().getRole().toUpperCase().equals(RoleType.SUPERADMIN.toString())) {
                if(assignationUser.isPresent()){
                    answerFound.setUser(assignationUser.get());
                    answerFound.setAnswerText("");
                }
                return AnswerDTO.build(answerRespository.save(answerFound));
            } else {
                logger.error("Usuario no cuenta con permisos para realizar esta accion");
                return null;
            }
        }
        throw new CustomException("Error, No se pudo actualizar la pregunta con ID:" + answerDTO.getIdAnswer(), HttpStatus.NOT_FOUND);
    }

    @Override
    public Integer countSolvedOrCompleteAnswersByIdQuest(UUID idQuest) {
        return answerRespository.countCompletedAnswersByQuestId(idQuest.toString());
    }

    public List<AnswerDTO> orderByTypeQuest(List<Answer> answersFound, int idQuestion) {


        Optional<Question> questionFound = questionRepository.findById(idQuestion);
        List<Answer> listToConverse = new ArrayList<>();
        List<AnswerDTO> questToSend = new ArrayList<>();
        if (questionFound.isPresent()) {
            if (questionFound.get().getTypequestion().equals(AnswerTypes.OPTIONALNORMAL.getWord()) || questionFound.get().getTypequestion().equals(AnswerTypes.SELECTORNORMAL.getWord())) {
                if (answersFound.size() == 2) {
                    List<String> getCodes = getJsonSinceString(questionFound.get());
                    if (answersFound.get(0).getDocumentId().equals(getCodes.get(0))) {
                        listToConverse.add(answersFound.get(0));
                        listToConverse.add(answersFound.get(1));
                    } else {
                        listToConverse.add(answersFound.get(1));
                        listToConverse.add(answersFound.get(0));
                    }
                }

            }

            if (questionFound.get().getTypequestion().equals(AnswerTypes.TABLE.getWord())) {
                List<String> foundCodes = getJsonSinceString(questionFound.get());
                for (String code : foundCodes) {
                    for (Answer getAnswer : answersFound) {
                        if (code.equals(getAnswer.getDocumentId().trim())) {
                            listToConverse.add(getAnswer);
                            break;
                        }
                    }
                }
            }
        }

        if (listToConverse.size() > 0) {
            for (Answer answer : listToConverse) {
                questToSend.add(AnswerDTO.build(answer));
            }
        } else {
            for (Answer answer : answersFound) {
                questToSend.add(AnswerDTO.build(answer));
            }
        }

        return questToSend;
    }

    public List<String> getJsonSinceString(Question question) {
        List<String> respCode = new ArrayList<>();
        try {
            JSONObject TypeRespJson = new JSONObject(question.getQuestionjson());
            Iterator<String> keys = TypeRespJson.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    JSONObject innerObject = TypeRespJson.getJSONObject(key);
                    if (innerObject.has("code")) {
                        String code = innerObject.getString("code");
                        respCode.add(code);
                    } else {
                        System.out.println("No se encontró la clave 'code' en el objeto: " + innerObject.toString());
                    }
                } catch (Exception e) {
                    throw new CustomException("Error al procesar la clave: " + key, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            throw new CustomException("Error, No se pudo convertir en Json la pregunta:" + question.getIdQuestion(), HttpStatus.NOT_FOUND);
        }
        return respCode;
    }

}
