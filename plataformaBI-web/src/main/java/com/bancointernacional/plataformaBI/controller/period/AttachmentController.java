package com.bancointernacional.plataformaBI.controller.period;

import com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO;
import com.bancointernacional.plataformaBI.domain.model.period.Attachment;
import com.bancointernacional.plataformaBI.service.AttachmentService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder

@RequestMapping("/attachment")
@RestController
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("process/{processId}")
    public ResponseEntity<?> getInfoFilesByProcessId(@PathVariable(value = "processId") UUID processId) throws IOException {
        try {
            List<AttachmentDTO> filesByIdProcess = attachmentService.getInfoFilesByProcessId(processId);
            return ResponseEntity.ok(filesByIdProcess);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("process-policy/{processPolicyId}")
    public ResponseEntity<?> getInfoFilesByIdPolicy(@PathVariable(value = "processPolicyId") UUID processPolicyId) throws IOException {
        try {
            List<AttachmentDTO> filesByProcessPolicyId = attachmentService.getInfoFilesByProcessPolicyId(processPolicyId);
            return ResponseEntity.ok(filesByProcessPolicyId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("policy-questionary/{policyQuestionaryId}")
    public ResponseEntity<?> getInfoFilesByPolicyQuestionaryId(@PathVariable(value = "policyQuestionaryId") UUID policyQuestionaryId) throws IOException {
        try {
            List<AttachmentDTO> filesByPolicyQuestionaryId = attachmentService.getInfoFilesByPolicyQuestionaryId(policyQuestionaryId);
            return ResponseEntity.ok(filesByPolicyQuestionaryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/questionaryAndAnswer/{policyQuestionaryId}/{userAnswerId}")
    public ResponseEntity<?> getInfoFilesByPolicyQuestionaryIdAndUserAnswerId(@PathVariable(name = "policyQuestionaryId") UUID policyQuestionaryId,
                                                                              @PathVariable(name = "userAnswerId") UUID userAnswerId) throws IOException {
        try {
            List<AttachmentDTO> filesByPolicyQuestionaryAndUserAnswerId = attachmentService.getInfoFilesByPolicyQuestionaryAndUserAnswerId(policyQuestionaryId, userAnswerId);
            return ResponseEntity.ok(filesByPolicyQuestionaryAndUserAnswerId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user-answer/{userAnswerId}")
    public ResponseEntity<?> getInfoFilesByUserAnswer(@PathVariable(name = "userAnswerId") UUID userAnswerId) throws IOException {
        try {
            List<AttachmentDTO> filesByUserAnswerId = attachmentService.getInfoFilesByUserAnswerId(userAnswerId);
            return ResponseEntity.ok(filesByUserAnswerId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/process-action/{actionId}")
    public ResponseEntity<?> getInfoFilesByProcessAction(@PathVariable(name = "actionId") UUID actionId) throws IOException {
        try {
            List<AttachmentDTO> filesByActionId = attachmentService.getInfoFilesByActionId(actionId);
            return ResponseEntity.ok(filesByActionId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/questionimages")
    public ResponseEntity<?> getImagesByIdQuestAndIdQuestion(@RequestParam(name = "policyQuestionaryId") UUID policyQuestionaryId,
                                                             @RequestParam(name = "userAnswerId") UUID userAnswerId) throws IOException {

        try {
            List<AttachmentDTO> images = new ArrayList<>();
            List<AttachmentDTO> filesByPolicyQuestionaryAndUserAnswerId = attachmentService.getInfoFilesByPolicyQuestionaryAndUserAnswerId(policyQuestionaryId, userAnswerId);
            for (AttachmentDTO attachFileDTO : filesByPolicyQuestionaryAndUserAnswerId) {
                switch (attachFileDTO.getTypeFile()) {
                    case "png":
                    case "jpeg":
                    case "jpg":
                        Optional<AttachmentDTO> imageFound = attachmentService.getPhotoPreview(attachFileDTO.getIdFile());

                        if (imageFound.isPresent()) {
                            System.out.println("Imagen:" + imageFound.isPresent());
                            images.add(imageFound.get());
                        }
                        break;
                }
            }
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("download/{attachmentId}")
    public ResponseEntity<?> getFileByIdFile(@PathVariable(value = "attachmentId") UUID attachmentId) throws IOException {
        try {
            Optional<Attachment> fileFoundByIdFile = attachmentService.getFile(attachmentId);
            if (!fileFoundByIdFile.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Attachment fileFound = fileFoundByIdFile.get();
            byte[] dataFile = fileFound.getAttachmentByte();
            String fileName = fileFound.getAttachmentName();
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
    public ResponseEntity<?> uploadFileProcess(
            @RequestParam("dataFile") MultipartFile dataFile,
            @RequestParam(value = "processId", required = false) UUID processId,
            @RequestParam(value = "processPolicyId", required = false) UUID processPolicyId,
            @RequestParam(value = "policyQuestionaryId", required = false) UUID policyQuestionaryId,
            @RequestParam(value = "userAnswerId", required = false) UUID userAnswerId,
            @RequestParam(value = "actionId", required = false) UUID actionId,
            @RequestParam("name") String name) throws IOException {
        if (dataFile.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío.");
        }
        LocalDate dateUploadFile = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dateUploadFile.format(formatter);
        LocalDate parsedDateUploadFile = LocalDate.parse(formattedDate);
        try {
            AttachmentDTO attachFile = new AttachmentDTO();
            attachFile.setNameFile(name);
            attachFile.setDateUploadFile(parsedDateUploadFile);
            String nameFile[] = dataFile.getOriginalFilename().split("\\.");
            int sizeName = nameFile.length;
            attachFile.setTypeFile((sizeName > 1) ? nameFile[sizeName - 1] : "file");
            attachFile.setDataFile(dataFile.getBytes());
            if (processPolicyId != null) attachFile.setIdPolicy(processPolicyId);
            if (processId != null) attachFile.setIdProcess(processId);
            if (policyQuestionaryId != null) attachFile.setIdQuest(policyQuestionaryId);
            if (userAnswerId != null) attachFile.setIdQuestion(userAnswerId);
            Attachment attachFileResult = attachmentService.storeService(attachFile);
            attachFile.setIdFile(attachFileResult.getAttachmentId());
            return ResponseEntity.ok(attachFile);
        } catch (Exception e) {
            System.out.println("Error al subir le archivo:" + e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<?> deleteFile(@PathVariable(value = "attachmentId") UUID attachmentId) {
        try {
            Optional<AttachmentDTO> fileDeleted = attachmentService.DeleteFileById(attachmentId);
            if (!fileDeleted.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(fileDeleted);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
