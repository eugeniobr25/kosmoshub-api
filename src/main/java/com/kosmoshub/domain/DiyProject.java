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
@Table(name = "diy_projects", indexes = {
        @Index(name = "idx_diy_user", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiyProject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "average_rating")
    private Double averageRating = 0.0;

    @Column(name = "total_votes")
    private Integer totalVotes = 0;

    @Column(name = "is_public")
    private Boolean isPublic = true;

    @Column(name = "is_finished")
    private Boolean isFinished = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "previous_content", columnDefinition = "TEXT")
    private String previousContent;

    @PreUpdate
    public void onPreUpdate() {
        this.previousContent = this.content;
    }
}