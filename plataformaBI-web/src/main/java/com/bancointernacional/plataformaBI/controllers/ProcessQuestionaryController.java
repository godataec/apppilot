package com.bancointernacional.plataformaBI.controllers;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancointernacional.plataformaBI.models.DTO.ProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.ProcessManager;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import com.bancointernacional.plataformaBI.services.serviceInterface.ProcessQuestionaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/processQuestionary")
public class ProcessQuestionaryController {

    @Autowired
    private ProcessQuestionaryService processQuestionaryService;

    @GetMapping
    public ResponseEntity<?> listProcessQuestionary(){
        List<ProcessQuestionaryDTO> responseProcessQuestionary =  processQuestionaryService.findAllProcessQuestionary();
        return new ResponseEntity<>(responseProcessQuestionary, HttpStatus.OK);
    }


    @GetMapping("/{idQuest}")
    public ResponseEntity<?> getProcessQuestionary(@PathVariable(name = "idQuest") UUID idQuest) {
            
            Optional<ProcessQuestionaryDTO> responseProcessQuestionary = processQuestionaryService.findProcessQuestionaryForId(idQuest);
            return responseProcessQuestionary.map(ResponseEntity::ok)
                                             .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByCharacters")
    public ResponseEntity<?> findByCharactersEspecifics(
            @RequestParam(name = "idProcess",required=false) String idProcess ,
            @RequestParam(name = "idPolicy",required=false) String idPolicy ,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int pageSize) {

            try {
                List<ProcessInsurancePolicyDTO> policyDTOS = processQuestionaryService.getActivePolicies(idProcess, idPolicy, page, pageSize);
                List<ProcessQuestionaryDTO> procesosQuest = processQuestionaryService.getProcessQuestByCharacters(idProcess, idPolicy, page, pageSize);
                Integer totalCount = processQuestionaryService.getProcessQuestByCharactersWithoutPages(idProcess, idPolicy);
                Map<String, Object> response = new HashMap<>();
                response.put("policies", policyDTOS);
                response.put("processquestionary", procesosQuest);
                response.put("total", totalCount);
                
                return ResponseEntity.ok(response);
                
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(null); 
            }
    }

    @GetMapping("/idpolicy/{idPolicy}")
    public ResponseEntity<?> getProcessQuestionary(@PathVariable(name = "idPolicy") Long idPolicy) {
            List<ProcessQuestionaryDTO> responsePorcessQuestionary = processQuestionaryService.findProcessQuestionnaryForidPolicy(idPolicy);
            return new ResponseEntity<>(responsePorcessQuestionary, HttpStatus.OK);
            
    }

    @PostMapping
    public ResponseEntity<?> postProcessQuestionary(@Valid @RequestBody ProcessQuestionaryDTO processQuestionaryDTO) {
        
        Optional<ProcessQuestionaryDTO> responseProcessQuestionary = processQuestionaryService.saveProcessQuestionary(processQuestionaryDTO);
        return responseProcessQuestionary.map(ResponseEntity::ok)
                                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("list")
    public ResponseEntity<?> postProcessQuestionaryList(@Valid @RequestBody List<ProcessQuestionaryDTO> processQuestionariesDTO) {
        
        List<ProcessQuestionaryDTO> createdprocessQuestionnaire = new ArrayList<>();
        for( ProcessQuestionaryDTO processQuestionnaire : processQuestionariesDTO)
            {
                createdprocessQuestionnaire.add(processQuestionaryService.saveProcessQuestionary(processQuestionnaire).get());
            }
        return new ResponseEntity<>(createdprocessQuestionnaire, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProcessQuestionary(@Valid @RequestBody ProcessQuestionary processQuestionary){

        Optional<ProcessQuestionaryDTO> responseProcessQuestionary= processQuestionaryService.updateProcessQuestionary(processQuestionary);
        return responseProcessQuestionary.map(ResponseEntity::ok)
                                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idQuest}")
    public ResponseEntity<?> removeProcessQuestionary(@Valid @PathVariable (name = "idQuest") UUID idQuest){
        processQuestionaryService.deleteLogicalProcessQuestionary(idQuest);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/verify/{idQuest}")
    public ResponseEntity<?> validateClosedAnswers(@Valid @PathVariable (name = "idQuest") UUID idQuest){
        Boolean allClosedAnswers = processQuestionaryService.validateClosedAnswers(idQuest);
        Map<String, Object> response = new HashMap<>();
        response.put("idQuestionary", idQuest);
        response.put("completed", allClosedAnswers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
