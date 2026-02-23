package com.bancointernacional.plataformaBI.repositories;

import com.bancointernacional.plataformaBI.models.entities.Answer;
import com.bancointernacional.plataformaBI.models.entities.ColorPalette;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ColorPaletteRepository  extends CrudRepository<ColorPalette, Long> {

    @Query(value = "SELECT * FROM COLORPALETTE WHERE ISDELETED = 0", nativeQuery = true)
    List<ColorPalette> findAllActiveColors();

}
