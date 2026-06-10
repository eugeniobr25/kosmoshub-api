package com.kosmoshub.service;

import com.kosmoshub.domain.ObservationPlan;
import com.kosmoshub.exception.ResourceNotFoundException;
import com.kosmoshub.repository.ObservationPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObservationPlanService {

    private final ObservationPlanRepository planRepository;

    @Transactional
    public ObservationPlan createPlan(ObservationPlan plan) {
        return planRepository.save(plan);
    }

    public ObservationPlan getPlanById(UUID id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plano de observação não encontrado com o ID: " + id));
    }

    public Page<ObservationPlan> getPlansByUser(UUID userId, Pageable pageable) {
        return planRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public ObservationPlan updatePlan(UUID id, ObservationPlan updatedData) {
        ObservationPlan existingPlan = getPlanById(id);

        if (updatedData.getTargetName() != null) existingPlan.setTargetName(updatedData.getTargetName());
        if (updatedData.getPlannedTimestamp() != null) existingPlan.setPlannedTimestamp(updatedData.getPlannedTimestamp());
        if (updatedData.getNotifyInAdvance() != null) existingPlan.setNotifyInAdvance(updatedData.getNotifyInAdvance());
        if (updatedData.getStatus() != null) existingPlan.setStatus(updatedData.getStatus());

        return planRepository.save(existingPlan);
    }

    @Transactional
    public void deletePlan(UUID id) {
        planRepository.deleteById(id);
    }
}