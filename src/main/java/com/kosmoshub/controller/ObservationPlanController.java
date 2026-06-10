package com.kosmoshub.controller;

import com.kosmoshub.domain.ObservationPlan;
import com.kosmoshub.dto.ObservationPlanCreateDTO;
import com.kosmoshub.dto.ObservationPlanResponseDTO;
import com.kosmoshub.service.ObservationPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class ObservationPlanController {

    private final ObservationPlanService planService;

    @PostMapping
    public ResponseEntity<ObservationPlanResponseDTO> createPlan(@Valid @RequestBody ObservationPlanCreateDTO dto) {
        ObservationPlan plan = new ObservationPlan();
        plan.setTargetName(dto.targetName());
        plan.setPlannedTimestamp(dto.plannedTimestamp());
        if (dto.notifyInAdvance() != null) plan.setNotifyInAdvance(dto.notifyInAdvance());

        ObservationPlan createdPlan = planService.createPlan(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(ObservationPlanResponseDTO.fromEntity(createdPlan));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservationPlanResponseDTO> getPlanById(@PathVariable UUID id) {
        ObservationPlan plan = planService.getPlanById(id);
        return ResponseEntity.ok(ObservationPlanResponseDTO.fromEntity(plan));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ObservationPlanResponseDTO>> getPlansByUser(
            @PathVariable UUID userId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ObservationPlan> plansPage = planService.getPlansByUser(userId, pageable);
        return ResponseEntity.ok(plansPage.map(ObservationPlanResponseDTO::fromEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObservationPlanResponseDTO> updatePlan(@PathVariable UUID id, @Valid @RequestBody ObservationPlanCreateDTO dto) {
        ObservationPlan updateData = new ObservationPlan();
        updateData.setTargetName(dto.targetName());
        updateData.setPlannedTimestamp(dto.plannedTimestamp());
        if (dto.notifyInAdvance() != null) updateData.setNotifyInAdvance(dto.notifyInAdvance());

        ObservationPlan updatedPlan = planService.updatePlan(id, updateData);
        return ResponseEntity.ok(ObservationPlanResponseDTO.fromEntity(updatedPlan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable UUID id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}