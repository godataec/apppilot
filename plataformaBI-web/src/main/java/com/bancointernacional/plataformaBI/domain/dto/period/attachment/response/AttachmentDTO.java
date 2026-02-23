package com.bancointernacional.plataformaBI.domain.dto.period.attachment.response;


import com.bancointernacional.plataformaBI.domain.model.period.Attachment;

import java.time.LocalDate;
import java.util.UUID;

public class AttachmentDTO {

    private UUID idFile;
    private String nameFile;
    private LocalDate dateUploadFile;
    private String typeFile;
    private byte[] dataFile;
    private byte[] photopreview;
    private UUID idProcess;
    private UUID idPolicy;
    private UUID idQuest;
    private UUID idQuestion;
    private UUID idAction;
    
    public UUID getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(UUID idQuest) {
        this.idQuest = idQuest;
    }

    public UUID getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(UUID idQuestion) {
        this.idQuestion = idQuestion;
    }

    public AttachmentDTO() {
    }

    public AttachmentDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, byte[] dataFile,
                         UUID idProcess, UUID idPolicy, UUID idQuest, UUID idQuestion) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
        this.idQuest = idQuest;
        this.idQuestion = idQuestion;
        
    }

    public AttachmentDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, byte[] dataFile,
                         UUID idProcess, UUID idPolicy, UUID idQuest) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
        this.idQuest = idQuest;
        
    }



    public AttachmentDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, byte[] dataFile,
                         UUID idProcess, UUID idPolicy) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
        
    }

    public AttachmentDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, UUID idProcess) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.idProcess = idProcess;
    }

    public AttachmentDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, UUID idProcess, UUID idPolicy) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
    }

    public AttachmentDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile,
                         UUID idProcess, UUID idPolicy, UUID idQuest) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
        this.idQuest = idQuest;
        
    }

    public AttachmentDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile,
                         UUID idProcess, UUID idPolicy, UUID idQuest, UUID idQuestion) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
        this.idQuest = idQuest;
        this.idQuestion = idQuestion;
        
    }


    public UUID getIdFile() {
        return idFile;
    }

    public void setIdFile(UUID idFile) {
        this.idFile = idFile;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public LocalDate getDateUploadFile() {
        return dateUploadFile;
    }

    public void setDateUploadFile(LocalDate dateUploadFile) {
        this.dateUploadFile = dateUploadFile;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public byte[] getDataFile() {
        return dataFile;
    }

    public void setDataFile(byte[] dataFile) {
        this.dataFile = dataFile;
    }

    public UUID getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(UUID idProcess) {
        this.idProcess = idProcess;
    }

    public UUID getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(UUID idPolicy) {
        this.idPolicy = idPolicy;
    }

    public UUID getIdAction() {
        return idAction;
    }

    public void setIdAction(UUID idAction) {
        this.idAction = idAction;
    }
  

    public static AttachmentDTO build(Attachment attachFile){


        if(attachFile == null){
            throw new RuntimeException("Debe pasar el entity de polizas");
        }

        UUID processId = attachFile.getProcessId() != null ? attachFile.getProcessId() : null;
        UUID processPolicyId = attachFile.getProcessPolicyId() != null ? attachFile.getProcessPolicyId() : null;
        UUID policyQuestionaryId = attachFile.getPolicyQuestionaryId() != null ? attachFile.getPolicyQuestionaryId() : null;
        UUID userAnswerId = attachFile.getUserAnswerId() != null ? attachFile.getUserAnswerId() : null;

        return new AttachmentDTO(
                                 attachFile.getAttachmentId(),
                                 attachFile.getAttachmentName(),
                                 attachFile.getUploadDate(),
                                 attachFile.getAttachmentType(),
                                 attachFile.getAttachmentByte(),
                                 processId,
                                 processPolicyId,
                                 policyQuestionaryId,
                                 userAnswerId
        );

    }

    public static AttachmentDTO buildByInfo(Attachment attachFile){
        if(attachFile == null){
            throw new RuntimeException("Debe pasar el entity de polizas");
        }

        UUID processId = attachFile.getProcessId() != null ? attachFile.getProcessId() : null;
        UUID processPolicyId = attachFile.getProcessPolicyId() != null ? attachFile.getProcessPolicyId() : null;
        UUID policyQuestionaryId = attachFile.getPolicyQuestionaryId() != null ? attachFile.getPolicyQuestionaryId() : null;
        UUID userAnswerId = attachFile.getUserAnswerId() != null ? attachFile.getUserAnswerId() : null;

        return new AttachmentDTO(
                                attachFile.getAttachmentId(),
                                attachFile.getAttachmentName(),
                                attachFile.getUploadDate(),
                                attachFile.getAttachmentType(),
                                processId,
                                processPolicyId,
                                policyQuestionaryId,
                                userAnswerId
        );
    }
    
}
