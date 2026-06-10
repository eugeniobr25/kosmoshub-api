package com.kosmoshub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "observation_posts", indexes = {
        @Index(name = "idx_post_user", columnList = "user_id"),
        @Index(name = "idx_post_plan", columnList = "plan_id")
})
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "equipment_metadata", columnDefinition = "jsonb")
    private String equipmentMetadata;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "previous_equipment_metadata", columnDefinition = "jsonb")
    private String previousEquipmentMetadata;

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

    @PreUpdate
    public void onPreUpdate() {
        this.previousEquipmentMetadata = this.equipmentMetadata;
    }
}