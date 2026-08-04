package com.subhash.backend.service.report;

import com.subhash.backend.model.metadata.RepositoryMetadata;
import com.subhash.backend.model.planning.DeploymentPlan;
import com.subhash.backend.model.technology.TechnologyStack;
import org.springframework.stereotype.Service;

@Service
public class RepositoryReportService {

    public void printRepositoryReport(RepositoryMetadata metadata) {

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
    }

    public void printTechnologyReport(TechnologyStack stack) {

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
    }

    public void printDeploymentPlan(DeploymentPlan plan) {

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
    }
}
