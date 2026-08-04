package com.subhash.backend.model;

public class AnalysisResponse {

    private String message;
    private String repository;
    private String language;
    private String framework;
    private String buildTool;

    private String cloudProvider;
    private String computeService;
    private String instanceType;

    private int cpu;
    private int memoryGb;
    private int storageGb;

    private int memory;
    private int storage;

    private boolean loadBalancer;
    private boolean autoScaling;

    private boolean dockerGenerated;
    private boolean jenkinsGenerated;
    private boolean terraformGenerated;
    private boolean dockerImageBuilt;

    private String status;

    public AnalysisResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
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

    public String getCloudProvider() {
        return cloudProvider;
    }

    public void setCloudProvider(String cloudProvider) {
        this.cloudProvider = cloudProvider;
    }

    public String getComputeService() {
        return computeService;
    }

    public void setComputeService(String computeService) {
        this.computeService = computeService;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public int getMemoryGb() {
        return memoryGb;
    }

    public void setMemoryGb(int memoryGb) {
        this.memoryGb = memoryGb;
        this.memory = memoryGb;
    }

    public int getStorageGb() {
        return storageGb;
    }

    public void setStorageGb(int storageGb) {
        this.storageGb = storageGb;
        this.storage = storageGb;
    }

    public int getMemory() {
        return memory > 0 ? memory : memoryGb;
    }

    public void setMemory(int memory) {
        this.memory = memory;
        this.memoryGb = memory;
    }

    public int getStorage() {
        return storage > 0 ? storage : storageGb;
    }

    public void setStorage(int storage) {
        this.storage = storage;
        this.storageGb = storage;
    }

    public boolean isLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(boolean loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public boolean isAutoScaling() {
        return autoScaling;
    }

    public void setAutoScaling(boolean autoScaling) {
        this.autoScaling = autoScaling;
    }

    public boolean isDockerGenerated() {
        return dockerGenerated;
    }

    public void setDockerGenerated(boolean dockerGenerated) {
        this.dockerGenerated = dockerGenerated;
    }

    public boolean isJenkinsGenerated() {
        return jenkinsGenerated;
    }

    public void setJenkinsGenerated(boolean jenkinsGenerated) {
        this.jenkinsGenerated = jenkinsGenerated;
    }

    public boolean isTerraformGenerated() {
        return terraformGenerated;
    }

    public void setTerraformGenerated(boolean terraformGenerated) {
        this.terraformGenerated = terraformGenerated;
    }

    public boolean isDockerImageBuilt() {
        return dockerImageBuilt;
    }

    public void setDockerImageBuilt(boolean dockerImageBuilt) {
        this.dockerImageBuilt = dockerImageBuilt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}