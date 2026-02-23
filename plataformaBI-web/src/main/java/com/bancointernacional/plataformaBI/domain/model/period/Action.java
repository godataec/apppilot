package com.bancointernacional.plataformaBI.domain.model.period;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="process_action")
public class Action {

    @Id
    @Column(name = "action_id", nullable = false, columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID actionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id", referencedColumnName = "process_id")
    private Process process;

    @Column(name = "action_reason")
    private String actionReason;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
