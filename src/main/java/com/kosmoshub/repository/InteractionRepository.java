package com.kosmoshub.repository;

import com.kosmoshub.domain.Interaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, UUID> {

    @EntityGraph(attributePaths = {"user"})
    List<Interaction> findByEntityIdAndEntityType(UUID entityId, Interaction.EntityType entityType);

    @EntityGraph(attributePaths = {"user"})
    List<Interaction> findByUserId(UUID userId);

    @Modifying
    @Query("DELETE FROM Interaction i WHERE i.entityId = :id AND i.entityType = :entityType")
    void deleteByEntityIdAndEntityType(@Param("id") UUID id, @Param("entityType") Interaction.EntityType entityType);

    @Modifying
    @Query("DELETE FROM Interaction i WHERE i.user.id = :userId")
    void deleteByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM Interaction i WHERE i.entityType = :postType AND i.entityId IN (SELECT p.id FROM ObservationPost p WHERE p.user.id = :userId)")
    void deleteInteractionsOnUserPosts(@Param("userId") UUID userId, @Param("postType") Interaction.EntityType postType);
}