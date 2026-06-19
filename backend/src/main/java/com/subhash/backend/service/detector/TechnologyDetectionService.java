package com.subhash.backend.service.detector;

import com.subhash.backend.model.metadata.RepositoryMetadata;
import com.subhash.backend.model.technology.TechnologyStack;
import org.springframework.stereotype.Service;

@Service
public class TechnologyDetectionService {

    public TechnologyStack detectTechnologies(RepositoryMetadata metadata) {

        TechnologyStack stack = new TechnologyStack();

        // Detect Build Tool
        if (!metadata.getPomFiles().isEmpty()) {
            stack.setBuildTool("Maven");
            stack.setLanguage("Java");
            stack.setFramework("Spring Boot");
        }

        // Detect Docker
        if (!metadata.getDockerFiles().isEmpty()) {
            stack.setDockerized(true);
        }

        // Detect Jenkins
        if (!metadata.getJenkinsFiles().isEmpty()) {
            stack.setJenkinsPresent(true);
        }

        // Detect Terraform
        if (!metadata.getTerraformFiles().isEmpty()) {
            stack.setTerraformPresent(true);
        }

        // Detect Kubernetes
        if (!metadata.getKubernetesFiles().isEmpty()) {
            stack.setKubernetesPresent(true);
        }

        return stack;
    }
}
