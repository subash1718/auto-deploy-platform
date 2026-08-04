package com.subhash.backend.model.infrastructure;

public class InfrastructureRecommendation {

    private String cloudProvider;
    private String computeService;
    private String instanceType;

    private int cpu;
    private int memoryGb;
    private int storageGb;

    private boolean vpc;
    private boolean internetGateway;
    private boolean loadBalancer;
    private boolean autoScaling;

    private int publicSubnets;
    private int privateSubnets;

    private int minInstances;
    private int maxInstances;

    public InfrastructureRecommendation() {
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
    }

    public int getStorageGb() {
        return storageGb;
    }

    public void setStorageGb(int storageGb) {
        this.storageGb = storageGb;
    }

    public boolean isVpc() {
        return vpc;
    }

    public void setVpc(boolean vpc) {
        this.vpc = vpc;
    }

    public boolean isInternetGateway() {
        return internetGateway;
    }

    public void setInternetGateway(boolean internetGateway) {
        this.internetGateway = internetGateway;
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

    public int getPublicSubnets() {
        return publicSubnets;
    }

    public void setPublicSubnets(int publicSubnets) {
        this.publicSubnets = publicSubnets;
    }

    public int getPrivateSubnets() {
        return privateSubnets;
    }

    public void setPrivateSubnets(int privateSubnets) {
        this.privateSubnets = privateSubnets;
    }

    public int getMinInstances() {
        return minInstances;
    }

    public void setMinInstances(int minInstances) {
        this.minInstances = minInstances;
    }

    public int getMaxInstances() {
        return maxInstances;
    }

    public void setMaxInstances(int maxInstances) {
        this.maxInstances = maxInstances;
    }
}
