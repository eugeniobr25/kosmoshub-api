package com.kosmoshub.service;

import com.kosmoshub.domain.ObservationPost;
import com.kosmoshub.repository.ObservationPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObservationPostService {

    private final ObservationPostRepository postRepository;

    @Transactional
    public ObservationPost createPost(ObservationPost post) {
        return postRepository.save(post);
    }

    public ObservationPost getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada com o ID: " + id));
    }

    public List<ObservationPost> getPostsByUserId(UUID userId) {
        return postRepository.findByUserId(userId);
    }

    @Transactional
    public ObservationPost updatePost(UUID id, ObservationPost updatedData) {
        ObservationPost existingPost = getPostById(id);

        // A Nossa Lógica de Coluna Sombra (Proteção dos metadados de equipamento)
        if (updatedData.getEquipmentMetadata() != null && !updatedData.getEquipmentMetadata().equals(existingPost.getEquipmentMetadata())) {
            existingPost.setPreviousEquipmentMetadata(existingPost.getEquipmentMetadata());
            existingPost.setEquipmentMetadata(updatedData.getEquipmentMetadata());
        }

        if (updatedData.getTargetName() != null) {
            existingPost.setTargetName(updatedData.getTargetName());
        }

        return postRepository.save(existingPost);
    }

    @Transactional
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }
}