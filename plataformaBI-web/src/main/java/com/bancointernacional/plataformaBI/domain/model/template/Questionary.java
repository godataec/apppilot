package com.bancointernacional.plataformaBI.domain.model.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questionary")
@Where(clause = "is_deleted = false")
public class Questionary {
    @Id
    @Column(name = "questionary_id", nullable = false)
    private Integer questionaryId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "questionary_name")
    private String questionaryName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @NotBlank
    @Size(max = 20)
    @Column(name = "questionary_status")
    private String questionaryStatus;

    @NotBlank
    @Size(max = 50)
    @Column(name = "source_file")
    private String sourceFile;

    @Column(name = "policy_id")
    private BigInteger insurancePolicy;

    @Column(name = "total_number")
    private Integer totalquestion;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
