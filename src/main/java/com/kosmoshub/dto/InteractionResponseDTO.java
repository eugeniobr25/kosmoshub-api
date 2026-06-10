package com.kosmoshub.dto;

import com.kosmoshub.domain.Interaction;
import com.kosmoshub.domain.Interaction.EntityType;
import com.kosmoshub.domain.Interaction.InteractionType;

import java.time.LocalDateTime;
import java.util.UUID;

public record InteractionResponseDTO(
        UUID id,
        UserResponseDTO user,
        UUID entityId,
        EntityType entityType,
        InteractionType type,
        String content,
        String previousContent,
        LocalDateTime createdAt
) {
    public static InteractionResponseDTO fromEntity(Interaction interaction) {
        return new InteractionResponseDTO(
                interaction.getId(),
                interaction.getUser() != null ? UserResponseDTO.fromEntity(interaction.getUser()) : null,
                interaction.getEntityId(),
                interaction.getEntityType(),
                interaction.getType(),
                interaction.getContent(),
                interaction.getPreviousContent(),
                interaction.getCreatedAt()
        );
    }
}