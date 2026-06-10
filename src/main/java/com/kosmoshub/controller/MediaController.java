package com.kosmoshub.controller;

import com.kosmoshub.domain.Media;
import com.kosmoshub.dto.MediaCreateDTO;
import com.kosmoshub.domain.Media.EntityType;
import com.kosmoshub.dto.MediaResponseDTO;
import com.kosmoshub.service.MediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    public ResponseEntity<MediaResponseDTO> createMedia(@Valid @RequestBody MediaCreateDTO dto) {
        Media media = new Media();
        media.setEntityId(dto.entityId());
        media.setEntityType(dto.entityType());
        media.setUrl(dto.url());
        media.setMediaType(dto.mediaType());

        Media created = mediaService.createMedia(media);
        return ResponseEntity.status(HttpStatus.CREATED).body(MediaResponseDTO.fromEntity(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaResponseDTO> getMediaById(@PathVariable UUID id) {
        Media media = mediaService.getMediaById(id);
        return ResponseEntity.ok(MediaResponseDTO.fromEntity(media));
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<List<MediaResponseDTO>> getMediaByEntity(
            @PathVariable Media.EntityType entityType,
            @PathVariable UUID entityId) {

        List<Media> mediaList = mediaService.getMediaByEntity(entityId, entityType);

        List<MediaResponseDTO> response = mediaList.stream()
                .map(MediaResponseDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable UUID id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }
}