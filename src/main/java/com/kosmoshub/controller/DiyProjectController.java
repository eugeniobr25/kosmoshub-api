package com.kosmoshub.controller;

import com.kosmoshub.domain.DiyProject;
import com.kosmoshub.dto.DiyProjectResponseDTO;
import com.kosmoshub.service.DiyProjectService;
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
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class DiyProjectController {

    private final DiyProjectService projectService;
    @PostMapping
    public ResponseEntity<DiyProjectResponseDTO> createProject(@Valid @RequestBody DiyProject project) {
        DiyProject createdProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DiyProjectResponseDTO.fromEntity(createdProject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiyProjectResponseDTO> getProjectById(@PathVariable UUID id) {
        DiyProject project = projectService.getProjectById(id);
        return ResponseEntity.ok(DiyProjectResponseDTO.fromEntity(project));
    }

    @GetMapping("/public")
    public ResponseEntity<org.springframework.data.domain.Page<DiyProjectResponseDTO>> getPublicProjects(
            @org.springframework.data.web.PageableDefault(size = 10) org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Page<DiyProject> projectsPage = projectService.getPublicFinishedProjects(pageable);
        return ResponseEntity.ok(projectsPage.map(DiyProjectResponseDTO::fromEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiyProject> updateProject(@PathVariable UUID id, @Valid @RequestBody DiyProject project) {
        return ResponseEntity.ok(projectService.updateProject(id, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}