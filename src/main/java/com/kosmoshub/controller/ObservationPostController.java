package com.kosmoshub.controller;

import com.kosmoshub.domain.ObservationPost;
import com.kosmoshub.dto.ObservationPostCreateDTO;
import com.kosmoshub.dto.ObservationPostResponseDTO;
import com.kosmoshub.repository.ObservationPostRepository;
import com.kosmoshub.service.ObservationPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class ObservationPostController {

    private final ObservationPostService postService;
    private final ObservationPostRepository postRepository; // <-- Adicionamos isto aqui!

    @PostMapping
    public ResponseEntity<ObservationPostResponseDTO> createPost(@Valid @RequestBody ObservationPostCreateDTO dto) {
        ObservationPost post = new ObservationPost();
        post.setTargetName(dto.targetName());
        post.setEquipmentMetadata(dto.equipmentMetadata());
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
    public ResponseEntity<Page<ObservationPostResponseDTO>> getPostsByUser(
            @PathVariable UUID userId,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<ObservationPost> postsPage = postService.getPostsByUser(userId, pageable);
        return ResponseEntity.ok(postsPage.map(ObservationPostResponseDTO::fromEntity));
    }

    @GetMapping
    public ResponseEntity<Page<ObservationPostResponseDTO>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ObservationPost> feed = postRepository.findAll(pageable);
        return ResponseEntity.ok(feed.map(ObservationPostResponseDTO::fromEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObservationPostResponseDTO> updatePost(@PathVariable UUID id, @Valid @RequestBody ObservationPostCreateDTO dto) {
        ObservationPost updateData = new ObservationPost();
        updateData.setTargetName(dto.targetName());
        updateData.setEquipmentMetadata(dto.equipmentMetadata());

        ObservationPost updatedPost = postService.updatePost(id, updateData);
        return ResponseEntity.ok(ObservationPostResponseDTO.fromEntity(updatedPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}