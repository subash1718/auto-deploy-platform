package com.subhash.backend.model.analysis;

public class ResourceRequirement {

    private int cpu;

    private int memoryGb;

    private int storageGb;

    private boolean databaseRequired;

    private String databaseType;

    private int applicationPort;

    private boolean loadBalancerRequired;

    private boolean autoScalingRequired;

    private boolean containerizationRequired;

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

    public boolean isDatabaseRequired() {
        return databaseRequired;
    }

    public void setDatabaseRequired(boolean databaseRequired) {
        this.databaseRequired = databaseRequired;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public int getApplicationPort() {
        return applicationPort;
    }

    public void setApplicationPort(int applicationPort) {
        this.applicationPort = applicationPort;
    }

    public boolean isLoadBalancerRequired() {
        return loadBalancerRequired;
    }

    public void setLoadBalancerRequired(boolean loadBalancerRequired) {
        this.loadBalancerRequired = loadBalancerRequired;
    }

    public boolean isAutoScalingRequired() {
        return autoScalingRequired;
    }

    public void setAutoScalingRequired(boolean autoScalingRequired) {
        this.autoScalingRequired = autoScalingRequired;
    }

    public boolean isContainerizationRequired() {
        return containerizationRequired;
    }

    public void setContainerizationRequired(boolean containerizationRequired) {
        this.containerizationRequired = containerizationRequired;
    }
}
