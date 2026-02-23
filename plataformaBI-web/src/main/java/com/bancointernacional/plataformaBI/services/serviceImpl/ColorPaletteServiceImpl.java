package com.bancointernacional.plataformaBI.services.serviceImpl;

import com.bancointernacional.plataformaBI.mapper.ColorPaletteMapper;
import com.bancointernacional.plataformaBI.models.DTO.ColorPaletteDTO;
import com.bancointernacional.plataformaBI.repositories.ColorPaletteRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.ColorPaletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorPaletteServiceImpl implements ColorPaletteService {

    @Autowired
    ColorPaletteRepository colorPaletteRepository;

    @Autowired
    ColorPaletteMapper colorPaletteMapper;

    @Override
    public List<ColorPaletteDTO> getColorsPalette() {
        return colorPaletteRepository.findAllActiveColors().stream().map(
                colorPalette -> colorPaletteMapper.toDto(colorPalette)
        ).collect(Collectors.toList());
    }
}
