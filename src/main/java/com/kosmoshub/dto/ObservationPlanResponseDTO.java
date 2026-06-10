package com.kosmoshub.dto;

import com.kosmoshub.domain.ObservationPlan;
import java.time.LocalDateTime;
import java.util.UUID;

public record ObservationPlanResponseDTO(
        UUID id,
        UserResponseDTO user,
        String targetName,
        LocalDateTime plannedTimestamp,
        Boolean notifyInAdvance,
        ObservationPlan.PlanStatus status
) {
    public static ObservationPlanResponseDTO fromEntity(ObservationPlan plan) {
        return new ObservationPlanResponseDTO(
                plan.getId(),
                plan.getUser() != null ? UserResponseDTO.fromEntity(plan.getUser()) : null,
                plan.getTargetName(),
                plan.getPlannedTimestamp(),
                plan.getNotifyInAdvance(),
                plan.getStatus()
        );
    }
}