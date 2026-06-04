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
@Table(name = "observation_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationPost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private ObservationPlan plan;

    @Column(name = "target_name", nullable = false)
    private String targetName;

    @Column(name = "equipment_metadata", columnDefinition = "jsonb")
    private String equipmentMetadata;

    @Column(name = "average_rating")
    private Double averageRating = 0.0;

    @Column(name = "total_votes")
    private Integer totalVotes = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "previous_equipment_metadata", columnDefinition = "jsonb")
    private String previousEquipmentMetadata;
}