package com.kosmoshub.service;

import com.kosmoshub.domain.DiyProject;
import com.kosmoshub.repository.DiyProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                .orElseThrow(() -> new RuntimeException("Projeto DIY não encontrado com o ID: " + id));
    }

    public List<DiyProject> getPublicFinishedProjects() {
        return diyProjectRepository.findByIsPublicTrueAndIsFinishedTrue();
    }

    @Transactional
    public DiyProject updateProject(UUID id, DiyProject updatedData) {
        DiyProject existingProject = getProjectById(id);

        if (updatedData.getContent() != null && !updatedData.getContent().equals(existingProject.getContent())) {
            existingProject.setPreviousContent(existingProject.getContent());
            existingProject.setContent(updatedData.getContent());
        }

        if (updatedData.getTitle() != null) {
            existingProject.setTitle(updatedData.getTitle());
        }

        if (updatedData.getIsPublic() != null) {
            existingProject.setIsPublic(updatedData.getIsPublic());
        }

        if (updatedData.getIsFinished() != null) {
            existingProject.setIsFinished(updatedData.getIsFinished());
        }

        return diyProjectRepository.save(existingProject);
    }

    @Transactional
    public void deleteProject(UUID id) {
        diyProjectRepository.deleteById(id);
    }
}