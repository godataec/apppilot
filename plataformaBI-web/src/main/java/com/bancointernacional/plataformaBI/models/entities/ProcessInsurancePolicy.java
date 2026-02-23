package com.bancointernacional.plataformaBI.models.entities;

import org.hibernate.annotations.Where;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name="PROCESSINSURANCEPOLICY")
@Where(clause = "ISDELETED = false")
public class ProcessInsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPolicy;

    @Column(name = "dateBegin")
    private LocalDate dateBegin;

    @Column(name = "dateEnd")
    private LocalDate dateEnd;

    @NotBlank
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idProcess", nullable = false, referencedColumnName = "idProcess")
    private ProcessManager process;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idInsurance", nullable = false, referencedColumnName = "idInsurance")
    private InsurancePolicy insurancePolicy;

    @Column(name = "ISDELETED")
    private boolean isDeleted;

    public ProcessInsurancePolicy() {
    }


    public ProcessInsurancePolicy(Long idPolicy, @NotBlank LocalDate dateBegin, @NotBlank LocalDate dateEnd,
            @NotBlank String status) {
        this.idPolicy = idPolicy;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
    }

    


    public ProcessInsurancePolicy(Long idPolicy, @NotBlank LocalDate dateBegin, @NotBlank LocalDate dateEnd,
            @NotBlank String status, ProcessManager process, InsurancePolicy insurancePolicy) {
        this.idPolicy = idPolicy;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
        this.process = process;
        this.insurancePolicy = insurancePolicy;
    }


    public Long getIdPolicy() {
        return idPolicy;
    }


    public void setIdPolicy(Long idPolicy) {
        this.idPolicy = idPolicy;
    }


    public LocalDate getDateBegin() {
        return dateBegin;
    }


    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }


    public LocalDate getDateEnd() {
        return dateEnd;
    }


    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public ProcessManager getProcess() {
        return process;
    }


    public void setProcess(ProcessManager process) {
        this.process = process;
    }


    public InsurancePolicy getInsurancePolicy() {
        return insurancePolicy;
    }


    public void setInsurancePolicy(InsurancePolicy insurancePolicy) {
        this.insurancePolicy = insurancePolicy;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
