package com.bancointernacional.plataformaBI.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bancointernacional.plataformaBI.models.entities.OptionsQuestion;

public interface OptionsQuestionRepository extends CrudRepository<OptionsQuestion, UUID>{


    @Query(value = "SELECT * FROM OPTIONS WHERE ISDELETED=0 AND IDQUESTION = ?1", nativeQuery = true)
    OptionsQuestion findByIdquestion(Integer idQuestion);

}
