package com.bancointernacional.plataformaBI.domain.model.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="question")
public class Question {

    @Id
    @Column(name = "question_id", nullable = false)
    Integer idQuestion;

    @NotBlank
    @Size(max = 20)
    @Column(name = "question_type")
    String questionType;

    @NotBlank
    @Size(max = 900)
    @Column(name = "description")
    String description;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "question_text")
    String questionText;

    @NotBlank
    @Size(max = 1300)
    @Column(name = "question_json")
    String questionJson;

    @Column(name = "parent_id")
    Integer parentId;

    @Column(name = "document_id")
    String documentId;

    @Column(name = "questionary_id")
    private BigInteger questionaryId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}
