package com.bancointernacional.plataformaBI.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.projection.QuestionnaireProjectionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancointernacional.plataformaBI.models.DTO.InsurancePolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionnaireDTO;
import com.bancointernacional.plataformaBI.models.entities.Questionnaire;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionnaireService;




@RequestMapping("/questionnaires")
@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;


    @GetMapping
    public ResponseEntity<?> getListQuestionnaire()
    {
        try {
            List<QuestionnaireDTO> listQuestionnaires = questionnaireService.findAll();
            return new ResponseEntity<>(listQuestionnaires, HttpStatus.OK);
            
        
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idQuestionnaire}")
    public ResponseEntity<?> getMethodName(@PathVariable("idQuestionnaire") Integer idQuestionnaire ) {
        try {
            Optional<QuestionnaireDTO>foundQuestionnaire = questionnaireService.findByidQuestionnaire(idQuestionnaire);
            return new ResponseEntity<>(foundQuestionnaire, HttpStatus.OK);
            
        
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
        
    }

    @GetMapping("idQuestAvalible/{idPolicy}")
    public ResponseEntity<?> getProcessPolicyAvaliblewithidProcess(@PathVariable(value ="idPolicy") Long idPolicy){

        List<QuestionnaireDTO> responseProcessQuestionary = questionnaireService.findQuestionnaireAvalible(idPolicy);
            return new ResponseEntity<>(responseProcessQuestionary, HttpStatus.OK);
    }

    @GetMapping("user/{UserUUID}")
    public ResponseEntity<?> getQuestionnairesByUser(@PathVariable(value ="UserUUID") UUID UserUUID){
        List<QuestionnaireProjectionDTO> responseProcessQuestionary = questionnaireService.findQuestionnairesForUser(UserUUID);
        return new ResponseEntity<>(responseProcessQuestionary, HttpStatus.OK);
    }



    @DeleteMapping("{idQuestionnaire}")
    public ResponseEntity<?> deleteQuestionnaire(@PathVariable("idQuestionnaire") Integer idQuestionnaire) {
        questionnaireService.deleteQuestionnaire(idQuestionnaire);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
