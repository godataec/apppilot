package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.settings.ColorPalleteDTO;
import com.bancointernacional.plataformaBI.domain.model.settings.Pallete;

import org.springframework.stereotype.Component;

@Component
public class ColorPaletteMapper {

    public ColorPalleteDTO toDto(Pallete entity){
        ColorPalleteDTO dto = new ColorPalleteDTO();
        dto.setColorName(entity.getColor());
        dto.setHexValue(entity.getValue());
        dto.setDescription(entity.getDescription());
        return dto;
    };


    public Pallete toEntity(ColorPalleteDTO dto){
        Pallete entity = new Pallete();
        entity.setColor(dto.getColorName());
        entity.setValue(dto.getHexValue());
        entity.setDescription(dto.getDescription());
        return entity;
    };


    Pallete fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pallete colorPalette = new Pallete();
        colorPalette.setColorPalleteId(id);
        return colorPalette;
    }
}