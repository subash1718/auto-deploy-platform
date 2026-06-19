package com.subhash.backend.service.planner;

import com.subhash.backend.model.planning.DeploymentPlan;
import com.subhash.backend.model.technology.TechnologyStack;
import org.springframework.stereotype.Service;

@Service
public class DeploymentPlannerService {

    public DeploymentPlan createPlan(TechnologyStack stack) {

        DeploymentPlan plan = new DeploymentPlan();

        // Java Spring Boot Project
        if ("Java".equalsIgnoreCase(stack.getLanguage())) {

            plan.setApplicationType("Spring Boot");

            plan.setBuildCommand("mvn clean package");

            plan.setGenerateDockerfile(!stack.isDockerized());

            plan.setBuildDockerImage(true);

            plan.setGenerateJenkinsfile(!stack.isJenkinsPresent());

            plan.setGenerateTerraform(!stack.isTerraformPresent());

            plan.setDeployToAws(true);
        }

        return plan;
    }
}
