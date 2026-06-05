package com.kosmoshub.controller;

import com.kosmoshub.domain.User;
import com.kosmoshub.repository.UserRepository;
import com.kosmoshub.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    // Criamos um Record rápido apenas para receber os dados do JSON do frontend
    public record LoginRequest(String email, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 1. Procura o utilizador pelo email na base de dados
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado com este email!"));

        // 2. A Mágica do BCrypt: Compara a senha em texto limpo com o Hash guardado no banco
        if (passwordEncoder.matches(request.password(), user.getPassword())) {

            // 3. Senha correta! Fabrica o crachá e devolve.
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token));

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta!");
        }
    }
}