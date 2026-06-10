package com.kosmoshub.service;

import com.kosmoshub.domain.DiyProject;
import com.kosmoshub.exception.ResourceNotFoundException;
import com.kosmoshub.repository.DiyProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiyProjectService {

    private final DiyProjectRepository diyProjectRepository;

    @Transactional
    public DiyProject createProject(DiyProject project) {
        return diyProjectRepository.save(project);
    }

    public DiyProject getProjectById(UUID id) {
        return diyProjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto DIY não encontrado com o ID: " + id));
    }

    public Page<DiyProject> getPublicFinishedProjects(Pageable pageable) {
        return diyProjectRepository.findByIsPublicTrueAndIsFinishedTrue(pageable);
    }

    @Transactional
    public DiyProject updateProject(UUID id, DiyProject updatedData) {
        DiyProject existingProject = getProjectById(id);

        if (updatedData.getTitle() != null) existingProject.setTitle(updatedData.getTitle());
        if (updatedData.getContent() != null) existingProject.setContent(updatedData.getContent());
        if (updatedData.getIsPublic() != null) existingProject.setIsPublic(updatedData.getIsPublic());
        if (updatedData.getIsFinished() != null) existingProject.setIsFinished(updatedData.getIsFinished());

        return diyProjectRepository.save(existingProject);
    }

    @Transactional
    public void deleteProject(UUID id) {
        diyProjectRepository.deleteById(id);
    }
}