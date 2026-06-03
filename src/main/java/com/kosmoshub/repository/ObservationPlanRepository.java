package com.kosmoshub.repository;

import com.kosmoshub.domain.ObservationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationPlanRepository extends JpaRepository<ObservationPlan, Long> {

    List<ObservationPlan> findByUserId(Long userId);

    List<ObservationPlan> findByStatus(String status);
}