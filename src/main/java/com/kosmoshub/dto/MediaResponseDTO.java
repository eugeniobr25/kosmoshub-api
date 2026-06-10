package com.kosmoshub.dto;

import com.kosmoshub.domain.Media;
import java.time.LocalDateTime;
import java.util.UUID;

public record MediaResponseDTO(
        UUID id,
        UUID userId, // Apenas o ID para não sobrecarregar o payload de mídias
        UUID entityId,
        Media.EntityType entityType,
        String url,
        String mediaType,
        LocalDateTime createdAt
) {
    public static MediaResponseDTO fromEntity(Media media) {
        return new MediaResponseDTO(
                media.getId(),
                media.getUser() != null ? media.getUser().getId() : null,
                media.getEntityId(),
                media.getEntityType(),
                media.getUrl(),
                media.getMediaType(),
                media.getCreatedAt()
        );
    }
}