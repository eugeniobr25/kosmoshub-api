package com.kosmoshub.service;

import com.kosmoshub.domain.Interaction;
import com.kosmoshub.exception.ResourceNotFoundException;
import com.kosmoshub.repository.InteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final InteractionRepository interactionRepository;

    @Transactional
    public Interaction createInteraction(Interaction interaction) {
        return interactionRepository.save(interaction);
    }

    public Interaction getInteractionById(UUID id) {
        return interactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interação não encontrada com o ID: " + id));
    }

    public List<Interaction> getInteractionsByEntity(UUID entityId, Interaction.EntityType entityType) {
        return interactionRepository.findByEntityIdAndEntityType(entityId, entityType);
    }

    @Transactional
    public Interaction updateInteraction(UUID id, Interaction updatedData) {
        Interaction existingInteraction = getInteractionById(id);

        if (updatedData.getContent() != null) {
            existingInteraction.setContent(updatedData.getContent());
        }

        return interactionRepository.save(existingInteraction);
    }

    @Transactional
    public void deleteInteraction(UUID id) {
        interactionRepository.deleteById(id);
    }
}