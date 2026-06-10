package com.kosmoshub.controller;

import com.kosmoshub.dto.LoginRequestDTO;
import com.kosmoshub.dto.TokenResponseDTO;
import com.kosmoshub.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        TokenResponseDTO response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}