package com.kosmoshub.service;

import com.kosmoshub.domain.Interaction;
import com.kosmoshub.domain.User;
import com.kosmoshub.exception.ResourceNotFoundException;
import com.kosmoshub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DiyProjectRepository diyProjectRepository;
    private final ObservationPlanRepository observationPlanRepository;
    private final InteractionRepository interactionRepository;
    private final MediaRepository mediaRepository;
    private final ObservationPostRepository postRepository;

    @Transactional
    public User createUser(User user) {
        String senhaCriptografada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCriptografada);
        return userRepository.save(user);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilizador não encontrado com o ID: " + id));
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilizador não encontrado com o ID: " + id);
        }
        interactionRepository.deleteByUserId(id);
        interactionRepository.deleteInteractionsOnUserPosts(id, Interaction.EntityType.POST);
        mediaRepository.deleteMediaOnUserPosts(id);
        postRepository.deleteByUserId(id);
        diyProjectRepository.deleteByUserId(id);
        observationPlanRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }
}