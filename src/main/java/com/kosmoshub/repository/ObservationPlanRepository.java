package com.kosmoshub.repository;

import com.kosmoshub.domain.ObservationPlan;
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
public interface ObservationPlanRepository extends JpaRepository<ObservationPlan, UUID> {

    // Busca global blindada para o Feed
    @EntityGraph(attributePaths = {"user"})
    Page<ObservationPlan> findAll(Pageable pageable);

    // 👇 A CORREÇÃO: O método que faltava para o seu Service não quebrar
    @EntityGraph(attributePaths = {"user"})
    Page<ObservationPlan> findByUserId(UUID userId, Pageable pageable);

    List<ObservationPlan> findByStatus(ObservationPlan.PlanStatus status);

    @Modifying
    @Query("DELETE FROM ObservationPlan o WHERE o.user.id = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}