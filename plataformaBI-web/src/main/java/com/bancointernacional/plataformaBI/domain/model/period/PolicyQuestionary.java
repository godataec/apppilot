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
@Table(name="policy_questionary")
public class PolicyQuestionary {

    @Id
    @Column(name = "policy_questionary_id", nullable = false, columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID policyQuestionaryId;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @NotBlank
    @Size(max = 20)
    @Column(name = "policy_questionary_status")
    private String policyQuestionaryStatus;

    @Column(name = "questionary_id")
    private BigInteger questionaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_policy_id", referencedColumnName = "process_policy_id")
    private ProcessPolicy processPolicy;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
