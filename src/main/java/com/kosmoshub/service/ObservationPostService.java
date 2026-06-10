package com.kosmoshub.service;

import com.kosmoshub.domain.Interaction;
import com.kosmoshub.domain.Media;
import com.kosmoshub.domain.ObservationPost;
import com.kosmoshub.exception.ResourceNotFoundException;
import com.kosmoshub.repository.InteractionRepository;
import com.kosmoshub.repository.MediaRepository;
import com.kosmoshub.repository.ObservationPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObservationPostService {

    private final ObservationPostRepository postRepository;
    private final InteractionRepository interactionRepository;
    private final MediaRepository mediaRepository;

    @Transactional
    public ObservationPost createPost(ObservationPost post) {
        return postRepository.save(post);
    }

    public ObservationPost getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postagem não encontrada com o ID: " + id));
    }

    public Page<ObservationPost> getPostsByUser(UUID userId, Pageable pageable) {
        return postRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public ObservationPost updatePost(UUID id, ObservationPost updatedData) {
        ObservationPost existingPost = getPostById(id);

        if (updatedData.getEquipmentMetadata() != null) {
            existingPost.setEquipmentMetadata(updatedData.getEquipmentMetadata());
        }
        if (updatedData.getTargetName() != null) {
            existingPost.setTargetName(updatedData.getTargetName());
        }

        return postRepository.save(existingPost);
    }

    @Transactional
    public void deletePost(UUID id) {
        interactionRepository.deleteByEntityIdAndEntityType(id, Interaction.EntityType.POST);
        mediaRepository.deleteByEntityIdAndEntityType(id, Media.EntityType.POST);
        postRepository.deleteById(id);
    }
}