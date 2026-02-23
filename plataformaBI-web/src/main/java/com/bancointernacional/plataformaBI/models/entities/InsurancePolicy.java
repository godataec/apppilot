package com.bancointernacional.plataformaBI.models.entities;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
@Table(name="INSURANCEPOLICY")
@Where(clause = "ISDELETED = false")
public class InsurancePolicy {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInsurance;

    @NotBlank
    @Size(max = 50)
    @Column(name = "nameInsurance", unique = true)
    private String nameInsurance;

    @NotBlank
    @Size(max = 20)
    @Column(name = "createdAt")
    private String createdAt;

 
    @Size(max = 20)
    @Column(name = "updatedAt")
    private String updatedAt;

    @NotBlank
    @Size(max = 20)
    @Column(name = "status")
    private String status;

    
    @Size(max = 100)
    @Column(name = "description")
    private String description;

    @Column(name = "ISDELETED")
    private boolean isDeleted;

    public InsurancePolicy() {
    }


    public InsurancePolicy(Long idInsurance, @NotBlank @Size(max = 50) String nameInsurance,
            @NotBlank @Size(max = 20) String createdAt, @Size(max = 20) String updatedAt,
            @NotBlank @Size(max = 20) String status, @Size(max = 100) String description) {
        this.idInsurance = idInsurance;
        this.nameInsurance = nameInsurance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.description = description;
    }


    public Long getIdInsurance() {
        return idInsurance;
    }


    public void setIdInsurance(Long idInsurance) {
        this.idInsurance = idInsurance;
    }


    public String getNameInsurance() {
        return nameInsurance;
    }


    public void setNameInsurance(String nameInsurance) {
        this.nameInsurance = nameInsurance;
    }


    public String getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
