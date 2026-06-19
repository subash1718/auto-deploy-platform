package com.subhash.backend.service.orchestrator;

import com.subhash.backend.model.AnalysisRequest;
import com.subhash.backend.model.AnalysisResponse;
import com.subhash.backend.model.metadata.RepositoryMetadata;
import com.subhash.backend.model.planning.DeploymentPlan;
import com.subhash.backend.model.technology.TechnologyStack;
import com.subhash.backend.service.deployment.ContainerBuildService;
import com.subhash.backend.service.detector.TechnologyDetectionService;
import com.subhash.backend.service.generator.DockerfileGeneratorService;
import com.subhash.backend.service.git.GitCloneService;
import com.subhash.backend.service.planner.DeploymentPlannerService;
import com.subhash.backend.service.scanner.RepositoryScannerService;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DeploymentOrchestratorService {

    private final GitCloneService gitCloneService;
    private final RepositoryScannerService repositoryScannerService;
    private final TechnologyDetectionService technologyDetectionService;
    private final DeploymentPlannerService deploymentPlannerService;
    private final DockerfileGeneratorService dockerfileGeneratorService;
    private final ContainerBuildService containerBuildService;

    public DeploymentOrchestratorService(
            GitCloneService gitCloneService,
            RepositoryScannerService repositoryScannerService,
            TechnologyDetectionService technologyDetectionService,
            DeploymentPlannerService deploymentPlannerService,
            DockerfileGeneratorService dockerfileGeneratorService,
            ContainerBuildService containerBuildService) {

        this.gitCloneService = gitCloneService;
        this.repositoryScannerService = repositoryScannerService;
        this.technologyDetectionService = technologyDetectionService;
        this.deploymentPlannerService = deploymentPlannerService;
        this.dockerfileGeneratorService = dockerfileGeneratorService;
        this.containerBuildService = containerBuildService;
    }

    public AnalysisResponse analyzeProject(AnalysisRequest request) {

        // Step 1 - Clone Repository
        String projectPath = gitCloneService.cloneRepository(request.getGithubUrl());

        System.out.println("Project cloned to: " + projectPath);

        // Step 2 - Scan Repository
        RepositoryMetadata metadata =
                repositoryScannerService.scanRepository(projectPath);

        // Step 3 - Repository Analysis
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

        // Step 4 - Detect Technologies
        TechnologyStack stack =
                technologyDetectionService.detectTechnologies(metadata);

        // Step 5 - Technology Report
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

        // Step 6 - Create Deployment Plan
        DeploymentPlan plan =
                deploymentPlannerService.createPlan(stack);

        // Step 7 - Deployment Plan
        System.out.println("\n========== Deployment Plan ==========");

        System.out.println("Application Type      : " + plan.getApplicationType());
        System.out.println("Build Command         : " + plan.getBuildCommand());

        System.out.println("Generate Dockerfile   : " +
                (plan.isGenerateDockerfile() ? "YES" : "NO"));

        System.out.println("Build Docker Image    : " +
                (plan.isBuildDockerImage() ? "YES" : "NO"));

        System.out.println("Generate Jenkinsfile  : " +
                (plan.isGenerateJenkinsfile() ? "YES" : "NO"));

        System.out.println("Generate Terraform    : " +
                (plan.isGenerateTerraform() ? "YES" : "NO"));

        System.out.println("Deploy to AWS         : " +
                (plan.isDeployToAws() ? "YES" : "NO"));

        // Step 8 - Generate Dockerfile
        if (plan.isGenerateDockerfile()) {

            dockerfileGeneratorService.generateDockerfile(projectPath);

        } else {

            System.out.println("\nDockerfile already exists. Skipping generation.");

        }

        // Step 9 - Find Application Root and Build Docker Image
        if (plan.isBuildDockerImage()) {

            String applicationRoot = projectPath;

            if (!metadata.getPomFiles().isEmpty()) {
                applicationRoot =
                        new File(metadata.getPomFiles().get(0)).getParent();
            }

            System.out.println("\nApplication Root: " + applicationRoot);

            System.out.println("\n========== Building Docker Image ==========");

            containerBuildService.buildDockerImage(applicationRoot);

        } else {

            System.out.println("\nDocker image build skipped.");

        }

        return new AnalysisResponse(
                "Repository analyzed, deployment plan created, Dockerfile generated and Docker image built successfully.");
    }
}
