package com.subhash.backend.model.technology;

public class TechnologyStack {

    private String language;
    private String framework;
    private String buildTool;

    private boolean dockerized;
    private boolean jenkinsPresent;
    private boolean terraformPresent;
    private boolean kubernetesPresent;

    public TechnologyStack() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getBuildTool() {
        return buildTool;
    }

    public void setBuildTool(String buildTool) {
        this.buildTool = buildTool;
    }

    public boolean isDockerized() {
        return dockerized;
    }

    public void setDockerized(boolean dockerized) {
        this.dockerized = dockerized;
    }

    public boolean isJenkinsPresent() {
        return jenkinsPresent;
    }

    public void setJenkinsPresent(boolean jenkinsPresent) {
        this.jenkinsPresent = jenkinsPresent;
    }

    public boolean isTerraformPresent() {
        return terraformPresent;
    }

    public void setTerraformPresent(boolean terraformPresent) {
        this.terraformPresent = terraformPresent;
    }

    public boolean isKubernetesPresent() {
        return kubernetesPresent;
    }

    public void setKubernetesPresent(boolean kubernetesPresent) {
        this.kubernetesPresent = kubernetesPresent;
    }
}
