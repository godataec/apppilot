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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "policy", schema = "dbo")
@Where(clause = "is_deleted = false")
public class Policy {

    @Id
    @Column(name = "policy_id")
    private Long policyId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "policy_name", unique = true)
    private String policyName;

    @NotBlank
    @Size(max = 20)
    @Column(name = "created_at")
    private String createdAt;


    @Size(max = 20)
    @Column(name = "updated_at")
    private String updatedAt;

    @NotBlank
    @Size(max = 20)
    @Column(name = "policy_status")
    private String policyStatus;


    @Size(max = 100)
    @Column(name = "policy_description")
    private String policyDescription;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
