package com.agendaacademica.dto;
import com.agendaacademica.model.ActivityStatus;
import jakarta.validation.constraints.NotNull;
public record StatusRequest(@NotNull ActivityStatus status) {}
