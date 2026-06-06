package com.kosmoshub.controller;

import com.kosmoshub.domain.DiyProject;
import com.kosmoshub.service.DiyProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
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
    public ResponseEntity<DiyProject> createProject(@Valid @RequestBody DiyProject project) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(project));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiyProject> getProjectById(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/public")
    public ResponseEntity<Page<DiyProject>> getPublicProjects(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(projectService.getPublicFinishedProjects((SpringDataWebProperties.Pageable) pageable));
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