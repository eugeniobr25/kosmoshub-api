package com.kosmoshub.repository;

import com.kosmoshub.domain.ObservationPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ObservationPostRepository extends JpaRepository<ObservationPost, UUID> {

    List<ObservationPost> findByUserId(UUID userId);
}