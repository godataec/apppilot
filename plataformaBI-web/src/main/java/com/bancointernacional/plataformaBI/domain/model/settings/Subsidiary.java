package com.bancointernacional.plataformaBI.domain.model.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subsidiary")
public class Subsidiary {
    @Id
    @Column(name = "subsidiary_id")
    private Long subsidiaryId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
