package com.kosmoshub.controller;

import com.kosmoshub.domain.Interaction;
import com.kosmoshub.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @PostMapping
    public ResponseEntity<Interaction> createInteraction(@RequestBody Interaction interaction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(interactionService.createInteraction(interaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interaction> getInteractionById(@PathVariable Long id) {
        return ResponseEntity.ok(interactionService.getInteractionById(id));
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<List<Interaction>> getInteractionsByEntity(@PathVariable String entityType, @PathVariable Long entityId) {
        return ResponseEntity.ok(interactionService.getInteractionsByEntity(entityId, entityType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interaction> updateInteraction(@PathVariable Long id, @RequestBody Interaction interaction) {
        return ResponseEntity.ok(interactionService.updateInteraction(id, interaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteraction(@PathVariable Long id) {
        interactionService.deleteInteraction(id);
        return ResponseEntity.noContent().build();
    }
}