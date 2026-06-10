package com.kosmoshub.dto;

import com.kosmoshub.domain.Interaction.EntityType;
import com.kosmoshub.domain.Interaction.InteractionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record InteractionCreateDTO(
        @NotNull(message = "O ID da entidade alvo (Post ou Projeto) é obrigatório.")
        UUID entityId,

        @NotNull(message = "O tipo da entidade alvo é obrigatório.")
        EntityType entityType,

        @NotNull(message = "O tipo de interação (COMMENT, QUESTION...) é obrigatório.")
        InteractionType type,

        @NotBlank(message = "O conteúdo da interação não pode estar vazio.")
        @Size(max = 2000, message = "O conteúdo do comentário não pode exceder 2000 caracteres.")
        String content
) {}