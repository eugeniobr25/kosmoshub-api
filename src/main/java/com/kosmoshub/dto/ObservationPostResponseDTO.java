package com.kosmoshub.dto;

import com.kosmoshub.domain.ObservationPost;
import java.util.UUID;

public record ObservationPostResponseDTO(
        UUID id,
        UserResponseDTO user,
        UUID planId,
        String targetName,
        String equipmentMetadata,
        Double averageRating,
        Integer totalVotes
) {
    public static ObservationPostResponseDTO fromEntity(ObservationPost post) {
        return new ObservationPostResponseDTO(
                post.getId(),
                UserResponseDTO.fromEntity(post.getUser()),
                post.getPlan() != null ? post.getPlan().getId() : null,
                post.getTargetName(),
                post.getEquipmentMetadata(),
                post.getAverageRating(),
                post.getTotalVotes()
        );
    }
}