package com.bancointernacional.plataformaBI.domain.model.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_history")
public class AuditHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "audit_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID auditId;

    @NotBlank(message = "Entity name is required")
    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Size(max = 20, message = "Action must not exceed 20 characters")
    @Column(name = "action", length = 20)
    private String action;

    @NotBlank(message = "Entity ID is required")
    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @NotBlank(message = "JSON request is required")
    @Column(name = "json_request", nullable = false)
    private String jsonRequest;

    @Column(name = "action_date")
    private LocalDateTime actionDate;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (actionDate == null) {
            actionDate = LocalDateTime.now();
        }
    }
}
