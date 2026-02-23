package com.bancointernacional.plataformaBI.domain.dto.period.process.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProcessDTO implements Serializable {
    
    @NotNull(message = "Process ID cannot be null")
    private UUID processId;
    
    @NotBlank(message = "Process name cannot be blank")
    @Size(max = 255, message = "Process name cannot exceed 255 characters")
    private String processName;
    
    @NotNull(message = "Process start date cannot be null")
    private LocalDate processStartDate;
    
    @NotNull(message = "Process end date cannot be null")
    private LocalDate processEndDate;
    
    @NotBlank(message = "Process status cannot be blank")
    @Size(max = 50, message = "Process status cannot exceed 50 characters")
    private String processStatus;
}
