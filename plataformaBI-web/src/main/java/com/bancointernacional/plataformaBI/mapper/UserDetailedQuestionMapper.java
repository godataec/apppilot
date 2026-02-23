package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.template.question.response.QuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.response.UserDetailedQuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import com.bancointernacional.plataformaBI.domain.model.period.UserQuestion;
import com.bancointernacional.plataformaBI.domain.model.settings.SubsidiaryUser;
import com.bancointernacional.plataformaBI.domain.model.template.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigInteger;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserDetailedQuestionMapper {

    @Mapping(source = "userQuestion.userQuestionId", target = "userQuestionId")
    @Mapping(source = "userQuestion.policyQuestionary.policyQuestionaryId", target = "policyQuestionaryId", qualifiedByName = "uuidToString")
    @Mapping(source = "question", target = "question")
    @Mapping(source = "user", target = "userAssigned")
    @Mapping(source = "userQuestion.questionStatus", target = "questionStatus")
    @Mapping(source = "userQuestion.deleted", target = "isDeleted")
    @Mapping(source = "userQuestion.createdAt", target = "createdAt")
    @Mapping(source = "userQuestion.updatedAt", target = "updatedAt")
    UserDetailedQuestionDTO toDetailedDTO(UserQuestion userQuestion, Question question, SubsidiaryUser user);

    @Mapping(source = "idQuestion", target = "idQuestion")
    @Mapping(source = "questionType", target = "typeQuestion")
    @Mapping(source = "description", target = "descriptionQuestion")
    @Mapping(source = "questionText", target = "questionText")
    @Mapping(source = "questionJson", target = "questionJson")
    @Mapping(source = "parentId", target = "parentId", qualifiedByName = "bigIntegerToInteger")
    @Mapping(source = "documentId", target = "documentId")
    @Mapping(source = "questionaryId", target = "idQuestionnaire", qualifiedByName = "bigIntegerToInteger")
    QuestionDTO questionToDTO(Question question);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "openIdUser", target = "openIdUser")
    @Mapping(source = "userName", target = "name")
    @Mapping(source = "userLastName", target = "lastName")
    @Mapping(source = "userStatus", target = "status")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "email", target = "email")
    UserDTO userToDTO(SubsidiaryUser user);

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    @Named("bigIntegerToInteger")
    default Integer bigIntegerToInteger(BigInteger value) {
        return value != null ? value.intValue() : null;
    }
}
