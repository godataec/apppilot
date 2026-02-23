package com.bancointernacional.plataformaBI.domain.dto.azure;

import lombok.Data;
import java.util.List;

@Data
public class AzureResponseDTO<T> {
    private List<T> value;
}
