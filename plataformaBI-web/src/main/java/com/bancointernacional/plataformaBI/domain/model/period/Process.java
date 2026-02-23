package com.bancointernacional.plataformaBI.domain.model.period;

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
@Table(name="process")
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "process_id", columnDefinition = "uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID processId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "process_description", unique = true)
    private String processDescription;

    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotBlank
    @Size(max = 20)
    @Column(name = "process_status")
    private String processStatus;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "reopened")
    private boolean reopened;

    @Column(name = "subsidiary_id")
    private Long subsidiaryId;
}