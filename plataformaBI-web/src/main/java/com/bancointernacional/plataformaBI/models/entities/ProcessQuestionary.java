package com.bancointernacional.plataformaBI.models.entities;

import java.time.LocalDate;
import java.util.UUID;

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
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PROCESSQUESTIONARY")
@Where(clause = "ISDELETED = false")
public class ProcessQuestionary {

    @Id
    @Column(name = "IDQUEST", nullable = false, columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID idQuest;

    @Column(name="DATEBEGINQUEST")
    private LocalDate dateBeginQuest;

    @Column(name="DATEENDQUEST")
    private LocalDate dateEndQuest;

    @NotBlank
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "IDQUESTIONNAIRE", nullable = false, referencedColumnName = "IDQUESTIONNAIRE")
    private Questionnaire questionnaire;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPolicy", nullable = false, referencedColumnName = "idPolicy")
    private ProcessInsurancePolicy processInsurancePolicy;

    @Column(name = "ISDELETED")
    private boolean isDeleted;

}
