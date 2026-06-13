package com.kosmoshub.controller;

import com.kosmoshub.domain.DiyProject;
import com.kosmoshub.dto.DiyProjectCreateDTO;
import com.kosmoshub.dto.DiyProjectResponseDTO;
import com.kosmoshub.repository.DiyProjectRepository;
import com.kosmoshub.service.DiyProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/diy-projects") // Rota corrigida para alinhar com o Frontend
@RequiredArgsConstructor
public class DiyProjectController {

    private final DiyProjectService projectService;
    private final DiyProjectRepository diyProjectRepository; // Injetado para o feed

    @PostMapping
    public ResponseEntity<DiyProjectResponseDTO> createProject(@Valid @RequestBody DiyProjectCreateDTO dto) {
        DiyProject project = new DiyProject();
        project.setTitle(dto.title());
        project.setContent(dto.content());
        if (dto.isPublic() != null) project.setIsPublic(dto.isPublic());

        DiyProject createdProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(DiyProjectResponseDTO.fromEntity(createdProject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiyProjectResponseDTO> getProjectById(@PathVariable UUID id) {
        DiyProject project = projectService.getProjectById(id);
        return ResponseEntity.ok(DiyProjectResponseDTO.fromEntity(project));
    }

    // A NOSSA ROTA CORRIGIDA PARA O DASHBOARD
    @GetMapping
    public ResponseEntity<Page<DiyProjectResponseDTO>> getLatestProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DiyProject> projects = diyProjectRepository.findAll(pageable);

        return ResponseEntity.ok(projects.map(DiyProjectResponseDTO::fromEntity));
    }

    @GetMapping("/public")
    public ResponseEntity<Page<DiyProjectResponseDTO>> getPublicProjects(@PageableDefault(size = 10) Pageable pageable) {
        Page<DiyProject> projectsPage = projectService.getPublicFinishedProjects(pageable);
        return ResponseEntity.ok(projectsPage.map(DiyProjectResponseDTO::fromEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiyProjectResponseDTO> updateProject(@PathVariable UUID id, @Valid @RequestBody DiyProjectCreateDTO dto) {
        DiyProject updateData = new DiyProject();
        updateData.setTitle(dto.title());
        updateData.setContent(dto.content());
        if (dto.isPublic() != null) updateData.setIsPublic(dto.isPublic());

        DiyProject updatedProject = projectService.updateProject(id, updateData);
        return ResponseEntity.ok(DiyProjectResponseDTO.fromEntity(updatedProject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}