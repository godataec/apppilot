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
@Table(name="process_policy")
public class ProcessPolicy {
    @Id
    @GeneratedValue
    @Column(name = "process_policy_id", columnDefinition = "uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID processPolicyId;

    @Column(name = "start_date")
    private LocalDate dateBegin;

    @Column(name = "end_date")
    private LocalDate dateEnd;

    @Column(name = "process_policy_status")
    private String processPolicyStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id", referencedColumnName = "process_id")
    private Process process;

    @Column(name = "policy_id")
    private BigInteger insurancePolicy;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
