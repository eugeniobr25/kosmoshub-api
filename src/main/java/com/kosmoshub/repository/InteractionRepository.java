package com.kosmoshub.repository;

import com.kosmoshub.domain.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, UUID> {

    List<Interaction> findByEntityIdAndEntityType(UUID entityId, String entityType);

    List<Interaction> findByUserId(UUID userId);
}