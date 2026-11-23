package com.agendaacademica.dto;
import java.time.LocalDate;
import com.agendaacademica.model.*;
import jakarta.validation.constraints.*;
public record ActivityRequest(
 @NotBlank String subjectId,
 @NotBlank @Size(max=150) String title,
 @Size(max=1000) String description,
 @NotNull ActivityType type,
 @NotNull Priority priority,
 @NotNull LocalDate dueDate
) {}
