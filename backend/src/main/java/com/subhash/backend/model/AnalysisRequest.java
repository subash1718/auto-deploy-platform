package com.subhash.backend.model;

public class AnalysisRequest {

    private String githubUrl;

    public AnalysisRequest() {
    }

    public AnalysisRequest(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }
}
