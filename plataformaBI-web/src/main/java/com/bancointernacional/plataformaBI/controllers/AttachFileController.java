package com.bancointernacional.plataformaBI.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO;
import com.bancointernacional.plataformaBI.models.entities.AttachFile;
import com.bancointernacional.plataformaBI.services.serviceImpl.AttachFileServiceImpl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

@RequestMapping("/fileManager")
@RestController
public class AttachFileController {

    @Autowired
    private AttachFileServiceImpl attachFileService;


    @GetMapping("idprocess/{idProcess}")
    public ResponseEntity<?> getinfoFilesByIdProcess(@PathVariable(value = "idProcess") UUID idProcess) throws IOException{
         try {
            
            List<AttachFileDTO> filesbyIdProcess = attachFileService.getInfoFilesByIdProcess(idProcess);
            return ResponseEntity.ok(filesbyIdProcess);

         } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }   
        

    }

    @GetMapping("idpolicy/{idPolicy}")
    public ResponseEntity<?> getinfoFilesByIdPolicy(@PathVariable(value = "idPolicy") Long idPolicy) throws IOException{
         try {
            
            List<AttachFileDTO> filesbyIdProcess = attachFileService.getInfoFilesByIdPolicy(idPolicy);
            return ResponseEntity.ok(filesbyIdProcess);

         } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }   
        

    }

    @GetMapping("idquest/{idQuest}")
    public ResponseEntity<?> getinfoFilesByIdQuest(@PathVariable(value = "idQuest") UUID idQuest) throws IOException{
         try {
            
            List<AttachFileDTO> filesbyIdQuest = attachFileService.getInfoFilesByIdQuest(idQuest);
            return ResponseEntity.ok(filesbyIdQuest);

         } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }   
        

    }

    @GetMapping("/questionary")
    public ResponseEntity<?> getinfoFilesByIdQuestAndIdQuestion(@RequestParam(name = "idQuest") UUID idQuest,
                                                                @RequestParam(name = "idQuestionnaire") Integer idQuestionnaire) throws IOException{
         try {
            
            List<AttachFileDTO> filesbyIdQuest = attachFileService.getInfoFilesByIdQuestAndIdQuestion(idQuest, idQuestionnaire);
            return ResponseEntity.ok(filesbyIdQuest);

         } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }   
        
    }

    @GetMapping("/question")
    public ResponseEntity<?> getinfoFilesByIdQuestAndIdQuestionIndividual(@RequestParam(name = "idQuest") UUID idQuest,
                                                                @RequestParam(name = "idQuestion") Integer idQuestion) throws IOException{
         try {
            
            List<AttachFileDTO> filesbyIdQuest = attachFileService.getInfoFilesByIdQuestAndIdQuestionIndividual(idQuest, idQuestion);
            return ResponseEntity.ok(filesbyIdQuest);

         } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }   
        
    }

    @GetMapping("/questionimages")
    public ResponseEntity<?> getImagesByIdQuestAndIdQuestion(@RequestParam(name = "idQuest") UUID idQuest,
    @RequestParam(name = "idQuestion") Integer idQuestion) throws IOException{
    
        try{
            List<AttachFileDTO> images = new ArrayList<>();
            List<AttachFileDTO> filesbyIdQuest = attachFileService.getInfoFilesByIdQuestAndIdQuestionIndividual(idQuest, idQuestion);
            for (AttachFileDTO attachFileDTO : filesbyIdQuest) {
                switch (attachFileDTO.getTypeFile()) {
                    case "png":
                    case "jpeg":
                    case "jpg":
                        Optional<AttachFileDTO> imageFound = attachFileService.getPhotoPreview(attachFileDTO.getIdFile());

                        if(imageFound.isPresent())
                        {
                            System.out.println("Imagen:"+imageFound.isPresent());
                            images.add(imageFound.get());   
                        } 
                        break;
                }
            }
            return ResponseEntity.ok(images);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         } 
    
    
    }

    @GetMapping("/filesByQuestions")
    public ResponseEntity<?> getFilesByIdQuestAndIdQuestion(@RequestParam(name = "idQuest") UUID idQuest,
    @RequestParam(name = "idQuestion") Integer idQuestion) throws IOException{
    
        try{
            List<AttachFileDTO> images = new ArrayList<>();
            List<AttachFileDTO> filesbyIdQuest = attachFileService.getInfoFilesByIdQuestAndIdQuestionIndividual(idQuest, idQuestion);
            for (AttachFileDTO attachFileDTO : filesbyIdQuest) {
                switch (attachFileDTO.getTypeFile()) {
                    case "png":
                    case "jpeg":
                    case "jpg": break;

                    default:
                        Optional<AttachFileDTO> imageFound = attachFileService.getPhotoPreview(attachFileDTO.getIdFile());

                        if(imageFound.isPresent())
                        {
                            System.out.println("Imagen:"+imageFound.isPresent());
                            images.add(imageFound.get());   
                        } 
                        break;
                }
            }
            return ResponseEntity.ok(images);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         } 
    
    
    }

    @GetMapping("download/{idFile}")
    public ResponseEntity<?> getFileByIdFile(@PathVariable(value= "idFile" )UUID idFile) throws IOException{

        try {
            Optional<AttachFile> fileFoundByIdFile = attachFileService.getFile(idFile);

            if(!fileFoundByIdFile.isPresent()){
                return ResponseEntity.notFound().build();
            }

            AttachFile fileFound = fileFoundByIdFile.get();

            byte[] dataFile = fileFound.getDataFile(); // Aquí obtienes el archivo como byte[]
            String fileName = fileFound.getNameFile(); // Nombre del archivo

            ByteArrayResource resource = new ByteArrayResource(dataFile);

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(dataFile.length)
                .body(resource);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> uploadFileProcess (
            @RequestParam("dataFile") MultipartFile dataFile,
            @RequestParam(value = "idProcess",required = false) UUID idProcess, // Ajusta el tipo según tu modelo
            @RequestParam(value = "idPolicy" , required = false) Long idPolicy,
            @RequestParam(value = "idQuest" , required = false) UUID idQuest,
            @RequestParam(value = "idQuestion" , required = false) Integer idQuestion,
            @RequestParam("name") String name) throws IOException { // Ajusta el tipo según tu modelo

                
        if (dataFile.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío.");
        }

        // Lógica para convertir la fecha de String a LocalDate (ajusta según tu formato)
        LocalDate dateUploadFile = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dateUploadFile.format(formatter);

        LocalDate parsedDateUploadFile = LocalDate.parse(formattedDate); 

        try {


            AttachFileDTO attachFile = new AttachFileDTO();
            attachFile.setNameFile(name);
            attachFile.setDateUploadFile(parsedDateUploadFile);

            @SuppressWarnings("null")
            String nameFile[] = dataFile.getOriginalFilename().split("\\.") ;
            int sizeName = nameFile.length; 
            
            attachFile.setTypeFile((sizeName > 1) ? nameFile[sizeName-1]: "file");
            attachFile.setDataFile(dataFile.getBytes()); // Establece el contenido del archivo
            
            if(idPolicy!=null) attachFile.setIdPolicy(idPolicy);  
            if(idProcess!=null) attachFile.setIdProcess(idProcess);
            if(idQuest!=null) attachFile.setIdQuest(idQuest);
            if(idQuestion!=null) attachFile.setIdQuestion(idQuestion);
             
            AttachFile  attachFileResult = attachFileService.storeService(attachFile);

            return ResponseEntity.ok(attachFileResult);


        } catch (Exception e) {
            System.out.println("Error al subir le archivo:"+e);
            return ResponseEntity.badRequest().body(null); 
        }
        
        // Crear una nueva instancia de AttachFile
       
    }

    @DeleteMapping("/{idFile}")
    public ResponseEntity<?> deleteFile(@PathVariable(value= "idFile" )UUID idFile){

        try {
            Optional<AttachFileDTO> fileDeleted = attachFileService.DeleteFileById(idFile); 

            if(!fileDeleted.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(fileDeleted);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
