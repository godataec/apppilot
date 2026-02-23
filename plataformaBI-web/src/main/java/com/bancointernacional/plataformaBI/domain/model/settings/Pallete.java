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
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "color_pallete")
public class Pallete {

    @Id
    @Column(name = "pallete_id")
    private Long colorPalleteId;

    @Column(name = "pallete_name")
    private String color;

    @Column(name = "subsidiary_id")
    private BigInteger subsidiaryId;

    @Column(name = "hex_value")
    private String value;

    @Column(name = "description")
    private String description;

    @Column(name = "pallete_type")
    private String palleteType;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
