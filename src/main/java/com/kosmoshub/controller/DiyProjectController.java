package com.kosmoshub.controller;

import com.kosmoshub.domain.DiyProject;
import com.kosmoshub.service.DiyProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class DiyProjectController {

    private final DiyProjectService projectService;

    @PostMapping
    public ResponseEntity<DiyProject> createProject(@RequestBody DiyProject project) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(project));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiyProject> getProjectById(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/public")
    public ResponseEntity<List<DiyProject>> getPublicFinishedProjects() {
        return ResponseEntity.ok(projectService.getPublicFinishedProjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiyProject> updateProject(@PathVariable UUID id, @RequestBody DiyProject project) {
        return ResponseEntity.ok(projectService.updateProject(id, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}