package com.subhash.backend.controller;

import com.subhash.backend.model.AnalysisRequest;
import com.subhash.backend.model.AnalysisResponse;
import com.subhash.backend.service.orchestrator.DeploymentOrchestratorService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalyzeController {

    private final DeploymentOrchestratorService deploymentOrchestratorService;

    public AnalyzeController(DeploymentOrchestratorService deploymentOrchestratorService) {
        this.deploymentOrchestratorService = deploymentOrchestratorService;
    }

    @PostMapping("/analyze")
    public AnalysisResponse analyze(@RequestBody AnalysisRequest request) {

        return deploymentOrchestratorService.analyzeProject(request);

    }
}
