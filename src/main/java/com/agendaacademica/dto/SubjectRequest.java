package com.agendaacademica.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record SubjectRequest(
 @NotBlank @Size(max=100) String name,
 @NotBlank @Size(max=30) String code,
 @NotBlank @Size(max=100) String teacher
) {}
