package com.subhash.backend.service.orchestrator;

import com.subhash.backend.model.AnalysisRequest;
import com.subhash.backend.model.AnalysisResponse;
import com.subhash.backend.model.metadata.RepositoryMetadata;
import com.subhash.backend.service.git.GitCloneService;
import com.subhash.backend.service.scanner.RepositoryScannerService;
import org.springframework.stereotype.Service;

@Service
public class DeploymentOrchestratorService {

    private final GitCloneService gitCloneService;
    private final RepositoryScannerService repositoryScannerService;

    public DeploymentOrchestratorService(
            GitCloneService gitCloneService,
            RepositoryScannerService repositoryScannerService) {

        this.gitCloneService = gitCloneService;
        this.repositoryScannerService = repositoryScannerService;
    }

    public AnalysisResponse analyzeProject(AnalysisRequest request) {

        // Step 1: Clone Repository
        String projectPath = gitCloneService.cloneRepository(request.getGithubUrl());

        System.out.println("Project cloned to: " + projectPath);

        // Step 2: Scan Repository
        RepositoryMetadata metadata =
                repositoryScannerService.scanRepository(projectPath);

        // Step 3: Print Analysis
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

        return new AnalysisResponse("Repository cloned and analyzed successfully");
    }
}
