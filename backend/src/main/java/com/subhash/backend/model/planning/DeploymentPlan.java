package com.subhash.backend.model.planning;

public class DeploymentPlan {

    private String applicationType;
    private String buildCommand;

    private boolean generateDockerfile;
    private boolean buildDockerImage;

    private boolean generateJenkinsfile;
    private boolean generateTerraform;

    private boolean deployToAws;

    public DeploymentPlan() {
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getBuildCommand() {
        return buildCommand;
    }

    public void setBuildCommand(String buildCommand) {
        this.buildCommand = buildCommand;
    }

    public boolean isGenerateDockerfile() {
        return generateDockerfile;
    }

    public void setGenerateDockerfile(boolean generateDockerfile) {
        this.generateDockerfile = generateDockerfile;
    }

    public boolean isBuildDockerImage() {
        return buildDockerImage;
    }

    public void setBuildDockerImage(boolean buildDockerImage) {
        this.buildDockerImage = buildDockerImage;
    }

    public boolean isGenerateJenkinsfile() {
        return generateJenkinsfile;
    }

    public void setGenerateJenkinsfile(boolean generateJenkinsfile) {
        this.generateJenkinsfile = generateJenkinsfile;
    }

    public boolean isGenerateTerraform() {
        return generateTerraform;
    }

    public void setGenerateTerraform(boolean generateTerraform) {
        this.generateTerraform = generateTerraform;
    }

    public boolean isDeployToAws() {
        return deployToAws;
    }

    public void setDeployToAws(boolean deployToAws) {
        this.deployToAws = deployToAws;
    }
}

