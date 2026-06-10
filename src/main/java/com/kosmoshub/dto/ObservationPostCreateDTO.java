package com.kosmoshub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ObservationPostCreateDTO(

        UUID planId,

        @NotBlank(message = "O nome do alvo celeste é obrigatório.")
        @Size(max = 100, message = "O nome do alvo não pode exceder 100 caracteres.")
        String targetName,

        @Size(max = 2000, message = "Os metadados do equipamento não podem exceder 2000 caracteres.")
        String equipmentMetadata
) {}