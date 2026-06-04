package com.kosmoshub.service;

import com.kosmoshub.domain.Media;
import com.kosmoshub.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    @Transactional
    public Media createMedia(Media media) {
        return mediaRepository.save(media);
    }

    public Media getMediaById(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mídia não encontrada com o ID: " + id));
    }

    public List<Media> getMediaByEntity(Long entityId, String entityType) {
        return mediaRepository.findByEntityIdAndEntityType(entityId, entityType);
    }

    // Nota de Arquitetura: Ficheiros de mídia (URLs) não são "editados", são removidos e substituídos.
    // Portanto, não temos método updateMedia() aqui.

    @Transactional
    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
    }
}