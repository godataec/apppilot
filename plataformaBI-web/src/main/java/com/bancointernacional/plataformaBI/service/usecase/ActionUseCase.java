package com.bancointernacional.plataformaBI.service.usecase;

import com.bancointernacional.plataformaBI.domain.dto.period.process.action.request.ProcessActionDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.action.response.ActionResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ActionUseCase {
    ActionResponseDTO createAction(ProcessActionDTO processActionDTO) ;
    List<ActionResponseDTO> getActionsByProcessId(UUID processId);
    ActionResponseDTO getActionById(UUID actionId) ;
}
