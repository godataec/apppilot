package com.bancointernacional.plataformaBI.util;

import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.template.Question;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.repository.template.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bancointernacional.plataformaBI.enums.AnswerTypes.*;

@Service
public class AnswerUtility {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserAnswerRepository userAnswerRepository;

    public void validateBeforeUpdate(UserAnswer userAnswer) {
        List<UserAnswer> userAnswers = userAnswerRepository.findRawByUserQuestionId(userAnswer.getUserQuestion().getUserQuestionId().toString());
        Optional<Question> optionalQuestion = questionRepository.findById(userAnswer.getQuestionId().intValue());
        if(optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            switch (question.getQuestionType()){
                case "OPTIONAL-NORMAL":
                    if (userAnswer.getAnswerType().equals(OPTIONAL.getWord())
                            && userAnswer.getAnswerText() != null
                            && userAnswer.getAnswerText().contains("##")) {
                        for(UserAnswer ua : userAnswers){
                            if(ua.isDeleted() && ua.getAnswerType().equals(NORMAL.getWord())){
                                ua.setDeleted(false);
                                userAnswerRepository.save(ua);
                            }
                        }
                    }else {
                        for(UserAnswer ua : userAnswers){
                            if(!ua.isDeleted() && ua.getAnswerType().equals(NORMAL.getWord())){
                                ua.setDeleted(true);
                                userAnswerRepository.save(ua);
                            }
                        }
                    }
                    break;
                case "SELECTOR-NORMAL":
                    if (userAnswer.getAnswerType().equals(SELECTOR.getWord())
                            && userAnswer.getAnswerText() != null
                            && userAnswer.getAnswerText().contains("##")) {
                        for(UserAnswer ua : userAnswers){
                            if(ua.isDeleted() && userAnswer.getAnswerType().equals(NORMAL.getWord())){
                                ua.setDeleted(false);
                                userAnswerRepository.save(ua);
                            }
                        }
                    }else {
                        for(UserAnswer ua : userAnswers){
                            if(!ua.isDeleted() && userAnswer.getAnswerType().equals(NORMAL.getWord())){
                                ua.setDeleted(true);
                                userAnswerRepository.save(ua);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }

        }

    }
}
