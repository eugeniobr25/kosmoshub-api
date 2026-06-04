package com.kosmoshub.service;

import com.kosmoshub.domain.Interaction;
import com.kosmoshub.repository.InteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final InteractionRepository interactionRepository;

    @Transactional
    public Interaction createInteraction(Interaction interaction) {
        return interactionRepository.save(interaction);
    }

    public Interaction getInteractionById(Long id) {
        return interactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interação não encontrada com o ID: " + id));
    }

    public List<Interaction> getInteractionsByEntity(Long entityId, String entityType) {
        return interactionRepository.findByEntityIdAndEntityType(entityId, entityType);
    }

    @Transactional
    public Interaction updateInteraction(Long id, Interaction updatedData) {
        Interaction existingInteraction = getInteractionById(id);

        // A Nossa Lógica de Coluna Sombra (Proteção do texto do comentário)
        if (updatedData.getContent() != null && !updatedData.getContent().equals(existingInteraction.getContent())) {
            existingInteraction.setPreviousContent(existingInteraction.getContent());
            existingInteraction.setContent(updatedData.getContent());
        }

        return interactionRepository.save(existingInteraction);
    }

    @Transactional
    public void deleteInteraction(Long id) {
        interactionRepository.deleteById(id);
    }
}