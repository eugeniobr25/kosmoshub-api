package com.kosmoshub.repository;

import com.kosmoshub.domain.DiyProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiyProjectRepository extends JpaRepository<DiyProject, Long> {

    List<DiyProject> findByUserId(Long userId);

    List<DiyProject> findByIsPublicTrueAndIsFinishedTrue();
}