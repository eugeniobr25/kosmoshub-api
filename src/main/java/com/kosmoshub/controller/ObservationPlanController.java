package com.kosmoshub.controller;

import com.kosmoshub.domain.ObservationPlan;
import com.kosmoshub.service.ObservationPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class ObservationPlanController {

    private final ObservationPlanService planService;

    @PostMapping
    public ResponseEntity<ObservationPlan> createPlan(@RequestBody ObservationPlan plan) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.createPlan(plan));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservationPlan> getPlanById(@PathVariable UUID id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ObservationPlan>> getPlansByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(planService.getPlansByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObservationPlan> updatePlan(@PathVariable UUID id, @RequestBody ObservationPlan plan) {
        return ResponseEntity.ok(planService.updatePlan(id, plan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable UUID id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}