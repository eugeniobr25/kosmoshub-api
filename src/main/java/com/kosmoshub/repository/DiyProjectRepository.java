package com.kosmoshub.repository;

import com.kosmoshub.domain.DiyProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DiyProjectRepository extends JpaRepository<DiyProject, UUID> {

    @EntityGraph(attributePaths = {"user"})
    List<DiyProject> findByUserId(UUID userId);

    @EntityGraph(attributePaths = {"user"})
    Page<DiyProject> findByIsPublicTrueAndIsFinishedTrue(Pageable pageable);

    // O Pedaço que faltava para a exclusão de conta em Cascata
    @Modifying
    @Query("DELETE FROM DiyProject d WHERE d.user.id = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}