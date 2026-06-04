package com.kosmoshub.controller;

import com.kosmoshub.domain.Media;
import com.kosmoshub.service.MediaService;
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
    public ResponseEntity<Media> createMedia(@RequestBody Media media) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaService.createMedia(media));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> getMediaById(@PathVariable UUID id) {
        return ResponseEntity.ok(mediaService.getMediaById(id));
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<List<Media>> getMediaByEntity(@PathVariable String entityType, @PathVariable UUID entityId) {
        return ResponseEntity.ok(mediaService.getMediaByEntity(entityId, entityType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable UUID id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }
}