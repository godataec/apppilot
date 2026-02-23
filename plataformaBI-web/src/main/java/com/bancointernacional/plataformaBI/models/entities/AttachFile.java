package com.bancointernacional.plataformaBI.models.entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;


@Entity
@Table(name="ATTACHFILE")
@Where(clause = "ISDELETED = false")
public class AttachFile {

    @Id
    @Column(name = "idFile", nullable = false, columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID idFile;

    @NotBlank
    @Size(max = 50)
    @Column(name= "nameFile")
    private String nameFile;
    
    
    @Column(name="dateUploadFile")
    private LocalDate dateUploadFile;
    
    @NotBlank
    @Size(max = 200)
    @Column(name="typeFile")
    private String typeFile;


    //tipo archivo
    @Lob
    @Column(name = "dataFile", columnDefinition = "VARBINARY(MAX) FILESTREAM NOT NULL")
    private byte[] dataFile;

    @Lob
    @Column(name = "photopreview", columnDefinition = "VARBINARY(MAX) FILESTREAM NOT NULL")
    private byte[] photopreview;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idProcess", nullable = false, referencedColumnName = "idProcess")
    private ProcessManager process;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPolicy", nullable = false, referencedColumnName = "idPolicy")
    private ProcessInsurancePolicy processInsurancePolicy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idQuest", nullable = false, referencedColumnName = "idQuest")
    private ProcessQuestionary processQuestionary;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idQuestion", nullable = false, referencedColumnName = "idQuestion")
    private Question question;

    @Column(name = "ISDELETED")
    private boolean isDeleted;

    public AttachFile() {
    }

    public AttachFile(UUID idFile, @NotBlank @Size(max = 50) String nameFile, LocalDate dateUploadFile,
            @NotBlank @Size(max = 200) String typeFile, byte[] dataFile, ProcessManager process,
            ProcessInsurancePolicy processInsurancePolicy, ProcessQuestionary processQuestionary, Question question) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.process = process;
        this.processInsurancePolicy = processInsurancePolicy;
        this.processQuestionary = processQuestionary;
        this.question = question;
    }

    public AttachFile(UUID idFile, @NotBlank @Size(max = 50) String nameFile, LocalDate dateUploadFile,
            @NotBlank @Size(max = 200) String typeFile, byte[] dataFile, ProcessManager process,
            ProcessInsurancePolicy processInsurancePolicy, ProcessQuestionary processQuestionary) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.process = process;
        this.processInsurancePolicy = processInsurancePolicy;
        this.processQuestionary = processQuestionary;
    }

    public AttachFile(UUID idFile, @NotBlank @Size(max = 50) String nameFile, LocalDate dateUploadFile,
            @NotBlank @Size(max = 50) String typeFile, byte[] dataFile, ProcessManager process,
            ProcessInsurancePolicy processInsurancePolicy) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.process = process;
        this.processInsurancePolicy = processInsurancePolicy;
    }

    public AttachFile(UUID idFile, @NotBlank @Size(max = 50) String nameFile, LocalDate dateUploadFile,
            @NotBlank @Size(max = 50) String typeFile, byte[] dataFile, ProcessManager process) {
        this.idFile = idFile;
        this.nameFile = nameFile;
        this.dateUploadFile = dateUploadFile;
        this.typeFile = typeFile;
        this.dataFile = dataFile;
        this.process = process;
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

    public ProcessManager getProcess() {
        return process;
    }

    public void setProcess(ProcessManager process) {
        this.process = process;
    }

    public ProcessInsurancePolicy getProcessInsurancePolicy() {
        return processInsurancePolicy;
    }

    public void setProcessInsurancePolicy(ProcessInsurancePolicy processInsurancePolicy) {
        this.processInsurancePolicy = processInsurancePolicy;
    }

    public ProcessQuestionary getProcessQuestionary() {
        return processQuestionary;
    }

    public void setProcessQuestionary(ProcessQuestionary processQuestionary) {
        this.processQuestionary = processQuestionary;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public byte[] getPhotopreview() {
        return photopreview;
    }

    public void setPhotopreview(byte[] photopreview) {
        this.photopreview = photopreview;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
