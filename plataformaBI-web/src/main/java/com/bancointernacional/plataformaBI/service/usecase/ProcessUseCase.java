package com.bancointernacional.plataformaBI.service.usecase;

import com.bancointernacional.plataformaBI.domain.dto.period.process.request.CreateProcessDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.request.UpdateProcessDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.response.ProcessDTO;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProcessUseCase {
    List<ProcessDTO> findAll();
    List<ProcessDTO> filterBy(BigInteger subsidiary ,String characters,int page, int pageSize, String dateBegin, String dateEnd, BigInteger year);
    Integer filterByWithoutPagination(BigInteger subsidiary ,String characters, String dateBegin, String dateEnd);
    Optional<ProcessDTO> findByProcessId(UUID idProcess);
    ProcessDTO save(CreateProcessDTO process);
    ProcessDTO update(ProcessDTO process, UUID idProcess);
    ProcessDTO updateProcess(UpdateProcessDTO updateProcessDTO);
    void deleteLogicalProcess(UUID idProcess);

}
