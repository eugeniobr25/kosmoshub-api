package com.kosmoshub.controller;

import com.kosmoshub.domain.User;
import com.kosmoshub.dto.UserResponseDTO;
import com.kosmoshub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody User user) {
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
    public ResponseEntity<org.springframework.data.domain.Page<UserResponseDTO>> getAllUsers(
            @org.springframework.data.web.PageableDefault(size = 10) org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Page<User> usersPage = (org.springframework.data.domain.Page<User>) userService.getAllUsers(pageable);
        return ResponseEntity.ok(usersPage.map(UserResponseDTO::fromEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}