package com.subhash.backend.controller;

import com.subhash.backend.model.AnalysisRequest;
import com.subhash.backend.model.AnalysisResponse;
import com.subhash.backend.service.orchestrator.DeploymentOrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AnalyzeController {

    private static final Logger logger = LoggerFactory.getLogger(AnalyzeController.class);
    private final DeploymentOrchestratorService deploymentOrchestratorService;

    public AnalyzeController(DeploymentOrchestratorService deploymentOrchestratorService) {
        this.deploymentOrchestratorService = deploymentOrchestratorService;
    }

    @PostMapping("/analyze")
    public AnalysisResponse analyze(@RequestBody AnalysisRequest request) {
        logger.info("Received repository analysis request for GitHub URL: {}", request != null ? request.getGithubUrl() : null);

        if (request == null || request.getGithubUrl() == null || request.getGithubUrl().isBlank()) {
            throw new IllegalArgumentException("GitHub Repository URL is required.");
        }

        return deploymentOrchestratorService.analyzeProject(request);
    }

    @PostMapping(
            value = "/analyze-zip",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public AnalysisResponse analyzeZip(
            @RequestParam("file") MultipartFile file,
            @RequestParam("environment") String environment,
            @RequestParam("expectedUsers") int expectedUsers,
            @RequestParam("highAvailability") boolean highAvailability
    ) {
        logger.info("Received ZIP analysis request for file: {}, env: {}, users: {}",
                file != null ? file.getOriginalFilename() : "null", environment, expectedUsers);

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded ZIP file must not be empty.");
        }

        return deploymentOrchestratorService.analyzeZipProject(
                file,
                environment,
                expectedUsers,
                highAvailability
        );
    }
}