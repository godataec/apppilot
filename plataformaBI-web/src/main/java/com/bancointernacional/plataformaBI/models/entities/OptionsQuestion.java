package com.bancointernacional.plataformaBI.models.entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "OPTIONS")
@Where(clause = "ISDELETED = false")
public class OptionsQuestion {

    @Id
    @Column(name = "IDOPTION", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID idOption;

    @NotBlank
    @Size(max = 500)
    @Column(name = "OPTIONTEXT")
    private String optionText;

    @Column(name = "CREATEDAT")
    private LocalDate createdAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "IDQUESTION", nullable = false, referencedColumnName = "IDQUESTION")
    private Question question;

    @Column(name = "ISDELETED")
    private boolean isDeleted;

}
