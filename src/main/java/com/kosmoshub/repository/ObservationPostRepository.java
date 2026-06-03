package com.kosmoshub.repository;

import com.kosmoshub.domain.ObservationPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationPostRepository extends JpaRepository<ObservationPost, Long> {

    List<ObservationPost> findByUserId(Long userId);
}