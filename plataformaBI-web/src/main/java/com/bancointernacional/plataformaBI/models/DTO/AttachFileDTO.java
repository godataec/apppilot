package com.bancointernacional.plataformaBI.models.DTO;

import java.time.LocalDate;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.entities.AttachFile;

public class AttachFileDTO {

    private UUID idFile;
    private String nameFile;
    private LocalDate dateUploadFile;
    private String typeFile;
    private byte[] dataFile;
    private byte[] photopreview;
    private UUID idProcess;
    private Long idPolicy;
    private UUID idQuest;
    private Integer idQuestion;
    
    public UUID getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(UUID idQuest) {
        this.idQuest = idQuest;
    }

    public Integer getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }

    public AttachFileDTO() {
    }

    public AttachFileDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, byte[] dataFile,
    UUID idProcess, Long idPolicy, UUID idQuest, Integer idQuestion) {
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

    public AttachFileDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, byte[] dataFile,
    UUID idProcess, Long idPolicy, UUID idQuest) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
        this.idQuest = idQuest;
        
    }



    public AttachFileDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, byte[] dataFile,
    UUID idProcess, Long idPolicy) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
        
    }

    public AttachFileDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile, UUID idProcess) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.idProcess = idProcess;
    }

    public AttachFileDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile,  UUID idProcess, Long idPolicy) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
    }

    public AttachFileDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile,
    UUID idProcess, Long idPolicy, UUID idQuest) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.idProcess = idProcess;
        this.idPolicy = idPolicy;
        this.idQuest = idQuest;
        
    }

    public AttachFileDTO(UUID idFile, String nameFile, LocalDate dateUploadFile, String typeFile,
    UUID idProcess, Long idPolicy, UUID idQuest, Integer idQuestion) {
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

    public Long getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(Long idPolicy) {
        this.idPolicy = idPolicy;
    }

  

    public static AttachFileDTO build(AttachFile attachFile){


        if(attachFile == null){
            throw new RuntimeException("Debe pasar el entity de polizas");
        }

        UUID idProcess = attachFile.getProcess() != null ? attachFile.getProcess().getIdProcess() : null;
        Long idPolicy = attachFile.getProcessInsurancePolicy() != null ? attachFile.getProcessInsurancePolicy().getIdPolicy() : null;
        UUID idQuest = attachFile.getProcessQuestionary() != null ? attachFile.getProcessQuestionary().getIdQuest() : null;
        Integer idQuestion = attachFile.getQuestion() != null ? attachFile.getQuestion().getIdQuestion() : null;

        return new AttachFileDTO(
                                 attachFile.getIdFile(),
                                 attachFile.getNameFile(),
                                 attachFile.getDateUploadFile(),
                                 attachFile.getTypeFile(),
                                 attachFile.getDataFile(),
                                 idProcess,
                                 idPolicy,
                                 idQuest,
                                 idQuestion
        );

    }

    public static AttachFileDTO buildByInfo(AttachFile attachFile){
        if(attachFile == null){
            throw new RuntimeException("Debe pasar el entity de polizas");
        }

        UUID idProcess = attachFile.getProcess() != null ? attachFile.getProcess().getIdProcess() : null;
        Long idPolicy = attachFile.getProcessInsurancePolicy() != null ? attachFile.getProcessInsurancePolicy().getIdPolicy() : null;
        UUID idQuest = attachFile.getProcessQuestionary() != null ? attachFile.getProcessQuestionary().getIdQuest() : null;
        Integer idQuestion = attachFile.getQuestion() != null ? attachFile.getQuestion().getIdQuestion() : null;

        return new AttachFileDTO(
                                 attachFile.getIdFile(),
                                 attachFile.getNameFile(),
                                 attachFile.getDateUploadFile(),
                                 attachFile.getTypeFile(),
                                 idProcess,
                                 idPolicy,
                                 idQuest,
                                 idQuestion
        );
    }
    
}
