package com.bancointernacional.plataformaBI.controllers;

import java.time.format.DateTimeParseException;
import java.util.*;

import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancointernacional.plataformaBI.models.DTO.OptionsQuestionDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.services.serviceInterface.OptionsQuestionService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    OptionsQuestionService optionsQuestionService;

    @GetMapping("/findByCharacters")
    public ResponseEntity<?> findByCharactersEspecifics(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int pageSize,
        @RequestParam(name = "idQuest",required=false) String idQuest,
        @RequestParam(name = "UUIDUser", required = false) String userUUID
        ) {
            try {
                List<QuestionDTO> procesosQuest;
                Integer totalCount;
                if(Objects.nonNull(userUUID) && UUIDValidator.isValidUUID(userUUID)){
                    procesosQuest = questionService.getQuestionByUserAndQuestionnaireId(idQuest, page, pageSize,userUUID);
                    totalCount= questionService.getQuestionByUserIdAndQuestionnaireIdWithoutPages(idQuest,userUUID);
                }else{
                    //parece ser que el count que devuelve tiene un error, debe verificarse por si acaso
                    procesosQuest = questionService.getQuestionByCharacters(idQuest, page, pageSize);
                    totalCount = questionService.getQuestionByCharactersWithoutPages(idQuest);
                }
                Map<String, Object> response = new HashMap<>();
                response.put("questionary", procesosQuest);
                response.put("total", totalCount);
                return ResponseEntity.ok(response);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(null); 
            }
    }

    @GetMapping("/questions/{idQuest}")
    public ResponseEntity<?> getQuestions(@PathVariable (name = "idQuest") String idQuest){
        List<QuestionDTO> listQuestionDTO = questionService.getQuestionsForidQuestionnaire(idQuest);
        List<OptionsQuestionDTO> listOptionsQuestionDTO = new ArrayList<>();
        for (QuestionDTO questionDTO : listQuestionDTO) {
             Optional<OptionsQuestionDTO> resultFound =  optionsQuestionService.getOptionByQuestion(questionDTO.getIdQuestion());
            resultFound.ifPresent(listOptionsQuestionDTO::add);
        }
        Map<String, Object> response = new HashMap<>();
                response.put("Questions", listQuestionDTO);
                response.put("OptionsByQuestion", listOptionsQuestionDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/question/{idQuestion}")
    public ResponseEntity<?> getQuestionForidQuestion (@PathVariable (name = "idQuestion") Integer idQuestion){
        QuestionDTO questionDTO = questionService.getQuestionForId(idQuestion);
        Optional<OptionsQuestionDTO> OptionQuestionDTO =  optionsQuestionService.getOptionByQuestion(questionDTO.getIdQuestion());
        Map<String, Object> response = new HashMap<>();
                response.put("Question", questionDTO);
                response.put("OptionsQuestion", OptionQuestionDTO);
        return ResponseEntity.ok(response);

    }

}
