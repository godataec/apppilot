package com.bancointernacional.plataformaBI.domain.model.period;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_answer")
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_answer_id", nullable = false)
    @Type(type = "uuid-char")
    UUID answerId;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "answer_type")
    private String answerType;

    @Column(name = "answer_description")
    private String answerDescription;

    @Column(name = "created_at")
    private LocalDate cretaedAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Size(max = 50)
    @Column(name = "document_id")
    private String documentId;

    @NotBlank
    @Size(max = 20)
    @Column(name = "answer_status")
    private String answerStatus;

    @Column(name = "user_assigned")
    @Type(type = "uuid-char")
    private UUID userAssigned;

    @Column(name = "policy_questionary_id")
    @Type(type = "uuid-char")
    private UUID policyQuestionaryId;

    @Column(name = "question_id")
    private BigInteger questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_question_id", referencedColumnName = "user_question_id")
    private UserQuestion userQuestion;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
