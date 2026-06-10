package com.kosmoshub.repository;

import com.kosmoshub.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media, UUID> {

    List<Media> findByEntityIdAndEntityType(UUID entityId, String entityType);

    @Modifying
    @Query("DELETE FROM Media m WHERE m.entityId = :entityId AND m.entityType = :entityType")
    void deleteByEntityIdAndEntityType(UUID entityId, String entityType);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("DELETE FROM Media m WHERE m.entityType = 'POST' AND m.entityId IN (SELECT p.id FROM ObservationPost p WHERE p.user.id = :userId)")
    void deleteMediaOnUserPosts(java.util.UUID userId);
}