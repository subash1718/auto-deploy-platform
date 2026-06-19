package com.subhash.backend.service.orchestrator;

import com.subhash.backend.model.AnalysisRequest;
import com.subhash.backend.model.AnalysisResponse;
import com.subhash.backend.model.metadata.RepositoryMetadata;
import com.subhash.backend.model.technology.TechnologyStack;
import com.subhash.backend.service.detector.TechnologyDetectionService;
import com.subhash.backend.service.git.GitCloneService;
import com.subhash.backend.service.scanner.RepositoryScannerService;
import org.springframework.stereotype.Service;

@Service
public class DeploymentOrchestratorService {

    private final GitCloneService gitCloneService;
    private final RepositoryScannerService repositoryScannerService;
    private final TechnologyDetectionService technologyDetectionService;

    public DeploymentOrchestratorService(
            GitCloneService gitCloneService,
            RepositoryScannerService repositoryScannerService,
            TechnologyDetectionService technologyDetectionService) {

        this.gitCloneService = gitCloneService;
        this.repositoryScannerService = repositoryScannerService;
        this.technologyDetectionService = technologyDetectionService;
    }

    public AnalysisResponse analyzeProject(AnalysisRequest request) {

        // Step 1
        String projectPath = gitCloneService.cloneRepository(request.getGithubUrl());

        System.out.println("Project cloned to: " + projectPath);

        // Step 2
        RepositoryMetadata metadata =
                repositoryScannerService.scanRepository(projectPath);

        // Step 3
        System.out.println("\n========== Repository Analysis ==========");
        System.out.println("Project Root: " + metadata.getProjectRoot());

        System.out.println("\nPOM Files:");
        metadata.getPomFiles().forEach(System.out::println);

        System.out.println("\nPackage.json Files:");
        metadata.getPackageJsonFiles().forEach(System.out::println);

        System.out.println("\nDockerfiles:");
        metadata.getDockerFiles().forEach(System.out::println);

        System.out.println("\nJenkinsfiles:");
        metadata.getJenkinsFiles().forEach(System.out::println);

        System.out.println("\nTerraform Files:");
        metadata.getTerraformFiles().forEach(System.out::println);

        System.out.println("\nKubernetes Files:");
        metadata.getKubernetesFiles().forEach(System.out::println);

        // Step 4
        TechnologyStack stack =
                technologyDetectionService.detectTechnologies(metadata);

        // Step 5
        System.out.println("\n========== Technology Report ==========");

        System.out.println("Language      : " + stack.getLanguage());
        System.out.println("Framework     : " + stack.getFramework());
        System.out.println("Build Tool    : " + stack.getBuildTool());

        System.out.println("Docker        : " +
                (stack.isDockerized() ? "Present" : "Missing"));

        System.out.println("Jenkins       : " +
                (stack.isJenkinsPresent() ? "Present" : "Missing"));

        System.out.println("Terraform     : " +
                (stack.isTerraformPresent() ? "Present" : "Missing"));

        System.out.println("Kubernetes    : " +
                (stack.isKubernetesPresent() ? "Present" : "Missing"));

        return new AnalysisResponse("Repository analyzed successfully");
    }
}
