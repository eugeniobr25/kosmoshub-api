package com.kosmoshub.controller;

import com.kosmoshub.domain.ObservationPost;
import com.kosmoshub.service.ObservationPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class ObservationPostController {

    private final ObservationPostService postService;

    @PostMapping
    public ResponseEntity<ObservationPost> createPost(@RequestBody ObservationPost post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservationPost> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ObservationPost>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObservationPost> updatePost(@PathVariable Long id, @RequestBody ObservationPost post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}