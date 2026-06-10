package com.kosmoshub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "observation_plans", indexes = {
        @Index(name = "idx_plan_user", columnList = "user_id"),
        @Index(name = "idx_plan_date", columnList = "planned_timestamp")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "target_name", nullable = false)
    private String targetName;

    @Column(name = "planned_timestamp", nullable = false)
    private LocalDateTime plannedTimestamp;

    @Column(name = "notify_in_advance")
    private Boolean notifyInAdvance = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanStatus status = PlanStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum PlanStatus {
        PENDING, COMPLETED, CANCELED
    }
}