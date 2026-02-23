package com.bancointernacional.plataformaBI.models.DTO;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.bancointernacional.plataformaBI.ErrorManagment.CustomException;
import com.bancointernacional.plataformaBI.models.entities.OptionsQuestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OptionsQuestionDTO {


    private UUID idOption;

    private String optionText;

    private LocalDate createdAt;

    private Integer idQuestion;


    public static OptionsQuestionDTO build( OptionsQuestion optionsQuestion)
    {

         if(optionsQuestion == null)
        {
             throw new CustomException("Entity OptionsQuestion no valido", HttpStatus.NOT_FOUND);
        }

        return new OptionsQuestionDTO(
            optionsQuestion.getIdOption(),
            optionsQuestion.getOptionText(),
            optionsQuestion.getCreatedAt(),
            optionsQuestion.getQuestion().getIdQuestion()
        );

    }
}
