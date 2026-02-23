package com.bancointernacional.plataformaBI.models.entities;



import lombok.Builder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;



@Entity
@Table(name="PROCESSMANAGER")
@Where(clause = "ISDELETED = false")
public class ProcessManager {

    @Id
    @Column(name = "IDPROCESS", nullable = false, columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID idProcess;

    @NotBlank
    @Size(max = 100)
    @Column(name = "processDescription", unique = true)
    private String processDescription;

    @NotBlank
    @Size(max = 20)
    @Column(name = "dateBegin")
    private String dateBegin;

    @NotBlank
    @Size(max = 20)
    @Column(name = "dateEnd")
    private String dateEnd;

    @NotBlank
    @Size(max = 20)
    @Column(name = "status")
    private String status;


    @Column(name = "ISDELETED")
    private boolean isDeleted;

    public ProcessManager() {
    }

    public ProcessManager(UUID idProcess, @NotBlank @Size(max = 100) String processDescription,
            @NotBlank @Size(max = 20) String dateBegin, @NotBlank @Size(max = 20) String dateEnd,
            @NotBlank @Size(max = 20) String status) {
        this.idProcess = idProcess;
        this.processDescription = processDescription;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
    }

    public ProcessManager(ProcessManager process) {
        this.idProcess = process.getIdProcess();
        this.processDescription = process.getProcessDescription();
        this.dateBegin = process.getDateBegin();
        this.dateEnd = process.getDateEnd();
        this.status = process.getStatus();
    }


    public UUID getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(UUID idProcess) {
        this.idProcess = idProcess;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
