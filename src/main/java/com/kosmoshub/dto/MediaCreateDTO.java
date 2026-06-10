package com.kosmoshub.dto;

import com.kosmoshub.domain.Media.EntityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MediaCreateDTO(
        @NotNull(message = "O ID da entidade é obrigatório.")
        UUID entityId,

        @NotNull(message = "O tipo da entidade é obrigatório.")
        EntityType entityType,

        @NotBlank(message = "A URL da mídia não pode estar vazia.")
        String url,

        @NotBlank(message = "O tipo de mídia (ex: image/jpeg) é obrigatório.")
        String mediaType
) {}