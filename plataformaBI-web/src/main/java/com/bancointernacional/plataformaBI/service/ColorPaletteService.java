package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.settings.ColorPalleteDTO;
import com.bancointernacional.plataformaBI.domain.model.settings.Pallete;
import com.bancointernacional.plataformaBI.mapper.ColorPaletteMapper;
import com.bancointernacional.plataformaBI.repository.settings.PalleteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ColorPaletteService {

    @Autowired
    private PalleteRepository palleteRepository;

    @Autowired
    private ColorPaletteMapper colorPaletteMapper;

    /**
     * Get all active color palettes from the database
     * @return List of active color palettes
     */
    public List<ColorPalleteDTO> getAllActiveColorPalettes() {
        log.info("Retrieving all active color palettes");
        try {
            List<Pallete> palettes = palleteRepository.findAllActive();
            return palettes.stream()
                    .map(colorPaletteMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving color palettes: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving color palettes: " + e.getMessage());
        }
    }

    /**
     * Get all active color palettes by subsidiary
     * @param subsidiaryId the subsidiary ID
     * @return List of active color palettes for the subsidiary
     */
    public List<ColorPalleteDTO> getAllActiveColorPalettesBySubsidiary(Long subsidiaryId) {
        log.info("Retrieving all active color palettes for subsidiary: {}", subsidiaryId);
        try {
            List<Pallete> palettes = palleteRepository.findAllActiveBySubsidiary(subsidiaryId);
            return palettes.stream()
                    .map(colorPaletteMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving color palettes for subsidiary {}: {}", subsidiaryId, e.getMessage(), e);
            throw new RuntimeException("Error retrieving color palettes: " + e.getMessage());
        }
    }
}
