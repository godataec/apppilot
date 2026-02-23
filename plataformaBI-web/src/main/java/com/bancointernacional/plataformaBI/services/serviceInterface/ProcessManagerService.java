package com.bancointernacional.plataformaBI.services.serviceInterface;


import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.ProcessmanagerDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessManager;

 public interface ProcessManagerService {

      List<ProcessManager> findAll();
      List<ProcessManager> findAllforPage(int offset, int limit);
      List<ProcessManager> findByDates(String fechaInicio, String fechaFin);
      List<ProcessManager> findByCharacters(String characters,int page, int pageSize, String dateBegin, String dateEnd, BigInteger year);
      Integer findByCharactersWithoutPagination(String characters, String dateBegin, String dateEnd);

      Optional<ProcessManager> findByidProcess(UUID idProcess);
      Optional<ProcessmanagerDTO> save(ProcessManager process);
      Optional<ProcessManager> update(ProcessManager process, UUID idProcess);
      boolean remove(UUID idProcess);

    void deleteLogicalProcess(UUID idProcess);
    
}
