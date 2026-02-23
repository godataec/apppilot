package com.bancointernacional.plataformaBI.controllers;

import java.math.BigInteger;
import java.time.format.DateTimeParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancointernacional.plataformaBI.models.DTO.ProcessmanagerDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessManager;
import com.bancointernacional.plataformaBI.services.serviceImpl.ProcessManagerServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/processmanager")
public class ProcessManagerController {

    @Autowired
    private ProcessManagerServiceImpl procesService;
    private String dateEnd;

    @GetMapping
    public List<ProcessManager> list(){
        return procesService.findAll();
    }

    @GetMapping("/pageable")
    public ResponseEntity<List<ProcessManager>> getAllProcesses(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "5") int size) {
        // Pageable pageable = PageRequest.of(page, size);
        List<ProcessManager> processPage = procesService.findAllforPage(page, size);
        return new ResponseEntity<>(processPage, HttpStatus.OK);
    }



    @GetMapping("/findByDate")
    public ResponseEntity<List<ProcessManager>> buscarPorFechas(
            @RequestParam("dateBegin") String dateBegin,
            @RequestParam("dateEnd") String dateEnd) {

            try {
                
                List<ProcessManager> procesos = procesService.findByDates(dateBegin, dateEnd);
                return ResponseEntity.ok(procesos);
                
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(null); 
            }
    }

    @GetMapping("/findByCharacters")
    public ResponseEntity<?> findByCharactersEspecifics(
            @RequestParam(name = "character",required=false) String character ,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @RequestParam(name ="dateBegin", required = false) String dateBegin,
            @RequestParam(name ="dateEnd" , required = false) String dateEnd,
            @RequestParam(name ="year" , required = false) BigInteger year) {

            try {
        this.dateEnd = dateEnd;

        List<ProcessManager> procesos = procesService.findByCharacters(character, page, pageSize, dateBegin, dateEnd, year);
                Integer totalCount = procesService.findByCharactersWithoutPagination(character, dateBegin, dateEnd);
                Map<String, Object> response = new HashMap<>();
                response.put("processes", procesos);
                response.put("total", totalCount);
                
                return ResponseEntity.ok(response);
                
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(null); 
            }
    }


    @GetMapping("/{idProcess}")
    public ResponseEntity<?>  findByidProcess(@PathVariable (name="idProcess") UUID idProcess){
        
        Optional<ProcessManager> process = procesService.findByidProcess(idProcess);
        
        if(process.isPresent()){
            return ResponseEntity.ok(process.orElseThrow(null));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idProcess}")
    public ResponseEntity<?> updateProcess(@Valid @RequestBody ProcessManager process, BindingResult result, @PathVariable (name="idProcess") UUID idProcess )
    {
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<ProcessManager> o = procesService.update(process, idProcess);
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow(null));
        }

            return ResponseEntity.notFound().build();



    }

    @PostMapping
    public ResponseEntity<?> postProcess (@Valid @RequestBody ProcessManager process, BindingResult result)
    {
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<ProcessmanagerDTO> o = procesService.save(process);
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow(null));
        }
        return ResponseEntity.notFound().build();
    }

     @DeleteMapping("/{idProcess}")
     public ResponseEntity<?> removeProcess(@PathVariable(name = "idProcess") UUID idProcess){
         procesService.deleteLogicalProcess(idProcess);
         return ResponseEntity.noContent().build();
     }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo "+err.getField()+" "+err.getDefaultMessage());
        });        
        return ResponseEntity.badRequest().body(errors);
    }

}
