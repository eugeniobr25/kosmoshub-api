package com.kosmoshub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ObservationPlanCreateDTO(
        @NotBlank(message = "O nome do alvo é obrigatório.")
        String targetName,

        @NotNull(message = "O horário planejado é obrigatório.")
        LocalDateTime plannedTimestamp,

        Boolean notifyInAdvance
) {}