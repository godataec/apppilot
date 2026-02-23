package com.bancointernacional.plataformaBI.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancointernacional.plataformaBI.models.DTO.OptionsQuestionDTO;
import com.bancointernacional.plataformaBI.models.entities.OptionsQuestion;
import com.bancointernacional.plataformaBI.services.serviceInterface.OptionsQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/options")
@RestController
public class OptionsQuestionsController {


    @Autowired
    OptionsQuestionService optionsQuestionService;


    @GetMapping("/byIdQuestion")
    public ResponseEntity<?> getOptions(@RequestParam(name = "idQuestion") Integer idQuestion) {
        System.out.println("idQuestion: "+idQuestion);
       Optional<OptionsQuestionDTO> optionsQuestion = optionsQuestionService.getOptionByQuestion(idQuestion);
        return ResponseEntity.ok(optionsQuestion); 

    }


    @GetMapping("/byIdQuestionnaire")
    public ResponseEntity<?> getOptionsByIdQuestionnaire(@RequestParam(name = "idQuestionnaire") Integer idQuestionnaire) {
        
       List<OptionsQuestion> optionsQuestion = optionsQuestionService.getOptionsByIdQuestionnaire(idQuestionnaire);
        return ResponseEntity.ok(optionsQuestion); 
        
    }
    

}
