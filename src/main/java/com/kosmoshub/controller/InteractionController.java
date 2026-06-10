package com.kosmoshub.controller;

import com.kosmoshub.domain.Interaction;
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

    // NOTA ARQUITETURAL: No futuro, faremos aqui o mesmo que fizemos no UserController,
    // substituindo a Entidade Interaction crua por InteractionCreateDTO e InteractionResponseDTO
    // para blindagem total contra Mass Assignment. Por agora, vamos focar em compilar e migrar!

    @PostMapping
    public ResponseEntity<Interaction> createInteraction(@Valid @RequestBody Interaction interaction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(interactionService.createInteraction(interaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interaction> getInteractionById(@PathVariable UUID id) {
        return ResponseEntity.ok(interactionService.getInteractionById(id));
    }

    // A MÁGICA AQUI: O Spring converte a String da URL diretamente para o nosso Enum!
    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<List<Interaction>> getInteractionsByEntity(
            @PathVariable Interaction.EntityType entityType,
            @PathVariable UUID entityId) {

        return ResponseEntity.ok(interactionService.getInteractionsByEntity(entityId, entityType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interaction> updateInteraction(@PathVariable UUID id, @Valid @RequestBody Interaction interaction) {
        return ResponseEntity.ok(interactionService.updateInteraction(id, interaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteraction(@PathVariable UUID id) {
        interactionService.deleteInteraction(id);
        return ResponseEntity.noContent().build();
    }
}