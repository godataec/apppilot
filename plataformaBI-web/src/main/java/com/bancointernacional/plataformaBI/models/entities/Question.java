package com.bancointernacional.plataformaBI.models.entities;

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
@Table(name="QUESTION")
@Where(clause = "ISDELETED = false")
public class Question {

    @Id
    @Column(name = "IDQUESTION", nullable = false)
    Integer idQuestion;

    @NotBlank
    @Size(max = 20)
    @Column(name = "TYPEQUESTION")
    String typequestion;

    @NotBlank
    @Size(max = 900)
    @Column(name = "DESCRIPTIONQUESTION")
    String descriptionquestion;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "QUESTIONTEXT")
    String questiontext;

    @NotBlank
    @Size(max = 1300)
    @Column(name = "QUESTIONJSON")
    String questionjson;

    @Column(name = "PARENTID")
    Integer parentid;

    @Column(name = "DOCUMENTID")
    String documentid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "IDQUESTIONNAIRE", nullable = false, referencedColumnName = "IDQUESTIONNAIRE")
    private Questionnaire questionnaire;

    @Column(name = "ISDELETED")
    private boolean isDeleted;
    
}
