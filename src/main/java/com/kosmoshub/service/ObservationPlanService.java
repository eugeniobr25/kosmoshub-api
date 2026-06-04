package com.kosmoshub.service;

import com.kosmoshub.domain.ObservationPlan;
import com.kosmoshub.repository.ObservationPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObservationPlanService {

    private final ObservationPlanRepository planRepository;

    @Transactional
    public ObservationPlan createPlan(ObservationPlan plan) {
        return planRepository.save(plan);
    }

    public ObservationPlan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano de observação não encontrado com o ID: " + id));
    }

    public List<ObservationPlan> getPlansByUserId(Long userId) {
        return planRepository.findByUserId(userId);
    }

    @Transactional
    public ObservationPlan updatePlan(Long id, ObservationPlan updatedData) {
        ObservationPlan existingPlan = getPlanById(id);

        if (updatedData.getTargetName() != null) existingPlan.setTargetName(updatedData.getTargetName());
        if (updatedData.getPlannedTimestamp() != null) existingPlan.setPlannedTimestamp(updatedData.getPlannedTimestamp());
        if (updatedData.getNotifyInAdvance() != null) existingPlan.setNotifyInAdvance(updatedData.getNotifyInAdvance());
        if (updatedData.getStatus() != null) existingPlan.setStatus(updatedData.getStatus());

        return planRepository.save(existingPlan);
    }

    @Transactional
    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }
}