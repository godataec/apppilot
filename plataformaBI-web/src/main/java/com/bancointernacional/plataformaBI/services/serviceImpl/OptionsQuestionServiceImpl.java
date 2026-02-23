package com.bancointernacional.plataformaBI.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancointernacional.plataformaBI.Enums.AnswerTypes;
import com.bancointernacional.plataformaBI.models.DTO.OptionsQuestionDTO;
import com.bancointernacional.plataformaBI.models.entities.OptionsQuestion;
import com.bancointernacional.plataformaBI.models.entities.Question;
import com.bancointernacional.plataformaBI.models.entities.Questionnaire;
import com.bancointernacional.plataformaBI.repositories.OptionsQuestionRepository;
import com.bancointernacional.plataformaBI.repositories.QuestionRepository;
import com.bancointernacional.plataformaBI.repositories.QuestionnaireRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.OptionsQuestionService;

@Service
public class OptionsQuestionServiceImpl implements OptionsQuestionService {

    @Autowired
    OptionsQuestionRepository optionsQuestionRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Override
    public Optional<OptionsQuestionDTO> getOptionByQuestion(Integer idQuestion) {
        OptionsQuestion optionQuestion = optionsQuestionRepository.findByIdquestion(idQuestion);
        return  Optional.of(OptionsQuestionDTO.build(optionQuestion));
    }

    @Override
    public List<OptionsQuestion> getOptionsByIdQuestionnaire( Integer idQuestionnaire) {
       
        Optional<Questionnaire> questionnaireFound = questionnaireRepository.findById(idQuestionnaire);
        List<OptionsQuestion> optionsByQuestions = new ArrayList<>();
        
        if(questionnaireFound.isPresent())
        {
            Questionnaire questionnaireExtract = questionnaireFound.get();

            List<Question> listOfQuestions = questionRepository.findByQuestionnaire(questionnaireExtract); 

            for (Question question : listOfQuestions) {
                if(question.getTypequestion().contains(AnswerTypes.OPTIONAL.getWord()))
                {
                    OptionsQuestion optionsByQuestion = optionsQuestionRepository.findByIdquestion(question.getIdQuestion());
                    optionsByQuestions.add(optionsByQuestion);
                }
            }

        }


        return optionsByQuestions ;
         
        
    }

}
