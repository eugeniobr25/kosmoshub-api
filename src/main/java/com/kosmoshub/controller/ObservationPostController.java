package com.kosmoshub.controller;

import com.kosmoshub.domain.ObservationPost;
import com.kosmoshub.dto.ObservationPostCreateDTO;
import com.kosmoshub.dto.ObservationPostResponseDTO;
import com.kosmoshub.service.ObservationPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping
    public ResponseEntity<ObservationPostResponseDTO> createPost(@Valid @RequestBody ObservationPostCreateDTO dto) {
        // Num ambiente real, podemos usar MapStruct para isso. Aqui fazemos o parse manual seguro:
        ObservationPost post = new ObservationPost();
        post.setTargetName(dto.targetName());
        post.setEquipmentMetadata(dto.equipmentMetadata());
        // A vinculação do User e Plan seria feita na camada de Serviço usando o ID do usuário logado

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