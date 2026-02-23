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
@Table(name = "ANSWER")
@Where(clause = "ISDELETED = false")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDANSWER", nullable = false)
    @Type(type = "uuid-char")
    UUID idAnswer; 

    @Column(name = "ANSWERTEXT")
    private String answerText;

    @Column(name = "CREATEDAT")
    private LocalDate createdAt;

    @Column(name = "UPDATEDAT")
    private LocalDate updatedAt;

    @NotBlank
    @Size(max = 50)
    @Column(name = "DOCUMENTID")
    private String documentId;

    @NotBlank
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "IDUSER", nullable = true, referencedColumnName = "IDUSER")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "IDQUEST", nullable = false, referencedColumnName = "IDQUEST")
    private ProcessQuestionary processQuest;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "IDQUESTION", nullable = false, referencedColumnName = "IDQUESTION")
    private Question question;

    @Column(name = "UPDATEDBY", nullable = true)
    private String updatedBy;

    @Column(name = "ISDELETED")
    private boolean isDeleted;
}
