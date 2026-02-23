package com.bancointernacional.plataformaBI.controllers;

import com.bancointernacional.plataformaBI.models.DTO.ColorPaletteDTO;
import com.bancointernacional.plataformaBI.services.serviceInterface.ColorPaletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/config")
@RestController
public class ColorPaletteController {

    @Autowired
    ColorPaletteService coloerServoce;

    @GetMapping("/color")
    public ResponseEntity<?> getColorSchema() {
        List<ColorPaletteDTO> lisOfEntityColors = coloerServoce.getColorsPalette();
        return new ResponseEntity<>(lisOfEntityColors, HttpStatus.OK);
    }
}
