package com.kosmoshub.repository;

import com.kosmoshub.domain.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    List<Interaction> findByEntityIdAndEntityType(Long entityId, String entityType);

    List<Interaction> findByUserId(Long userId);
}