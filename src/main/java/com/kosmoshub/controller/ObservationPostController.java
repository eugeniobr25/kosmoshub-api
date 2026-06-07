package com.kosmoshub.controller;

import com.kosmoshub.domain.ObservationPost;
import com.kosmoshub.dto.ObservationPostResponseDTO;
import com.kosmoshub.service.ObservationPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class ObservationPostController {

    private final ObservationPostService postService;

    @PostMapping
    public ResponseEntity<ObservationPostResponseDTO> createPost(@Valid @RequestBody ObservationPost post) {
        ObservationPost createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ObservationPostResponseDTO.fromEntity(createdPost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservationPostResponseDTO> getPostById(@PathVariable UUID id) {
        ObservationPost post = postService.getPostById(id);
        return ResponseEntity.ok(ObservationPostResponseDTO.fromEntity(post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<org.springframework.data.domain.Page<ObservationPostResponseDTO>> getPostsByUser(
            @PathVariable UUID userId,
            @org.springframework.data.web.PageableDefault(size = 10) org.springframework.data.domain.Pageable pageable) {

        org.springframework.data.domain.Page<ObservationPost> postsPage = postService.getPostsByUser(userId, pageable);

        return ResponseEntity.ok(postsPage.map(ObservationPostResponseDTO::fromEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObservationPost> updatePost(@PathVariable UUID id, @Valid @RequestBody ObservationPost post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}