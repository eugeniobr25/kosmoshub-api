package com.kosmoshub.controller;

import com.kosmoshub.domain.Interaction;
import com.kosmoshub.dto.InteractionCreateDTO;
import com.kosmoshub.dto.InteractionResponseDTO;
import com.kosmoshub.service.InteractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @PostMapping
    public ResponseEntity<InteractionResponseDTO> createInteraction(@Valid @RequestBody InteractionCreateDTO dto) {
        Interaction interaction = new Interaction();
        interaction.setEntityId(dto.entityId());
        interaction.setEntityType(dto.entityType());
        interaction.setType(dto.type());
        interaction.setContent(dto.content());

        Interaction created = interactionService.createInteraction(interaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(InteractionResponseDTO.fromEntity(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InteractionResponseDTO> getInteractionById(@PathVariable UUID id) {
        Interaction interaction = interactionService.getInteractionById(id);
        return ResponseEntity.ok(InteractionResponseDTO.fromEntity(interaction));
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<List<InteractionResponseDTO>> getInteractionsByEntity(
            @PathVariable Interaction.EntityType entityType,
            @PathVariable UUID entityId) {

        List<Interaction> interactions = interactionService.getInteractionsByEntity(entityId, entityType);

        List<InteractionResponseDTO> responseList = interactions.stream()
                .map(InteractionResponseDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InteractionResponseDTO> updateInteraction(
            @PathVariable UUID id,
            @Valid @RequestBody InteractionCreateDTO dto) {

        Interaction updateData = new Interaction();
        updateData.setContent(dto.content());

        Interaction updated = interactionService.updateInteraction(id, updateData);
        return ResponseEntity.ok(InteractionResponseDTO.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteraction(@PathVariable UUID id) {
        interactionService.deleteInteraction(id);
        return ResponseEntity.noContent().build();
    }
}