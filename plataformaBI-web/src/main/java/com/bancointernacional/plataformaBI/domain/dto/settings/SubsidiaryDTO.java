package com.bancointernacional.plataformaBI.domain.dto.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubsidiaryDTO implements Serializable {
    private BigInteger subsidiaryId;
    private String groupName;
    private String description;
}
