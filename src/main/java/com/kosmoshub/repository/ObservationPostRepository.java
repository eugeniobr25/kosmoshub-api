package com.kosmoshub.repository;

import com.kosmoshub.domain.ObservationPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ObservationPostRepository extends JpaRepository<ObservationPost, UUID> {

    @EntityGraph(attributePaths = {"user", "plan"})
    Page<ObservationPost> findByUserId(UUID userId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM ObservationPost p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}