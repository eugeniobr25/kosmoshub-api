package com.kosmoshub.repository;

import com.kosmoshub.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media, UUID> {

    List<Media> findByEntityIdAndEntityType(UUID entityId, Media.EntityType entityType);

    @Modifying
    @Query("DELETE FROM Media m WHERE m.entityId = :entityId AND m.entityType = :entityType")
    void deleteByEntityIdAndEntityType(@Param("entityId") UUID entityId, @Param("entityType") Media.EntityType entityType);

    @Modifying
    @Query("DELETE FROM Media m WHERE m.entityType = com.kosmoshub.domain.Media.EntityType.POST AND m.entityId IN (SELECT p.id FROM ObservationPost p WHERE p.user.id = :userId)")
    void deleteMediaOnUserPosts(@Param("userId") UUID userId);
}