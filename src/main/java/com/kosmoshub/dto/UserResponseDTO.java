package com.kosmoshub.dto;

import com.kosmoshub.domain.User;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        String email,
        String avatarUrl,
        String bio,
        Integer totalScore,
        User.Role role
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getBio(),
                user.getTotalScore(),
                user.getRole()
        );
    }
}