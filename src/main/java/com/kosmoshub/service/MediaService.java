package com.kosmoshub.service;

import com.kosmoshub.domain.Media;
import com.kosmoshub.exception.ResourceNotFoundException;
import com.kosmoshub.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    @Transactional
    public Media createMedia(Media media) {
        return mediaRepository.save(media);
    }

    public Media getMediaById(UUID id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mídia não encontrada com o ID: " + id));
    }

    public List<Media> getMediaByEntity(UUID entityId, Media.EntityType entityType) {
        return mediaRepository.findByEntityIdAndEntityType(entityId, entityType);
    }

    @Transactional
    public void deleteMedia(UUID id) {
        mediaRepository.deleteById(id);
    }

    @Transactional
    public void deleteMediaByEntity(UUID entityId, Media.EntityType entityType) {
        mediaRepository.deleteByEntityIdAndEntityType(entityId, entityType);
    }
}