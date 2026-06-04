package com.kosmoshub.repository;

import com.kosmoshub.domain.ObservationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ObservationPlanRepository extends JpaRepository<ObservationPlan, UUID> {

    List<ObservationPlan> findByUserId(UUID userId);

    List<ObservationPlan> findByStatus(String status);
}