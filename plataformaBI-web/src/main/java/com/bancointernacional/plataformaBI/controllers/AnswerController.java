package com.bancointernacional.plataformaBI.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/answer")
@RestController
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping("/processQuestionary/{idQuest}")
    public ResponseEntity<?> getAnswerByProcessQuestion(@PathVariable(name = "idQuest") UUID idQuest) {
        List<AnswerDTO> listOfAnswer = answerService.findByIdProcessQuestionary(idQuest);
        return new ResponseEntity<>(listOfAnswer, HttpStatus.OK);
    }

    @GetMapping("/processandquest")
    public ResponseEntity<?> getAnswerByProcessQuestionAndQuest(@RequestParam(name = "idQuest") UUID idQuest,
                                                                @RequestParam(name = "idQuestionnaire") Long idQuestionnaire) {
        List<AnswerDTO> listOfAnswer = answerService.findAnswerByProcessQuestandQuestionnaire(idQuest, idQuestionnaire);
        Map<String, Object> response = new HashMap<>();
        response.put("answers", listOfAnswer);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findByProcessAndQuestion")
    public ResponseEntity<?> getAnswerByProcessAndQuestion(@RequestParam(name = "idQuest") UUID idQuest, @RequestParam(name = "idQuestion") String idQuestion) {
        String[] questions = idQuestion.split("\\,");
        ArrayList<AnswerDTO> answersFound = new ArrayList<AnswerDTO>();
        for (int cont = 0; cont < questions.length; cont++) {
            List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(idQuest, Long.valueOf(questions[cont]));
            for (AnswerDTO answerDTO : listOfAnswer) {
                answersFound.add(answerDTO);
            }
        }
        //List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(idQuest, idQuestion );
        Map<String, Object> response = new HashMap<>();
        response.put("answers", answersFound);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{idAnswer}")
    public ResponseEntity<?> getAnswerByIdAnswer(@PathVariable(name = "idAnswer") UUID idAnswer) {
        AnswerDTO answerFound = answerService.findQuestionByAnswer(idAnswer);
        return new ResponseEntity<>(answerFound, HttpStatus.OK);
    }


    @GetMapping("/AnswerByUser")
    public ResponseEntity<?> getAnswerByIdProcessAndIdUser(
            @RequestParam(name = "idQuest") UUID idQuest,
            @RequestParam(name = "idUser") UUID idUser) {
        List<AnswerDTO> listOfAnswerByUser = answerService.findByProcessQuestionaryAndUser(idQuest, idUser);
        return new ResponseEntity<>(listOfAnswerByUser, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postAnswers(@Valid @RequestBody AnswerDTO answer) {
        List<AnswerDTO> ResultAnswerSave = answerService.saveAnswer(answer);
        return new ResponseEntity<>(ResultAnswerSave, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> putAnswer(@Valid @RequestBody AnswerDTO answer) {
        AnswerDTO answerUpdated = answerService.updateAnswer(answer);
        if(Objects.nonNull(answerUpdated)){
            return new ResponseEntity<>(answerUpdated, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }


    @PutMapping("/UpdateByList")
    public ResponseEntity<?> putAnswersbyList(@Valid @RequestBody List<AnswerDTO> answers) {
        List<AnswerDTO> listOfAnswerUpdate = new ArrayList<>();
        for (AnswerDTO answerToUpdate : answers) {
            System.out.println("answerToUpdate:" + answerToUpdate);
            AnswerDTO ResultAnswerSave = answerService.updateAnswer(answerToUpdate);
            listOfAnswerUpdate.add(ResultAnswerSave);
        }
        return new ResponseEntity<>(listOfAnswerUpdate, HttpStatus.OK);
    }

    @PutMapping("/assignedTo")
    public ResponseEntity<?> updateOwnerInAnswers(@Valid @RequestBody List<AnswerDTO> answers) {
        List<AnswerDTO> listOfAnswerUpdate = new ArrayList<>();
        for (AnswerDTO answerToUpdate : answers) {
            System.out.println("answerToUpdate:" + answerToUpdate);
            AnswerDTO ResultAnswerSave = answerService.updateOwnerInAnswer(answerToUpdate);
            listOfAnswerUpdate.add(ResultAnswerSave);
        }
        return new ResponseEntity<>(listOfAnswerUpdate, HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteAnswers(@Valid @RequestBody List<AnswerDTO> answers) {
        List<AnswerDTO> listAnswerDeleted = new ArrayList<>();
        for (AnswerDTO answerToUpdate : answers) {
            AnswerDTO ResultAnswerSave = answerService.deleteAnswer(answerToUpdate);
            listAnswerDeleted.add(ResultAnswerSave);
        }
        return new ResponseEntity<>(listAnswerDeleted, HttpStatus.OK);
    }


    @DeleteMapping("/deletequestandquestion")
    public ResponseEntity<?> deleteAnswersByIdQuestAndidQuestions(@RequestParam(name = "idQuest") UUID idQuest, @RequestParam(name = "idQuestion") Long idQuestion) {
        List<AnswerDTO> listAnswerDeleted = new ArrayList<>();
        listAnswerDeleted = answerService.deleteAnswersByIdQuestAndIdQuestion(idQuest, idQuestion);
        return new ResponseEntity<>(listAnswerDeleted, HttpStatus.OK);
    }


}
