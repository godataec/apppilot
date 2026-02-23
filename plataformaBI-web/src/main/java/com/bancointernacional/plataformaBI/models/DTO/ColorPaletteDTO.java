package com.bancointernacional.plataformaBI.models.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ColorPaletteDTO {

    private String colorName;
    private String hexValue;
    private String description;

}
