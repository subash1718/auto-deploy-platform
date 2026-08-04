package com.subhash.backend.service.orchestrator;

import com.subhash.backend.model.AnalysisRequest;
import com.subhash.backend.model.AnalysisResponse;
import com.subhash.backend.model.infrastructure.InfrastructureRecommendation;
import com.subhash.backend.model.metadata.RepositoryMetadata;
import com.subhash.backend.model.planning.DeploymentPlan;
import com.subhash.backend.model.technology.TechnologyStack;
import com.subhash.backend.service.deployment.ContainerBuildService;
import com.subhash.backend.service.deployment.ProjectBuildService;
import com.subhash.backend.service.detector.TechnologyDetectionService;
import com.subhash.backend.service.generator.DockerfileGeneratorService;
import com.subhash.backend.service.generator.JenkinsfileGeneratorService;
import com.subhash.backend.service.generator.TerraformGeneratorService;
import com.subhash.backend.service.git.GitCloneService;
import com.subhash.backend.service.planner.DeploymentPlannerService;
import com.subhash.backend.service.recommendation.InfrastructureRecommendationService;
import com.subhash.backend.service.report.DeploymentReportService;
import com.subhash.backend.service.report.RepositoryReportService;
import com.subhash.backend.service.scanner.RepositoryScannerService;
import com.subhash.backend.service.zip.ZipExtractionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class DeploymentOrchestratorService {

    private final GitCloneService gitCloneService;
    private final ZipExtractionService zipExtractionService;
    private final RepositoryScannerService repositoryScannerService;
    private final TechnologyDetectionService technologyDetectionService;
    private final DeploymentPlannerService deploymentPlannerService;
    private final RepositoryReportService repositoryReportService;
    private final ProjectBuildService projectBuildService;
    private final DockerfileGeneratorService dockerfileGeneratorService;
    private final JenkinsfileGeneratorService jenkinsfileGeneratorService;
    private final TerraformGeneratorService terraformGeneratorService;
    private final ContainerBuildService containerBuildService;
    private final InfrastructureRecommendationService infrastructureRecommendationService;
    private final DeploymentReportService deploymentReportService;

    public DeploymentOrchestratorService(
            GitCloneService gitCloneService,
            ZipExtractionService zipExtractionService,
            RepositoryScannerService repositoryScannerService,
            TechnologyDetectionService technologyDetectionService,
            DeploymentPlannerService deploymentPlannerService,
            RepositoryReportService repositoryReportService,
            ProjectBuildService projectBuildService,
            DockerfileGeneratorService dockerfileGeneratorService,
            JenkinsfileGeneratorService jenkinsfileGeneratorService,
            TerraformGeneratorService terraformGeneratorService,
            ContainerBuildService containerBuildService,
            InfrastructureRecommendationService infrastructureRecommendationService,
            DeploymentReportService deploymentReportService) {

        this.gitCloneService = gitCloneService;
        this.zipExtractionService = zipExtractionService;
        this.repositoryScannerService = repositoryScannerService;
        this.technologyDetectionService = technologyDetectionService;
        this.deploymentPlannerService = deploymentPlannerService;
        this.repositoryReportService = repositoryReportService;
        this.projectBuildService = projectBuildService;
        this.dockerfileGeneratorService = dockerfileGeneratorService;
        this.jenkinsfileGeneratorService = jenkinsfileGeneratorService;
        this.terraformGeneratorService = terraformGeneratorService;
        this.containerBuildService = containerBuildService;
        this.infrastructureRecommendationService = infrastructureRecommendationService;
        this.deploymentReportService = deploymentReportService;
    }

    public AnalysisResponse analyzeProject(AnalysisRequest request) {
        String projectPath = gitCloneService.cloneRepository(request.getGithubUrl());
        System.out.println("Project cloned to: " + projectPath);
        return executePipeline(projectPath, request);
    }

    public AnalysisResponse analyzeZipProject(
            MultipartFile file,
            String environment,
            int expectedUsers,
            boolean highAvailability
    ) {
        try {
            File uploadsDir = new File("uploads");
            if (!uploadsDir.exists()) {
                uploadsDir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                originalFilename = "uploaded-app.zip";
            }

            File tempZip = new File(uploadsDir, originalFilename);
            try (FileOutputStream fos = new FileOutputStream(tempZip)) {
                fos.write(file.getBytes());
            }

            String projectPath = zipExtractionService.extractZip(tempZip);
            tempZip.delete();

            AnalysisRequest request = new AnalysisRequest();
            request.setEnvironment(environment);
            request.setExpectedUsers(expectedUsers);
            request.setHighAvailability(highAvailability);

            return executePipeline(projectPath, request);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process ZIP application upload", e);
        }
    }

    private AnalysisResponse executePipeline(String projectPath, AnalysisRequest request) {

        // Step 2 - Scan Repository
        RepositoryMetadata metadata = repositoryScannerService.scanRepository(projectPath);

        // Step 3 - Repository Report
        repositoryReportService.printRepositoryReport(metadata);

        // Step 4 - Detect Technologies
        TechnologyStack stack = technologyDetectionService.detectTechnologies(metadata);

        // Step 5 - Technology Report
        repositoryReportService.printTechnologyReport(stack);

        // Step 6 - Infrastructure Recommendation
        InfrastructureRecommendation recommendation =
                infrastructureRecommendationService.recommend(stack, request);

        System.out.println("\n========== Infrastructure Recommendation ==========");
        System.out.println("Cloud Provider     : " + recommendation.getCloudProvider());
        System.out.println("Compute Service    : " + recommendation.getComputeService());
        System.out.println("Instance Type      : " + recommendation.getInstanceType());
        System.out.println("CPU                : " + recommendation.getCpu() + " vCPU");
        System.out.println("Memory             : " + recommendation.getMemoryGb() + " GB");
        System.out.println("Storage            : " + recommendation.getStorageGb() + " GB");
        System.out.println("VPC                : " + (recommendation.isVpc() ? "YES" : "NO"));
        System.out.println("Internet Gateway   : " + (recommendation.isInternetGateway() ? "YES" : "NO"));
        System.out.println("Public Subnets     : " + recommendation.getPublicSubnets());
        System.out.println("Private Subnets    : " + recommendation.getPrivateSubnets());
        System.out.println("Load Balancer      : " + (recommendation.isLoadBalancer() ? "YES" : "NO"));
        System.out.println("Auto Scaling       : " + (recommendation.isAutoScaling() ? "YES" : "NO"));
        System.out.println("Minimum Instances  : " + recommendation.getMinInstances());
        System.out.println("Maximum Instances  : " + recommendation.getMaxInstances());

        // Step 7 - Create Deployment Plan
        DeploymentPlan plan = deploymentPlannerService.createPlan(stack);

        // Step 8 - Deployment Plan Report
        repositoryReportService.printDeploymentPlan(plan);

        // Determine Application Root
        String applicationRoot = projectPath;
        if (!metadata.getPomFiles().isEmpty()) {
            applicationRoot = new File(metadata.getPomFiles().get(0)).getParent();
        }
        System.out.println("\nApplication Root: " + applicationRoot);

        // Step 9 - Build Project
        System.out.println("\n========== Building Project ==========");
        projectBuildService.buildProject(applicationRoot, plan.getBuildCommand());

        // Step 10 - Generate Dockerfile
        boolean dockerGenerated = false;
        if (plan.isGenerateDockerfile()) {
            System.out.println("\n========== Generating Dockerfile ==========");
            dockerfileGeneratorService.generateDockerfile(applicationRoot);
            dockerGenerated = true;
        } else {
            System.out.println("\nDockerfile already exists. Skipping generation.");
        }

        // Step 11 - Generate Jenkinsfile
        boolean jenkinsGenerated = false;
        if (plan.isGenerateJenkinsfile()) {
            System.out.println("\n========== Generating Jenkinsfile ==========");
            jenkinsfileGeneratorService.generateJenkinsfile(applicationRoot);
            jenkinsGenerated = true;
        } else {
            System.out.println("\nJenkinsfile already exists. Skipping generation.");
        }

        // Step 12 - Generate Terraform
        boolean terraformGenerated = false;
        if (plan.isGenerateTerraform()) {
            System.out.println("\n========== Generating Terraform ==========");
            terraformGeneratorService.generateTerraform(applicationRoot, recommendation);
            terraformGenerated = true;
        } else {
            System.out.println("\nTerraform generation skipped.");
        }

        // Step 13 - Build Docker Image
        boolean dockerImageBuilt = false;
        if (plan.isBuildDockerImage()) {
            System.out.println("\n========== Building Docker Image ==========");
            try {
                containerBuildService.buildDockerImage(applicationRoot);
                dockerImageBuilt = true;
            } catch (Exception e) {
                System.out.println("Docker build skipped or failed: " + e.getMessage());
            }
        } else {
            System.out.println("\nDocker image build skipped.");
        }

        // Step 14 - Generate Deployment Report
        try {
            deploymentReportService.generateReport(applicationRoot, stack, recommendation, plan);
        } catch (Exception e) {
            System.out.println("Failed to generate deployment report: " + e.getMessage());
        }

        // Step 15 - Prepare Response
        AnalysisResponse response = new AnalysisResponse();
        response.setMessage("Application analyzed and deployment artifacts generated successfully.");
        response.setStatus("SUCCESS");
        response.setRepository(new File(projectPath).getName());

        response.setLanguage(stack.getLanguage() == null ? "Unknown" : stack.getLanguage());
        response.setFramework(stack.getFramework() == null ? "Unknown" : stack.getFramework());
        response.setBuildTool(stack.getBuildTool() == null ? "Unknown" : stack.getBuildTool());

        response.setCloudProvider(recommendation.getCloudProvider());
        response.setComputeService(recommendation.getComputeService());
        response.setInstanceType(recommendation.getInstanceType());

        response.setCpu(recommendation.getCpu());
        response.setMemoryGb(recommendation.getMemoryGb());
        response.setStorageGb(recommendation.getStorageGb());

        response.setLoadBalancer(recommendation.isLoadBalancer());
        response.setAutoScaling(recommendation.isAutoScaling());

        response.setDockerGenerated(dockerGenerated);
        response.setJenkinsGenerated(jenkinsGenerated);
        response.setTerraformGenerated(terraformGenerated);
        response.setDockerImageBuilt(dockerImageBuilt);

        return response;
    }
}
