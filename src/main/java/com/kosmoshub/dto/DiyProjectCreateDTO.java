package com.kosmoshub.dto;

import jakarta.validation.constraints.NotBlank;

public record DiyProjectCreateDTO(
        @NotBlank(message = "O título é obrigatório.")
        String title,

        @NotBlank(message = "O conteúdo é obrigatório.")
        String content,

        Boolean isPublic
) {}