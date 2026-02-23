package com.bancointernacional.plataformaBI.domain.model.period;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_question")
public class UserQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_question_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID userQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_questionary_id", referencedColumnName = "policy_questionary_id")
    private PolicyQuestionary policyQuestionary;

    @Column(name = "question_id")
    private BigInteger questionId;

    @Column(name = "user_assigned")
    @Type(type = "uuid-char")
    private UUID userAssigned;

    @Column(name = "question_status")
    private String questionStatus;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
