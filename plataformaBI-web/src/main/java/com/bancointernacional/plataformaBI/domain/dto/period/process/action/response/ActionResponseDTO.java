package com.bancointernacional.plataformaBI.domain.dto.period.process.action.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionResponseDTO implements Serializable {

    private UUID actionId;
    private UUID processId;
    private String actionReason;
    private String actionType;
    private LocalDate createdAt;
    private boolean processReopened;
}
