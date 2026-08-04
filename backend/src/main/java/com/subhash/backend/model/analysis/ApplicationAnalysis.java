package com.subhash.backend.model.analysis;

public class ApplicationAnalysis {

    private boolean dockerPresent;
    private boolean terraformPresent;
    private boolean kubernetesPresent;
    private boolean databaseRequired;
    private String database;
    private int port;
    private boolean restApi;
    private boolean microservice;

    public boolean isDockerPresent() {
        return dockerPresent;
    }

    public void setDockerPresent(boolean dockerPresent) {
        this.dockerPresent = dockerPresent;
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

    public boolean isDatabaseRequired() {
        return databaseRequired;
    }

    public void setDatabaseRequired(boolean databaseRequired) {
        this.databaseRequired = databaseRequired;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isRestApi() {
        return restApi;
    }

    public void setRestApi(boolean restApi) {
        this.restApi = restApi;
    }

    public boolean isMicroservice() {
        return microservice;
    }

    public void setMicroservice(boolean microservice) {
        this.microservice = microservice;
    }
}
