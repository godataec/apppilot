package com.bancointernacional.plataformaBI.models.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="QUESTIONNAIRE")
@Where(clause = "ISDELETED = false")
public class Questionnaire {

    @Id
    @Column(name = "IDQUESTIONNAIRE", nullable = false)
    Integer idquestionnaire;

    @NotBlank
    @Size(max = 50)
    @Column(name = "NAMEQUESTIONNAIRE")
    String namequestionnaire;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    String description;

    @Column(name = "CREATEDAT")
    LocalDate createdAt;

    @NotBlank
    @Size(max = 20)
    @Column(name = "STATUS")
    String status;

    @NotBlank
    @Size(max = 50)
    @Column(name = "SOURCEFILE")
    String sourceFile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idInsurance", nullable = false, referencedColumnName = "idInsurance")
    private InsurancePolicy insurancePolicy;

    @Column(name = "TOTALQUESTION")
    private Integer totalquestion;

    @Column(name = "ISDELETED")
    private boolean isDeleted;
}
