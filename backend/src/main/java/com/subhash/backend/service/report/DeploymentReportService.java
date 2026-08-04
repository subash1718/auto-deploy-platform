package com.subhash.backend.service.report;

import com.subhash.backend.model.infrastructure.InfrastructureRecommendation;
import com.subhash.backend.model.planning.DeploymentPlan;
import com.subhash.backend.model.technology.TechnologyStack;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DeploymentReportService {

    public void generateReport(
            String applicationRoot,
            TechnologyStack stack,
            InfrastructureRecommendation recommendation,
            DeploymentPlan plan) {

        File report = new File(applicationRoot, "deployment-report.txt");

        try (FileWriter writer = new FileWriter(report)) {

            writer.write("====================================================\n");
            writer.write("           INTELLIGENT DEPLOYMENT REPORT            \n");
            writer.write("====================================================\n\n");

            writer.write("Timestamp      : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("Repository     : " + new File(applicationRoot).getName() + "\n\n");

            writer.write("--- Technology Stack ---\n");
            writer.write("Language       : " + (stack.getLanguage() != null ? stack.getLanguage() : "Unknown") + "\n");
            writer.write("Framework      : " + (stack.getFramework() != null ? stack.getFramework() : "Unknown") + "\n");
            writer.write("Build Tool     : " + (stack.getBuildTool() != null ? stack.getBuildTool() : "Unknown") + "\n\n");

            writer.write("--- Infrastructure Recommendation ---\n");
            writer.write("Cloud Provider : " + recommendation.getCloudProvider() + "\n");
            writer.write("Compute        : " + recommendation.getComputeService() + "\n");
            writer.write("Instance Type  : " + recommendation.getInstanceType() + "\n");
            writer.write("CPU            : " + recommendation.getCpu() + " vCPU\n");
            writer.write("Memory         : " + recommendation.getMemoryGb() + " GB\n");
            writer.write("Storage        : " + recommendation.getStorageGb() + " GB\n");
            writer.write("VPC            : " + (recommendation.isVpc() ? "Enabled" : "Disabled") + "\n");
            writer.write("Load Balancer  : " + (recommendation.isLoadBalancer() ? "Enabled" : "Disabled") + "\n");
            writer.write("Auto Scaling   : " + (recommendation.isAutoScaling() ? "Enabled" : "Disabled") + "\n\n");

            writer.write("--- Deployment Summary ---\n");
            writer.write("Build Command  : " + plan.getBuildCommand() + "\n");
            writer.write("Generate Docker: " + (plan.isGenerateDockerfile() ? "YES" : "NO") + "\n");
            writer.write("Build Docker   : " + (plan.isBuildDockerImage() ? "YES" : "NO") + "\n");
            writer.write("Generate Jenkins: " + (plan.isGenerateJenkinsfile() ? "YES" : "NO") + "\n");
            writer.write("Generate Terraform: " + (plan.isGenerateTerraform() ? "YES" : "NO") + "\n");
            writer.write("Status         : COMPLETED SUCCESSFULLY\n");

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate deployment report", e);
        }
    }
}
