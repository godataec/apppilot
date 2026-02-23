package com.bancointernacional.plataformaBI.domain.model.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question_option")
public class QuestionOption {

    @Id
    @Column(name = "option_id",nullable = false, columnDefinition = "uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID optionId;

    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @NotBlank
    @Size(max = 500)
    @Column(name = "option_text")
    private String optionText;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
