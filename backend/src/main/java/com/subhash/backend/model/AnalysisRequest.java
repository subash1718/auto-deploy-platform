package com.subhash.backend.model;

public class AnalysisRequest {

    private String githubUrl;

    private String environment;

    private int expectedUsers;

    private boolean highAvailability;

    public AnalysisRequest() {
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getExpectedUsers() {
        return expectedUsers;
    }

    public void setExpectedUsers(int expectedUsers) {
        this.expectedUsers = expectedUsers;
    }

    public boolean isHighAvailability() {
        return highAvailability;
    }

    public void setHighAvailability(boolean highAvailability) {
        this.highAvailability = highAvailability;
    }
}
