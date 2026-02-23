package com.bancointernacional.plataformaBI.services.serviceImpl;

import java.util.*;
import java.util.stream.Collectors;

import com.bancointernacional.plataformaBI.mapper.QuestionnaireMapper;
import com.bancointernacional.plataformaBI.models.DTO.projection.QuestionnaireProjectionDTO;
import com.bancointernacional.plataformaBI.models.entities.User;
import com.bancointernacional.plataformaBI.models.projections.QuestionnaireProjection;
import com.bancointernacional.plataformaBI.repositories.AttachFileRespository;
import com.bancointernacional.plataformaBI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancointernacional.plataformaBI.ErrorManagment.CustomException;
import com.bancointernacional.plataformaBI.models.DTO.QuestionnaireDTO;
import com.bancointernacional.plataformaBI.models.entities.Questionnaire;
import com.bancointernacional.plataformaBI.repositories.QuestionnaireRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionnaireService;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    QuestionnaireRepository questionnaireRespRepository;

    @Autowired
    QuestionnaireMapper questionnaireMapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private AttachFileServiceImpl attachFileServiceImpl;
    @Autowired
    private AttachFileRespository attachFileRespository;


    @Override
    public Double getQuestionariesAssignedToPolicy(Long idInsurance) {
        Double countValues = questionnaireRespRepository.countQuestionnaireByInsurancePolicy(idInsurance);
        if(countValues != null){
            return countValues;
        }
        return (double) 0;
    }

    @Override
    public List<QuestionnaireDTO> findAll() {
        
        List<Questionnaire> listQuestionnaire = questionnaireRespRepository.findAll();

        if(listQuestionnaire == null || listQuestionnaire.size() == 0){
            throw new CustomException("Lista de cuestionarios vacia", HttpStatus.NOT_FOUND);
        }

        return listQuestionnaire
                                .stream()
                                .map(u -> QuestionnaireDTO.build(u))
                                .collect(Collectors.toList());
    }

    @Override
    public Optional<QuestionnaireDTO> findByidQuestionnaire(Integer idQuestionnaire) {
        

        Optional<Questionnaire> foundQuestionnaire = questionnaireRespRepository.findByIdquestionnaire(idQuestionnaire);

        if(foundQuestionnaire.isPresent())
        {

            Questionnaire foundQuest = foundQuestionnaire.get();

            return Optional.of(QuestionnaireDTO.build(foundQuest));
        }
        else 
        {
            throw new CustomException("No se encontro el cuestionario.", HttpStatus.NOT_FOUND);
        }

    }

     @Override
    @Transactional(readOnly = true)
    public List<QuestionnaireDTO> findQuestionnaireAvalible(Long idPolicy) {
        System.out.println("idPolicy"+idPolicy);
        return questionnaireRespRepository
                            .findByIdPolicyAvalible(idPolicy)
                            .stream()
                            .map(u -> { return QuestionnaireDTO.build(u);})
                            .collect(Collectors.toList());
    }

    @Override
    public List<QuestionnaireProjectionDTO> findQuestionnairesForUser(UUID userUUID) {
        Optional<User> optionalUser = userRepository.findByOpenIdUser(userUUID.toString());
        if(optionalUser.isPresent()){
            List<QuestionnaireProjection> pendingQuestionnaires = questionnaireRespRepository
                    .findQuestionnaireNameAndQuestIdByUserId(optionalUser.get().getIdUser().toString());
            return questionnaireMapper.toDtoList(pendingQuestionnaires);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean deleteQuestionnaire(Integer idQuestionnaire) {
        try {
            questionnaireRespRepository.deleteQuestionnaireById(idQuestionnaire);
            return true;
        }catch (Exception e){
            System.out.printf("expection "+ e.getMessage());
            return false;
        }
    }


}
