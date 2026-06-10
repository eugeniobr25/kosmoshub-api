package com.kosmoshub.service;

import com.kosmoshub.domain.User;
import com.kosmoshub.dto.LoginRequestDTO;
import com.kosmoshub.dto.TokenResponseDTO;
import com.kosmoshub.repository.UserRepository;
import com.kosmoshub.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public TokenResponseDTO authenticate(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new BadCredentialsException("Email ou senha incorretos."));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BadCredentialsException("Email ou senha incorretos.");
        }

        String token = tokenService.generateToken(user);
        return new TokenResponseDTO(token);
    }
}