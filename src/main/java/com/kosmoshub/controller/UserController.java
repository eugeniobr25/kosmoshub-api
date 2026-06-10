package com.kosmoshub.controller;

import com.kosmoshub.domain.User;
import com.kosmoshub.dto.UserRegistrationRequestDTO;
import com.kosmoshub.dto.UserResponseDTO;
import com.kosmoshub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRegistrationRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponseDTO.fromEntity(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        Page<User> usersPage = userService.getAllUsers(pageable); // Removi o cast (casting) desnecessário que causaria erro
        return ResponseEntity.ok(usersPage.map(UserResponseDTO::fromEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}