package com.bancointernacional.plataformaBI.services.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import com.bancointernacional.plataformaBI.models.entities.User;
import com.bancointernacional.plataformaBI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bancointernacional.plataformaBI.ErrorManagment.CustomException;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.entities.Question;
import com.bancointernacional.plataformaBI.repositories.QuestionRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<QuestionDTO> getQuestionsForidQuestionnaire(String idQuest) {
        return questionRepository.findQuestionsByIdQuestionnaire(idQuest)
                .stream()
                .map(u -> QuestionDTO.build(u))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO getQuestionForId(Integer idQuestion) {
        Optional<Question> questionFound = questionRepository.findById(idQuestion);
        if (!questionFound.isPresent()) {
            throw new CustomException("No se encontro la pregunta.", HttpStatus.NOT_FOUND);
        }
        return QuestionDTO.build(questionFound.get());
    }

    @Override
    public List<QuestionDTO> getQuestionByCharacters(String idQuest, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Question> questionDes = questionRepository.findQuestionForCharacterswithDes(idQuest, offset, pageSize);
        List<QuestionDTO> questionsList = new ArrayList<>();
        List<Question> questionResult = getSubQuestions(questionDes);
        for (Question question : questionResult) {
            questionsList.add(QuestionDTO.build(question));
        }
        return questionsList;
    }

    public List<Question> getSubQuestions(List<Question> questions) {
        List<Question> result = new ArrayList<>();
        Set<Question> visited = new HashSet<>();
        Stack<Question> stack = new Stack<>();
        for (Question question : questions) {
            stack.push(question);
        }
        while (!stack.isEmpty()) {
            Question current = stack.pop();
            if (visited.add(current)) {
                result.add(current);
                if (current.getIdQuestion() != null) {
                    List<Question> subQuestions = questionRepository.findSubQuestions(current.getIdQuestion());
                    if (!subQuestions.isEmpty()) {
                        stack.addAll(subQuestions);
                    }
                }
            }
        }
        result.sort(Comparator.comparing(Question::getIdQuestion));
        return result;
    }

    @Override
    public Integer getQuestionByCharactersWithoutPages(String idQuest) {
        return questionRepository.findQuestionForCharacterswithoutPages(idQuest);
    }

    @Override
    public List<QuestionDTO> getQuestionByUserAndQuestionnaireId(String idQuest, int page, int pageSize, String userUUID) {
        int offset = (page - 1) * pageSize;
        Optional<User> optionalUser = userRepository.findByOpenIdUser(userUUID);
        if(optionalUser.isPresent()) {
            Set<Question> questionDes = questionRepository.findQuestionnaireNameAndQuestIdByUserId(
                    optionalUser.get().getIdUser().toString(), idQuest, offset, pageSize);
            return questionDes.stream().map(QuestionDTO::build).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Integer getQuestionByUserIdAndQuestionnaireIdWithoutPages(String idQuest, String userUUID) {
        Optional<User> optionalUser = userRepository.findByOpenIdUser(userUUID);
        if(optionalUser.isPresent()) {
            return questionRepository.countQuestionnaireNameAndQuestIdByUserId(
                    optionalUser.get().getIdUser().toString(), idQuest);
        }
        return 0;
    }

}
