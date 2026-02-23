package com.bancointernacional.plataformaBI.domain.dto.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorPalleteDTO implements Serializable {
    private String colorName;
    private String hexValue;
    private String description;
}
