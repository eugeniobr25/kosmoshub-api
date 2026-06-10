package com.kosmoshub.dto;

import com.kosmoshub.domain.DiyProject;
import java.util.UUID;

public record DiyProjectResponseDTO(
        UUID id,
        UserResponseDTO user,
        String title,
        String content,
        Double averageRating,
        Integer totalVotes,
        Boolean isPublic,
        Boolean isFinished
) {
    public static DiyProjectResponseDTO fromEntity(DiyProject project) {
        return new DiyProjectResponseDTO(
                project.getId(),
                UserResponseDTO.fromEntity(project.getUser()),
                project.getTitle(),
                project.getContent(),
                project.getAverageRating(),
                project.getTotalVotes(),
                project.getIsPublic(),
                project.getIsFinished()
        );
    }
}