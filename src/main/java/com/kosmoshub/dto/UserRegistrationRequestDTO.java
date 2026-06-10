package com.kosmoshub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDTO(

        @NotBlank(message = "O nome de utilizador é obrigatório.")
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "O nome só pode conter letras, números e underlines.")
        String username,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O formato do email é inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres.")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9]).+$", message = "A senha deve conter pelo menos uma letra maiúscula e um número.")
        String password
) {}