package com.subhash.backend.model.metadata;

import java.util.ArrayList;
import java.util.List;

public class RepositoryMetadata {

    private String projectRoot;

    private List<String> pomFiles = new ArrayList<>();

    private List<String> packageJsonFiles = new ArrayList<>();

    private List<String> dockerFiles = new ArrayList<>();

    private List<String> jenkinsFiles = new ArrayList<>();

    private List<String> terraformFiles = new ArrayList<>();

    private List<String> kubernetesFiles = new ArrayList<>();

    public String getProjectRoot() {
        return projectRoot;
    }

    public void setProjectRoot(String projectRoot) {
        this.projectRoot = projectRoot;
    }

    public List<String> getPomFiles() {
        return pomFiles;
    }

    public List<String> getPackageJsonFiles() {
        return packageJsonFiles;
    }

    public List<String> getDockerFiles() {
        return dockerFiles;
    }

    public List<String> getJenkinsFiles() {
        return jenkinsFiles;
    }

    public List<String> getTerraformFiles() {
        return terraformFiles;
    }

    public List<String> getKubernetesFiles() {
        return kubernetesFiles;
    }
}
